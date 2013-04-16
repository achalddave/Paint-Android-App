package edu.berkeley.cs160.achaldave.prog2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.larswerkman.colorpicker.ColorPicker;
import com.larswerkman.colorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.colorpicker.SVBar;

public class ShapesFragment extends Fragment {

	SettingsActivity settings;
	View root;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		root = inflater.inflate(R.layout.activity_shapes_fragment, container, false);
		RadioGroup shapePicker = (RadioGroup) root.findViewById(R.id.shape_selector);
		this.settings = (SettingsActivity) this.getActivity();
		
		if (settings.shapeType == MainActivity.ShapeType.OVAL) {
			shapePicker.check(R.id.oval);
		} else if (settings.shapeType == MainActivity.ShapeType.RECTANGLE) {
			shapePicker.check(R.id.rectangle);
		} else {
			int id = shapePicker.getCheckedRadioButtonId();
			if (id == R.id.oval) {
				settings.shapeType = MainActivity.ShapeType.OVAL;
			} else {
				settings.shapeType = MainActivity.ShapeType.RECTANGLE;
			}
		}
		
		shapePicker.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.oval) {
					settings.shapeType = MainActivity.ShapeType.OVAL;
				} else {
					settings.shapeType = MainActivity.ShapeType.RECTANGLE;
				}
			}
		});
		
		
		ColorPicker picker = (ColorPicker) root.findViewById(R.id.shapes_color_picker);
		picker.setColor(settings.color);
		picker.setOnColorChangedListener(new OnColorChangedListener() {
			
			@Override
			public void onColorChanged(int color) {
				// TODO Auto-generated method stub
				settings.color = color;
			}
		});
		
		return root;
	}

}