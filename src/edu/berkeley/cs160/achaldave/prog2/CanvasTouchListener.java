package edu.berkeley.cs160.achaldave.prog2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class CanvasTouchListener implements OnTouchListener {
	// helpers for drawing
	private float startX;
	private float startY;
	private float prevX;
	private float prevY;
	private float x;
	private float y;
	
	private MainActivity m;
	private GestureDetector gdt;
	
	public CanvasTouchListener(MainActivity m, GestureDetector gdt) {
		this.m = m;
		this.gdt = gdt;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (gdt.onTouchEvent(event)) {
			return true;
		}

		x = event.getX();
		y = event.getY();
		// else, draw
		switch (m.currShape) {
		case BRUSH:
			handlePath(event, x, y, false);
			break;
		case ERASER:
			handlePath(event, x, y, true);
			break;
		case OVAL:
		case RECTANGLE:
			handleShape(event, x, y);
			break;
		}
		return true;
	}

	private void handlePath(MotionEvent event, float x, float y, boolean erase) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			m.setCurrentShape();
			m.visibleDrawables.add(m.currentDrawable);

			m.currentPath.moveTo(x, y);
			prevX = x;
			prevY = y;
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			// quad formula used in fingerpaint.java api sample (make
			// things smoother)

			int historySize = event.getHistorySize();
			float histX,
			histY;
			for (int i = 0; i < historySize; i++) {
				histX = event.getHistoricalX(i);
				histY = event.getHistoricalY(i);
				m.currentPath.quadTo(prevX, prevY, (prevX + histX) / 2,
						(prevY + histY) / 2);
				prevX = histX;
				prevY = histY;
			}
			m.currentPath.quadTo(prevX, prevY, (prevX + x) / 2, (prevY + y) / 2);
			prevX = x;
			prevY = y;

			m.myView.invalidate();
			
			if (erase && event.getAction() == MotionEvent.ACTION_UP) {
			}
			break;
		default:
			break;
		}
	}

	private void handleShape(MotionEvent event, float x, float y) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			m.setCurrentShape();
			m.visibleDrawables.add(m.currentDrawable);

			startX = x;
			startY = y;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			this.setBounds((int) startX, (int) startY, (int) x, (int) y);
			m.currentDrawable.getPaint().setColor(m.color);

			m.myView.invalidate();
		}
	}

	private void setBounds(int x1, int y1, int x2, int y2) {
		m.currentDrawable.setBounds(Math.min(x1, x2), Math.min(y1, y2),
				Math.max(x1, x2), Math.max(y1, y2));
	}

}