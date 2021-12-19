package com.example.bokeh;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

public class SettablePreference extends Preference {
	protected String value;
	protected ValueRenderer valueRenderer;

	public SettablePreference(Context context) {
		super(context);
	}

	public SettablePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SettablePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		persistString(value);
		notifyChanged();
	}
	
	public void setValueRenderer(ValueRenderer renderer) {
		this.valueRenderer = renderer;
	}
	
    @Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		super.onSetInitialValue(restorePersistedValue, defaultValue);
		if (!restorePersistedValue) {
			setValue((String)defaultValue);
		}
		else {
			setValue(getPersistedString((String)defaultValue));
		}
	}

	@Override
    protected void onBindView(View view) {
        super.onBindView(view);
        if (valueRenderer != null) {
        	valueRenderer.renderValue(view, value);
        }
    }
	
	public static interface ValueRenderer {
		public void renderValue(View view, String value);
	}
}
