package edu.berkeley.cs160.achaldave.prog2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

public class EraserIndicatorView extends BrushIndicatorView {

	protected EraserFragment frag;
	
	public EraserIndicatorView(Context context, EraserFragment frag) {
		super(context, frag);
		
		this.frag = frag;
		brushIndicator = new ShapeDrawable(new OvalShape());
		Paint p = brushIndicator.getPaint();
		p.setColor(Color.BLACK);
		p.setStyle(Style.STROKE);
	}
	
    protected void onDraw(Canvas canvas) {
    	if (canvasHeight < 0) {
    		canvasWidth = canvas.getWidth();
    		canvasHeight = canvas.getHeight();
    		maxRadius = canvasWidth > canvasHeight ? canvasHeight : canvasWidth;
    		frag.updateSlider();
    	}
    	radius = settings.getEraseRadius();
		brushIndicator.setBounds((canvasWidth - radius) / 2, (canvasHeight - radius) / 2, (canvasWidth + radius) / 2, (canvasHeight + radius) / 2);
    	brushIndicator.draw(canvas);
    }
	
	protected void updateRadius() {
    	this.invalidate();
	}
}
