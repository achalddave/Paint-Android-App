package edu.berkeley.cs160.achaldave.prog2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class DrawingView extends View {
	protected Bitmap mBitmap;
	protected Canvas currentCanvas;
	protected Paint p;

	protected int canvasWidth;
	protected int canvasHeight;
	
	private MainActivity m;

	public DrawingView(Context context, MainActivity mainActivity) {
		super(context);
		this.m = mainActivity;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap == null) {
			canvasWidth = canvas.getWidth();
			canvasHeight = canvas.getHeight();
			mBitmap = Bitmap.createBitmap(canvasWidth, canvasHeight,
					Bitmap.Config.RGB_565);
			currentCanvas = new Canvas(mBitmap);
			p = new Paint();
			p.setColor(m.backgroundColor);
		}

		currentCanvas.drawPaint(p);
		currentCanvas.setBitmap(mBitmap);
		
		for (int i = 0; i < m.visibleDrawables.size(); i++) {
			m.visibleDrawables.get(i).draw(currentCanvas);
		}

		canvas.drawBitmap(mBitmap, 0, 0, null);
	}

	protected Bitmap getBitmap() {
		return mBitmap;
	}
}