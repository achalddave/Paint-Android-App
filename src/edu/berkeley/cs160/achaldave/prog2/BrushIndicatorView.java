package edu.berkeley.cs160.achaldave.prog2;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.View;

public class BrushIndicatorView extends View {
	protected ShapeDrawable brushIndicator;

	protected int canvasHeight = -1, canvasWidth = -1;
	protected int maxRadius;
	protected int radius = 0;
	protected BrushFragment frag;
	
	SettingsActivity settings;
	
	public BrushIndicatorView(Context context, BrushFragment frag) {
		super(context);
		
		this.frag = frag;
		brushIndicator = new ShapeDrawable(new OvalShape());
		settings = (SettingsActivity) context;
		radius = settings.getBrushRadius();
		Paint p = brushIndicator.getPaint();
		p.setColor(settings.color);
		this.updateRadius();
	}
	

    protected void onDraw(Canvas canvas) {
    	if (canvasHeight < 0) {
    		canvasWidth = canvas.getWidth();
    		canvasHeight = canvas.getHeight();
    		maxRadius = canvasWidth > canvasHeight ? canvasHeight : canvasWidth;
    		this.frag.updateSlider();
    	}
    	radius = settings.getBrushRadius();
    	Log.d("Achal", "Radius is " + radius);
		brushIndicator.setBounds((canvasWidth - radius) / 2, (canvasHeight - radius) / 2, (canvasWidth + radius) / 2, (canvasHeight + radius) / 2);
		brushIndicator.getPaint().setColor(settings.color);
    	brushIndicator.draw(canvas);
    }
    
    protected void updateRadius() {
    	this.invalidate();
    }
    
}