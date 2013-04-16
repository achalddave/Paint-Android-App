package edu.berkeley.cs160.achaldave.prog2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected DrawingView myView;
	protected OnTouchListener touchListener;
	protected GestureDetector gdt;

	protected ArrayList<Drawable> visibleDrawables = new ArrayList<Drawable>();
	protected Shape currentShape = new OvalShape();
	protected ShapeDrawable currentDrawable = new ShapeDrawable(currentShape);
	protected Path currentPath;

	static int defaultColor = Color.RED;
	static int defaultRadius = 10;
	static int defaultBackground = Color.WHITE;
	
	static enum ShapeType {
		BRUSH, ERASER, OVAL, RECTANGLE
	}
	
	protected int color = defaultColor;
	protected int brushRadius = defaultRadius;
	protected int eraseRadius = defaultRadius;
	protected int backgroundColor = defaultBackground;
	protected ShapeType currShape = ShapeType.BRUSH;

	protected String filePath;

	protected LinearLayout layout;

	protected boolean popupVisible = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		layout = (LinearLayout) findViewById(R.id.linearlayout);
		myView = new DrawingView(this, MainActivity.this);

		// disable hardware acceleration
		myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		layout.addView(myView);

		gdt = new GestureDetector(this, new CanvasGestureListener(
				MainActivity.this));

		touchListener = new CanvasTouchListener(MainActivity.this, gdt);

		myView.setOnTouchListener(touchListener);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		boolean clearScreen = intent.getBooleanExtra("clear", false);
		if (clearScreen == true) {
			visibleDrawables.clear();
			myView.invalidate();
		} else {
			color = intent.getIntExtra("color", color);
			backgroundColor = intent.getIntExtra("backgroundColor", backgroundColor);
			brushRadius = intent.getIntExtra("brushRadius", brushRadius);
			eraseRadius = intent.getIntExtra("eraseRadius", eraseRadius);
			currShape = ShapeType.valueOf(intent.getStringExtra("type"));
		}
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			showPopup();
			return true;
		} else {
			return false;
		}
	}

	protected void showPopup() {
		// fix this code
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra("width", (int) (myView.canvasWidth * 0.9));
		intent.putExtra("height", (int) (myView.canvasHeight * 0.9));
		intent.putExtra("background", backgroundColor);
		intent.putExtra("color", color);
		intent.putExtra("brushRadius", brushRadius);
		intent.putExtra("eraseRadius", eraseRadius);
		intent.putExtra("shapeType", currShape.name());
		startActivity(intent);
	}

	protected void setCurrentShape() {
		switch (currShape) {
		case BRUSH:
			setPath(false);
			break;
		case ERASER:
			setPath(true);
			break;
		case OVAL:
			currentShape = new OvalShape();
			currentDrawable = new ShapeDrawable(currentShape);
			break;
		case RECTANGLE:
			currentShape = new RectShape();
			currentDrawable = new ShapeDrawable(currentShape);
			break;
		}
	}

	private void setPath(boolean erase) {
		currentPath = new Path();
		currentShape = new PathShape(currentPath, myView.canvasWidth,
				myView.canvasHeight);
		currentDrawable = new ShapeDrawable(currentShape);
		currentDrawable
				.setBounds(0, 0, myView.canvasWidth, myView.canvasHeight);

		Paint curr = currentDrawable.getPaint();
		curr.setColor(color);
		curr.setStyle(Paint.Style.STROKE);
		curr.setStrokeWidth(brushRadius);
		curr.setStrokeJoin(Paint.Join.ROUND);
		curr.setStrokeCap(Paint.Cap.ROUND);
        curr.setAntiAlias(true);
        curr.setDither(true);
		
        if (erase == true) {
			curr.setStrokeWidth(eraseRadius);
        	curr.setColor(backgroundColor);
        }
	}

	private Boolean saveBitmap() {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		Bitmap currentBitmap = myView.getBitmap();
		currentBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		// will create "test.jpg" in sdcard folder.
		File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
				+ File.separator + "test.jpg");

		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// write the bytes in file
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			fo.flush();
			fo.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		filePath = f.getAbsolutePath();
		return true;

	}

}