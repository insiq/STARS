package com.example.bokeh;

import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import com.example.bokeh.SettableMappedIndexPreference.Mapper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class BokehWallpaper extends WallpaperService {

public static final String SHARED_PREFS_NAME = "bokeh_settings";


	public static final int DEFAULT_FIGURE_COUNT = 30;
	public static final int DEFAULT_MIN_RADIUS = 9;
	public static final int DEFAULT_MAX_RADIUS = 49;
	public static final int DEFAULT_MIN_TRANSPARENCY = 64;
	public static final int DEFAULT_MAX_TRANSPARENCY = 212;
	public static final int DEFAULT_MIN_SPEED = 0;
	public static final int DEFAULT_MAX_SPEED = 7;
	public static final int DEFAULT_BRIGHTNESS = 50;
	public static final int DEFAULT_FRAME_RATE = 29;

// Установка максимальных и минимальных настроек
	public static final int FIGURE_COUNT_MAX_INDEX = 990;//1000 штук максимально количество
	public static final int MIN_RADIUS_MAX_INDEX = 5;
	public static final int MAX_RADIUS_MAX_INDEX = 9;
	public static final int MIN_TRANSPARENCY_MAX_INDEX = 255;
	public static final int MAX_TRANSPARENCY_MAX_INDEX = 255;
	public static final int MIN_SPEED_MAX_INDEX = 70;
	public static final int MAX_SPEED_MAX_INDEX = 70;
	public static final int BRIGHTNESS_MAX_INDEX = 100;
	public static final int FRAME_RATE_MAX_INDEX = 99;
	
	private ArrayList<Figure> figures = new ArrayList<Figure>();
    private final Handler mHandler = new Handler();
    private Random random = new Random(System.currentTimeMillis());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new FigureEngine();
    }
    //Настройка количества
    public static Mapper FIGURE_COUNT_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	return index + 10;
        }
        //Обработка исключения
    	@Override
    	public float calculateToFloat(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
    };
    

    
    public static Mapper MIN_RADIUS_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
    		throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
    	public float calculateToFloat(int index) {
        	return index + 1.0f;
        }

    	@Override
		public String calculateToDisplayableString(int index) {
    		DecimalFormat df = new DecimalFormat("0");
			return df.format(calculateToFloat(index));
		}
    };
    
    public static Mapper MAX_RADIUS_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
    		throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
    	public float calculateToFloat(int index) {
        	return index + 1.0f;
        }

    	@Override
		public String calculateToDisplayableString(int index) {
    		DecimalFormat df = new DecimalFormat("0");
			return df.format(calculateToFloat(index));
		}
    };
    

    
    public static Mapper MIN_TRANSPARENCY_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	return index;
        }

    	@Override
    	public float calculateToFloat(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
    };
    
    public static Mapper MAX_TRANSPARENCY_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	return index;
        }

    	@Override
    	public float calculateToFloat(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
    };
    
    public static Mapper MIN_SPEED_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
    	public float calculateToFloat(int index) {
        	return index / 10.0f;
        }

    	@Override
		public String calculateToDisplayableString(int index) {
    		DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
    };
    
    public static Mapper MAX_SPEED_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
    	public float calculateToFloat(int index) {
        	return index / 10.0f;
        }

    	@Override
		public String calculateToDisplayableString(int index) {
    		DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
    };
    
    public static Mapper BRIGHTNESS_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
    	public float calculateToFloat(int index) {
        	return (float)index / (float)BRIGHTNESS_MAX_INDEX;
        }

    	@Override
		public String calculateToDisplayableString(int index) {
    		DecimalFormat df = new DecimalFormat("0.00");
			return df.format(calculateToFloat(index));
		}
    };
    
    public static Mapper FRAME_RATE_MAPPER = new Mapper() {
    	@Override
    	public int calculateToInt(int index) {
        	return index + 1;
        }

    	@Override
    	public float calculateToFloat(int index) {
        	throw new UnsupportedOperationException("В свойствах присутствует несоответствие между различными типами данных");
        }

    	@Override
		public String calculateToDisplayableString(int index) {
			return Integer.toString(calculateToInt(index));
		}
    };
    
    private static int getFrameInterval(int frameRate) {
    	return (int)Math.round(1000.0f / (float)frameRate);
    }

}
