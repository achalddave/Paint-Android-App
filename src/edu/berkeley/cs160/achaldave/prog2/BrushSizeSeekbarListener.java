package edu.berkeley.cs160.achaldave.prog2;

import android.app.Fragment;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BrushSizeSeekbarListener implements OnSeekBarChangeListener {
	Fragment frag;
	int maxRadius;
	boolean erase;

	public BrushSizeSeekbarListener(Fragment frag, boolean erase) {
		this.frag = frag;
		this.erase = erase;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		this.maxRadius = ((BrushFragment) frag).brushIndicator.maxRadius;
		if (maxRadius > 0) {
			if (erase == true) {
				EraserFragment frag = ((EraserFragment) this.frag);
				Log.d("Achal", "erase progress is " + progress);
				frag.settings
						.setEraseRadius((int) ((progress / 100.0f) * (maxRadius - 1)) + 1);
				frag.updateRadius();
			} else {
				BrushFragment frag = ((BrushFragment) this.frag);
				frag.settings
						.setBrushRadius((int) ((progress / 100.0f) * (maxRadius - 1)) + 1);
				frag.updateRadius();
			}
		}
	}
}
