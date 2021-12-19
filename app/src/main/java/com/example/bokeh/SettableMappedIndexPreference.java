package com.example.bokeh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SettableMappedIndexPreference extends SettablePreference {
	private Mapper mapper;

	public SettableMappedIndexPreference(Context context) {
		super(context);
	}

	public SettableMappedIndexPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SettableMappedIndexPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

    public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	@Override
    protected void onBindView(View view) {
        super.onBindView(view);
        if (valueRenderer != null) {
        	if (mapper != null && value != null) {
            	valueRenderer.renderValue(view, mapper.calculateToDisplayableString(Integer.parseInt(value)));
        	}
        	else {
            	valueRenderer.renderValue(view, "");
        	}
        }
    }

	public static interface Mapper {
    	public int calculateToInt(int index);
    	public float calculateToFloat(int index);
    	public String calculateToDisplayableString(int index);
    }
}
