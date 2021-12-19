package com.example.bokeh;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BokehWallpaperSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	public static final String FIGURE_COUNT_KEY = "count";
	public static final String MIN_TRANSPARENCY_KEY = "min_transparency";
	public static final String MAX_TRANSPARENCY_KEY = "max_transparency";
	public static final String BRIGHTNESS_KEY = "brightness";
	public static final String MIN_RADIUS_KEY = "min_radius";
	public static final String MAX_RADIUS_KEY = "max_radius";
	public static final String MIN_SPEED_KEY = "min_speed";
	public static final String MAX_SPEED_KEY = "max_speed";


	private static final int FIGURE_COUNT_DIALOG = 101;
	private static final int MIN_TRANSPARENCY_DIALOG = 102;
	private static final int MAX_TRANSPARENCY_DIALOG = 103;
	private static final int BRIGHTNESS_DIALOG = 104;
	private static final int MIN_RADIUS_DIALOG = 105;
	private static final int MAX_RADIUS_DIALOG = 106;
	private static final int MIN_SPEED_DIALOG = 109;
	private static final int MAX_SPEED_DIALOG = 110;

	
	private static ArrayList<String> preferenceKeys = new ArrayList<String>();
	private static HashMap<String, Integer> preferenceKeyAssociatedDialog = new HashMap<String, Integer>();
	private static HashMap<String, Object> preferenceKeyAssociatedDefaultValue = new HashMap<String, Object>();
	private static HashMap<String, Integer> preferenceKeyAssociatedMaxIndex = new HashMap<String, Integer>();
	private static HashMap<String, String> preferenceKeyAssociatedTitle = new HashMap<String, String>();
	private static HashMap<String, SettableMappedIndexPreference.Mapper> preferenceKeyAssociatedMapper = new HashMap<String, SettableMappedIndexPreference.Mapper>();
	static {
		preferenceKeys.add(FIGURE_COUNT_KEY);
		preferenceKeys.add(MIN_TRANSPARENCY_KEY);
		preferenceKeys.add(MAX_TRANSPARENCY_KEY);
		preferenceKeys.add(BRIGHTNESS_KEY);
		preferenceKeys.add(MIN_RADIUS_KEY);
		preferenceKeys.add(MAX_RADIUS_KEY);
		preferenceKeys.add(MIN_SPEED_KEY);
		preferenceKeys.add(MAX_SPEED_KEY);

		
		preferenceKeyAssociatedDialog.put(FIGURE_COUNT_KEY, FIGURE_COUNT_DIALOG);
		preferenceKeyAssociatedDialog.put(MIN_TRANSPARENCY_KEY, MIN_TRANSPARENCY_DIALOG);
		preferenceKeyAssociatedDialog.put(MAX_TRANSPARENCY_KEY, MAX_TRANSPARENCY_DIALOG);
		preferenceKeyAssociatedDialog.put(BRIGHTNESS_KEY, BRIGHTNESS_DIALOG);
		preferenceKeyAssociatedDialog.put(MIN_RADIUS_KEY, MIN_RADIUS_DIALOG);
		preferenceKeyAssociatedDialog.put(MAX_RADIUS_KEY, MAX_RADIUS_DIALOG);
		preferenceKeyAssociatedDialog.put(MIN_SPEED_KEY, MIN_SPEED_DIALOG);
		preferenceKeyAssociatedDialog.put(MAX_SPEED_KEY, MAX_SPEED_DIALOG);

		
		preferenceKeyAssociatedDefaultValue.put(FIGURE_COUNT_KEY, BokehWallpaper.DEFAULT_FIGURE_COUNT);
		preferenceKeyAssociatedDefaultValue.put(MIN_TRANSPARENCY_KEY, BokehWallpaper.DEFAULT_MIN_TRANSPARENCY);
		preferenceKeyAssociatedDefaultValue.put(MAX_TRANSPARENCY_KEY, BokehWallpaper.DEFAULT_MAX_TRANSPARENCY);
		preferenceKeyAssociatedDefaultValue.put(BRIGHTNESS_KEY, BokehWallpaper.DEFAULT_BRIGHTNESS);
		preferenceKeyAssociatedDefaultValue.put(MIN_RADIUS_KEY, BokehWallpaper.DEFAULT_MIN_RADIUS);
		preferenceKeyAssociatedDefaultValue.put(MAX_RADIUS_KEY, BokehWallpaper.DEFAULT_MAX_RADIUS);
		preferenceKeyAssociatedDefaultValue.put(MIN_SPEED_KEY, BokehWallpaper.DEFAULT_MIN_SPEED);
		preferenceKeyAssociatedDefaultValue.put(MAX_SPEED_KEY, BokehWallpaper.DEFAULT_MAX_SPEED);

		
		preferenceKeyAssociatedMaxIndex.put(FIGURE_COUNT_KEY, BokehWallpaper.FIGURE_COUNT_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MIN_TRANSPARENCY_KEY, BokehWallpaper.MIN_TRANSPARENCY_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MAX_TRANSPARENCY_KEY, BokehWallpaper.MAX_TRANSPARENCY_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(BRIGHTNESS_KEY, BokehWallpaper.BRIGHTNESS_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MIN_RADIUS_KEY, BokehWallpaper.MIN_RADIUS_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MAX_RADIUS_KEY, BokehWallpaper.MAX_RADIUS_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MIN_SPEED_KEY, BokehWallpaper.MIN_SPEED_MAX_INDEX);
		preferenceKeyAssociatedMaxIndex.put(MAX_SPEED_KEY, BokehWallpaper.MAX_SPEED_MAX_INDEX);


		preferenceKeyAssociatedTitle.put(FIGURE_COUNT_KEY, "Количество");
		preferenceKeyAssociatedTitle.put(MIN_TRANSPARENCY_KEY, "Минимальная прозрачность");
		preferenceKeyAssociatedTitle.put(MAX_TRANSPARENCY_KEY, "Максимальная прозрачность");
		preferenceKeyAssociatedTitle.put(BRIGHTNESS_KEY, "Яркость");
		preferenceKeyAssociatedTitle.put(MIN_RADIUS_KEY, "Минимальный размер");
		preferenceKeyAssociatedTitle.put(MAX_RADIUS_KEY, "Максимальный размер");
		preferenceKeyAssociatedTitle.put(MIN_SPEED_KEY, "Минимальная скорость");
		preferenceKeyAssociatedTitle.put(MAX_SPEED_KEY, "Максимальная скорость");


		preferenceKeyAssociatedMapper.put(FIGURE_COUNT_KEY, BokehWallpaper.FIGURE_COUNT_MAPPER);
		preferenceKeyAssociatedMapper.put(MIN_TRANSPARENCY_KEY, BokehWallpaper.MIN_TRANSPARENCY_MAPPER);
		preferenceKeyAssociatedMapper.put(MAX_TRANSPARENCY_KEY, BokehWallpaper.MAX_TRANSPARENCY_MAPPER);
		preferenceKeyAssociatedMapper.put(BRIGHTNESS_KEY, BokehWallpaper.BRIGHTNESS_MAPPER);
		preferenceKeyAssociatedMapper.put(MIN_RADIUS_KEY, BokehWallpaper.MIN_RADIUS_MAPPER);
		preferenceKeyAssociatedMapper.put(MAX_RADIUS_KEY, BokehWallpaper.MAX_RADIUS_MAPPER);
		preferenceKeyAssociatedMapper.put(MIN_SPEED_KEY, BokehWallpaper.MIN_SPEED_MAPPER);
		preferenceKeyAssociatedMapper.put(MAX_SPEED_KEY, BokehWallpaper.MAX_SPEED_MAPPER);

	}

	private String currentKey;
	
	@Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getPreferenceManager().setSharedPreferencesName(BokehWallpaper.SHARED_PREFS_NAME);
        addPreferencesFromResource(R.xml.bokeh_settings);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        PreferenceScreen rootPS;
        Preference p;
        rootPS = getPreferenceScreen();

        for (final String preferenceKey : preferenceKeys) {
            p = rootPS.findPreference(preferenceKey);
            ((SettableMappedIndexPreference)p).setValueRenderer(simpleRenderer);
            ((SettableMappedIndexPreference)p).setMapper(preferenceKeyAssociatedMapper.get(preferenceKey));
            p.setOnPreferenceClickListener(new OnPreferenceClickListener() {
    			@Override
    			public boolean onPreferenceClick(Preference preference) {
    				currentKey = preferenceKey;
    				showDialog(preferenceKeyAssociatedDialog.get(preferenceKey));
    				return false;
    			}
            });
        }

	}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key) {
    }
    
    SettablePreference.ValueRenderer simpleRenderer = new SettablePreference.ValueRenderer() {
		@Override
		public void renderValue(View view, String value) {
		    TextView myTextView = (TextView)view.findViewById(R.id.settable_preference);
		    if (value != null) {
		        myTextView.setText(value);
		    }
		    else {
		        myTextView.setText("");
		    }
		}
    };

	@Override
    protected Dialog onCreateDialog(int id) {
        for (final String preferenceKey : preferenceKeys) {
    		if (id == preferenceKeyAssociatedDialog.get(preferenceKey)) {
    			return new MagnitudeDialog(this, onMagnitudeSetListener, (Integer)preferenceKeyAssociatedDefaultValue.get(preferenceKey));
    		}
        }
    	return null;
    }

    @Override
	protected void onPrepareDialog(int id, Dialog dialog) {
        for (final String preferenceKey : preferenceKeys) {
    		if (id == preferenceKeyAssociatedDialog.get(preferenceKey)) {
    	        PreferenceScreen ps;
    	        SettableMappedIndexPreference p;
    	        ps = getPreferenceScreen();
    	        p = (SettableMappedIndexPreference)ps.findPreference(preferenceKey);
    	        String value = p.getValue();
    	        int magnitude = getDimmestMagnitude(value, (Integer)preferenceKeyAssociatedDefaultValue.get(preferenceKey));
    	    	((MagnitudeDialog)dialog).updateMagnitude(magnitude);
    			
    		}
        }
	}
    
    private static boolean isEmpty(String val) {
    	return (val == null || val.trim().length() == 0);
    }
    
    public static int getDimmestMagnitude(String magnitude, int defaultValue) {
    	if (isEmpty(magnitude)) {
    		return defaultValue;
    	}
    	return Integer.parseInt(magnitude);
    }

    private interface OnMagnitudeSetListener {
    	public void onMagnitudeSet(int magnitude);
    }

    private OnMagnitudeSetListener onMagnitudeSetListener = new OnMagnitudeSetListener() {
		@Override
		public void onMagnitudeSet(int magnitude) {
	        PreferenceScreen ps;
	        SettableMappedIndexPreference sp;
	        ps = getPreferenceScreen();
            sp = (SettableMappedIndexPreference)ps.findPreference(currentKey);
	        sp.setValue(Integer.toString(magnitude));

		}
    };

    private class MagnitudeDialog extends AlertDialog implements OnSeekBarChangeListener {
		protected MagnitudeDialog(Context context, final OnMagnitudeSetListener onMagnitudeSetListener, int magnitude) {
			super(context);
	        LayoutInflater layoutInflater = LayoutInflater.from(BokehWallpaperSettings.this);
	        final View magnitudeView = layoutInflater.inflate(R.layout.slider_dialog_box, null);
			setView(magnitudeView);
			TextView magnitudeValue = (TextView)magnitudeView.findViewById(R.id.magnitudeValue);
			int defaultValue = (Integer)preferenceKeyAssociatedDefaultValue.get(currentKey);
			String defaultText = preferenceKeyAssociatedMapper.get(currentKey).calculateToDisplayableString(defaultValue);
			magnitudeValue.setText(defaultText);
        	SeekBar magnitudeSlider = (SeekBar)magnitudeView.findViewById(R.id.slider);
        	magnitudeSlider.setMax((Integer)preferenceKeyAssociatedMaxIndex.get(currentKey));
        	magnitudeSlider.setProgress(magnitude);
        	magnitudeSlider.setOnSeekBarChangeListener(this);
        	setTitle(preferenceKeyAssociatedTitle.get(currentKey));
			setButton(AlertDialog.BUTTON_POSITIVE, "Сохранить", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	SeekBar magnitudeSlider = (SeekBar)findViewById(R.id.slider);
	            	int dimmestMagnitude = magnitudeSlider.getProgress();
	            	onMagnitudeSetListener.onMagnitudeSet(dimmestMagnitude);
	            }
	        });
			setButton(AlertDialog.BUTTON_NEUTRAL, "Сброс", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	onMagnitudeSetListener.onMagnitudeSet((Integer)preferenceKeyAssociatedDefaultValue.get(currentKey));
	            }
	        });
			setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            }
	        });
		}
		
		public void updateMagnitude(int magnitude) {
        	SeekBar magnitudeSlider = (SeekBar)findViewById(R.id.slider);
        	magnitudeSlider.setProgress(magnitude);
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			TextView magnitudeValue = (TextView)findViewById(R.id.magnitudeValue);
			magnitudeValue.setText(preferenceKeyAssociatedMapper.get(currentKey).calculateToDisplayableString(progress));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}


    }
}
