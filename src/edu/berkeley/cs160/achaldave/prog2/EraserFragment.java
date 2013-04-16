package edu.berkeley.cs160.achaldave.prog2;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class EraserFragment extends BrushFragment {
	private View root;
	protected Paint paint;
	protected SettingsActivity settings;
	protected SeekBar eraserSize;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		this.root = inflater.inflate(R.layout.activity_eraser_fragment, container, false);
		this.settings = (SettingsActivity) this.getActivity();
		setupEraserIndicator();
		
		Button clear = (Button) root.findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				settings.clearScreen();
			}
		});
		return root;
	}
	
	protected void updateSlider() {
		eraserSize.setProgress((int) ((((settings.getEraseRadius()) - 1) * 100.0f) / (brushIndicator.maxRadius - 1)));
	}
	
	private void setupEraserIndicator() {
		brushIndicatorContainer = (LinearLayout) root.findViewById(R.id.eraser_size_indicator_container);
		brushIndicator = new EraserIndicatorView(this.getActivity(), this);
		brushIndicatorContainer.addView(brushIndicator);

		eraserSize = (SeekBar) root.findViewById(R.id.eraser_size);
		Log.d("Achal", "max radius is " + brushIndicator.maxRadius);

		eraserSize.setOnSeekBarChangeListener(new BrushSizeSeekbarListener(EraserFragment.this, true));
	}

	protected void updateRadius() {
		this.brushIndicator.updateRadius();
	}
}
