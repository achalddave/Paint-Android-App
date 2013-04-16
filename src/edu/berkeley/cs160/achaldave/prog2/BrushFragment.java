package edu.berkeley.cs160.achaldave.prog2;

import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.colorpicker.SVBar;

public class BrushFragment extends Fragment {

	private View root;
	protected LinearLayout brushIndicatorContainer;
	protected BrushIndicatorView brushIndicator;
	protected Paint paint;
	protected SettingsActivity settings;
	protected SeekBar brushSize;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		this.root = inflater.inflate(R.layout.activity_brush_fragment, container, false);
		this.settings = (SettingsActivity) this.getActivity();
		ColorPicker picker = (ColorPicker) root.findViewById(R.id.picker);
		picker.setColor(settings.color);
		
		picker.setOnColorChangedListener(new OnColorChangedListener() {
			
			@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
				settings.color = color;
				BrushFragment.this.updateRadius();
			}
		});
		
		setupBrushIndicator();
		
		return root;
	}
	
	protected void updateSlider() {
		brushSize.setProgress((int) ((((settings.getBrushRadius()) - 1) * 100.0f) / (brushIndicator.maxRadius - 1)));
	}
	
	private void setupBrushIndicator() {
		brushIndicatorContainer = (LinearLayout) root.findViewById(R.id.brush_size_indicator_container);
		brushIndicator = new BrushIndicatorView(this.getActivity(), this);
		brushIndicatorContainer.addView(brushIndicator);

		brushSize = (SeekBar) root.findViewById(R.id.brush_size);

		brushSize.setOnSeekBarChangeListener(new BrushSizeSeekbarListener(BrushFragment.this,  false));
	}
	
	protected void updateRadius() {
		brushIndicator.updateRadius();
	}

}