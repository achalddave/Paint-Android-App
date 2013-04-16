package edu.berkeley.cs160.achaldave.prog2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CanvasGestureListener implements GestureDetector.OnGestureListener {
	
	private MainActivity m;
	
	public CanvasGestureListener(MainActivity m) {
		this.m = m;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		m.showPopup();
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
