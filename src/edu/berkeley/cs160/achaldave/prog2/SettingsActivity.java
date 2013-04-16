package edu.berkeley.cs160.achaldave.prog2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class SettingsActivity extends Activity {

	protected int backgroundColor = MainActivity.defaultBackground;
	protected int color = MainActivity.defaultColor;
	private int brushRadius = MainActivity.defaultRadius;
	private int eraseRadius = MainActivity.defaultRadius;
	protected MainActivity.ShapeType shapeType = MainActivity.ShapeType.BRUSH;
	private boolean clearScreen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * setContentView(R.layout.activity_settings);
		 * getWindow().setLayout(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT);
		 */
		Intent intent = getIntent();
		int width = intent.getIntExtra("width", 500);
		int height = intent.getIntExtra("height", 500);
		backgroundColor = intent.getIntExtra("background", backgroundColor);
		color = intent.getIntExtra("color", color);
		brushRadius = intent.getIntExtra("brushRadius", brushRadius);
		eraseRadius = intent.getIntExtra("eraseRadius", eraseRadius);
		shapeType = MainActivity.ShapeType.valueOf(intent.getStringExtra("shapeType"));
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		LayoutParams params = getWindow().getAttributes();
		params.height = height; // fixed height
		params.width = width; // fixed width
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		setContentView(R.layout.activity_settings);

		ActionBar ab = getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ab.addTab(ab
				.newTab()
				.setText("Brush")
				.setTabListener(
						new MenuTabListener<BrushFragment>(this, MainActivity.ShapeType.BRUSH.name(),
								BrushFragment.class)), (shapeType == MainActivity.ShapeType.BRUSH));
		ab.addTab(ab
				.newTab()
				.setText("Eraser")
				.setTabListener(
						new MenuTabListener<EraserFragment>(this, MainActivity.ShapeType.ERASER.name(),
								EraserFragment.class)), (shapeType == MainActivity.ShapeType.ERASER));
		ab.addTab(ab
				.newTab()
				.setText("Shapes")
				.setTabListener(
						new MenuTabListener<ShapesFragment>(this, "shapes",
								ShapesFragment.class)), (shapeType == MainActivity.ShapeType.OVAL || shapeType == MainActivity.ShapeType.RECTANGLE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	public static void showAsPopup(Activity activity) {
		// To show activity as dialog and dim the background, you need to
		// declare android:theme="@style/PopupTheme" on for the chosen activity
		// on the manifest
		activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		LayoutParams params = activity.getWindow().getAttributes();
		params.height = 850; // fixed height
		params.width = 850; // fixed width
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		activity.getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
	}
	
	@Override
	protected void onStop() {
		Intent intent = new Intent(this.getBaseContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("clear", clearScreen);
		intent.putExtra("background", backgroundColor);
		intent.putExtra("type", shapeType.name());
		intent.putExtra("brushRadius", brushRadius);
		intent.putExtra("eraseRadius", eraseRadius);
		intent.putExtra("color", color);
		Log.d("Achal", "Shape type is " + shapeType.name());
		
		startActivity(intent);

		super.onStop();
	}
	
	protected void clearScreen() {
		clearScreen = true;
		this.finish();
	}
	
	public void setBrushRadius(int rad) { Log.d("Achal", "Setting brush radius to " + rad); this.brushRadius = rad; }
	
	public int getBrushRadius() { return this.brushRadius; }

	public void setEraseRadius(int rad) { this.eraseRadius = rad; }
	
	public int getEraseRadius() { return this.eraseRadius; }
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			Rect dialogBounds = new Rect();
			getWindow().getDecorView().getHitRect(dialogBounds);

			if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
				// Tapped outside so we finish the activity
				this.finish();
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	

}
