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
import android.support.v4.os.OperationCanceledException;
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
	public static final int FIGURE_COUNT_MAX_INDEX = 990;//1000 звёзд
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
    	public int calculateToInt(int index)  {
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
	class FigureEngine extends Engine implements OnSharedPreferenceChangeListener {
		private int FIGURE_COUNT = FIGURE_COUNT_MAPPER.calculateToInt(DEFAULT_FIGURE_COUNT);
		private float MIN_RADIUS = MIN_RADIUS_MAPPER.calculateToFloat(DEFAULT_MIN_RADIUS);
		private float MAX_RADIUS = MAX_RADIUS_MAPPER.calculateToFloat(DEFAULT_MAX_RADIUS);
		private int MIN_TRANSPARENCY = MIN_TRANSPARENCY_MAPPER.calculateToInt(DEFAULT_MIN_TRANSPARENCY);
		private int MAX_TRANSPARENCY = MAX_TRANSPARENCY_MAPPER.calculateToInt(DEFAULT_MAX_TRANSPARENCY);
		private float MIN_SPEED = MIN_SPEED_MAPPER.calculateToFloat(DEFAULT_MIN_SPEED);
		private float MAX_SPEED = MAX_SPEED_MAPPER.calculateToFloat(DEFAULT_MAX_SPEED);
		private float BRIGHTNESS = BRIGHTNESS_MAPPER.calculateToFloat(DEFAULT_BRIGHTNESS);
		private long FRAME_INTERVAL = getFrameInterval(FRAME_RATE_MAPPER.calculateToInt(DEFAULT_FRAME_RATE));



		private final Paint mPaint = new Paint();
		private float mTouchX = -5;
		private float mTouchY = -5;
		private float mWidth;
		private float mHeight;

		private IntBuffer canvasBuf;
		private IntBuffer bokehBuf;

		private int cycleCounter = 0;

		private final Runnable mPainter = new Runnable() {
			public void run() {
				drawFrame();
				mutate();
			}
		};
		private boolean mVisible;
		private SharedPreferences mPrefs;

		FigureEngine() {



			final Paint paint = mPaint;
			paint.setColor(0xffffffff);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(2);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStyle(Paint.Style.STROKE);

			mPrefs = BokehWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
			mPrefs.registerOnSharedPreferenceChangeListener(this);
			onSharedPreferenceChanged(mPrefs, null);

			fixFlippedMinMax();
		}

		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			FIGURE_COUNT = FIGURE_COUNT_MAPPER.calculateToInt(Integer.parseInt((prefs.getString(
					BokehWallpaperSettings.FIGURE_COUNT_KEY, Integer.toString(DEFAULT_FIGURE_COUNT)))));
			MIN_RADIUS = MIN_RADIUS_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MIN_RADIUS_KEY, Integer.toString(DEFAULT_MIN_RADIUS))));
			MAX_RADIUS = MAX_RADIUS_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MAX_RADIUS_KEY, Integer.toString(DEFAULT_MAX_RADIUS))));
			MIN_TRANSPARENCY = MIN_TRANSPARENCY_MAPPER.calculateToInt(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MIN_TRANSPARENCY_KEY, Integer.toString(DEFAULT_MIN_TRANSPARENCY))));
			MAX_TRANSPARENCY = MAX_TRANSPARENCY_MAPPER.calculateToInt(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MAX_TRANSPARENCY_KEY, Integer.toString(DEFAULT_MAX_TRANSPARENCY))));
			MIN_SPEED = MIN_SPEED_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MIN_SPEED_KEY, Integer.toString(DEFAULT_MIN_SPEED))));
			MAX_SPEED = MAX_SPEED_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.MAX_SPEED_KEY, Integer.toString(DEFAULT_MAX_SPEED))));
			BRIGHTNESS = BRIGHTNESS_MAPPER.calculateToFloat(Integer.parseInt(prefs.getString(
					BokehWallpaperSettings.BRIGHTNESS_KEY, Integer.toString(DEFAULT_BRIGHTNESS))));

			fixFlippedMinMax();
			createShapes();
		}

		private void fixFlippedMinMax() {
			if (MIN_RADIUS > MAX_RADIUS) {
				MAX_RADIUS = MIN_RADIUS;
			}

			if (MIN_TRANSPARENCY > MAX_TRANSPARENCY) {
				MAX_TRANSPARENCY = MIN_TRANSPARENCY;
			}
			if (MIN_SPEED > MAX_SPEED) {
				MAX_SPEED = MIN_SPEED;
			}
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			setTouchEventsEnabled(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mPainter);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
				mutate();
			} else {
				mHandler.removeCallbacks(mPainter);
			}
		}
		// Обработка движения звёзд при касании краёв экрана
		private void mutate() {
			cycleCounter++;
			if (cycleCounter >= 64) {
				cycleCounter = 0;
			}

			int count = figures.size();
			Figure figure;
			for (int i=0; i<count; i++) {
				figure = figures.get(i);

				figure.x = figure.x + figure.xMutation;
				if (
						(figure.xMutation > 0.0f && figure.x > mWidth)
								||
								(figure.xMutation < 0.0f && figure.x < 0.0f))
				{
					figure.xMutation = -figure.xMutation;
					figure.x = figure.x + figure.xMutation;
					figure.x = figure.x + figure.xMutation;
				}

				figure.y = figure.y + figure.yMutation;
				if (
						(figure.yMutation > 0.0f && figure.y > mHeight)
								||
								(figure.yMutation < 0.0f && figure.y < 0.0f))
				{
					figure.yMutation = -figure.yMutation;
					figure.y = figure.y + figure.yMutation;
					figure.y = figure.y + figure.yMutation;
				}

				figure.radius = figure.radius + figure.radiusMutation;
				if (
						(figure.radiusMutation > 0.0f && figure.radius > MAX_RADIUS)
								||
								(figure.radiusMutation < 0.0f && figure.radius < MIN_RADIUS))
				{
					figure.radiusMutation = -figure.radiusMutation;
					figure.radius = figure.radius + figure.radiusMutation;
					figure.radius = figure.radius + figure.radiusMutation;
				}

				figure.alpha = figure.alpha + figure.alphaMutation;
				if (
						(figure.alphaMutation > 0 && figure.alpha > MAX_TRANSPARENCY)
								||
								(figure.alphaMutation < 0 && figure.alpha < MIN_TRANSPARENCY))
				{
					figure.alphaMutation = -figure.alphaMutation;
					figure.alpha = figure.alpha + figure.alphaMutation;
					figure.alpha = figure.alpha + figure.alphaMutation;
				}

				populateFigure(figure);
			}

		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			mWidth = width;
			mHeight = height;
			createShapes();
			drawFrame();
			mutate();
		}
		//Создание с помощью настроек нужного количества фигур
		private void createShapes() {
			Figure figure;
			figures.clear();
			for (int i=0; i<FIGURE_COUNT; i++) {
				figure = createShape(0.0f + mWidth * random.nextFloat(), 0.0f + mHeight * random.nextFloat());
				figures.add(figure);
			}
		}
		//Настройка различных переменных для звёзд
		private Figure createShape(float x, float y) {
			Figure figure = new Figure();
			figure.radius = MIN_RADIUS + (MAX_RADIUS - MIN_RADIUS) * random.nextFloat();
			figure.x = x;
			figure.y = y;
			figure.alpha = MIN_TRANSPARENCY + random.nextInt(MAX_TRANSPARENCY - MIN_TRANSPARENCY + 1);
			figure.xMutation = (MAX_SPEED - MIN_SPEED) * (2.0f * random.nextFloat() - 1.0f);//Задаём направление звёзд
			figure.yMutation = (MAX_SPEED - MIN_SPEED) * (0.0f * random.nextFloat() - 0.0f);

			figure.xMutation = figure.xMutation + Math.signum(figure.xMutation) * MIN_SPEED;
			figure.yMutation = figure.yMutation + Math.signum(figure.yMutation) * MIN_SPEED;
			figure.alphaMutation = 1 + random.nextInt(2);
			figure.radiusMutation = -0.7f + 1.4f * random.nextFloat();//Мигание
			figure.paintStroke1 = new Paint();
			figure.paintStroke1.setStyle(Paint.Style.STROKE);
			figure.paintStroke1.setAntiAlias(true);

			figure.paintFill = new Paint();
			figure.paintFill.setStyle(Paint.Style.FILL);
			figure.paintFill.setAntiAlias(true);

			populateFigure(figure);
			return figure;
		}
		//Задание цвета звёзд
		private void populateFigure(Figure figure) {
			float radiusAndStroke = figure.radius + figure.strokeWidth;
			float boundedWidth = mWidth - radiusAndStroke * 3.0f;
			float boundedHeight = mHeight - radiusAndStroke * 3.0f;
			float boundedHalfWidth = boundedWidth / 2.0f;
			float boundedHalfHeight = boundedHeight / 3.0f;
			float max = boundedHalfWidth * boundedHalfHeight;
			float xy = (figure.x - radiusAndStroke - boundedHalfWidth) *
					(figure.y - radiusAndStroke - boundedHalfHeight) + max;
			max = max * 1.8f;


			figure.strokeColor = Color.parseColor("#FFFFFFFF");//Цвет звёзд
			figure.paintStroke1.setColor(figure.strokeColor);
			figure.paintStroke1.setStrokeWidth(figure.strokeWidth);
			figure.paintFill.setColor(figure.strokeColor);
			figure.paintFill.setStrokeWidth(figure.strokeWidth);

		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mPainter);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
			drawFrame();
		}

		//Сохранение положения события касания, чтобы использовать его позже для рисования
		@Override
		public void onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				mTouchX = event.getX();
				mTouchY = event.getY();
				if (figures.size() > 0) {
					figures.remove(0);
					Figure bokeh = createShape(mTouchX, mTouchY);
					figures.add(bokeh);
				}
			}
			else {
				mTouchX = -5;
				mTouchY = -5;
			}
			super.onTouchEvent(event);
		}

		//Повторный вызов метода путём публикации с задержкой

		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {

					drawFigure(c);

				}
			} finally {
				if (c != null) holder.unlockCanvasAndPost(c);
			}


			// Перепланировка следующей перерисовки

			mHandler.removeCallbacks(mPainter);
			if (mVisible) {
				mHandler.postDelayed(mPainter, FRAME_INTERVAL);
			}
		}

		//Рисование звёзд и гор
		Paint paint;
		Bitmap bitmapSource;
		Bitmap bitmap;
		void drawFigure(Canvas c) {
			c.save();
			//Добавление картинки гор
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.gg);
			bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight());






			c.drawColor(0xff000000);//Цвет неба
			int count = figures.size();
			Figure figure;
			for (int i=0; i<count; i++) {
				figure = figures.get(i);



				c.drawCircle(figure.x, figure.y, figure.radius + figure.strokeWidth / 2.0f, figure.paintFill);



			}
			c.drawBitmap(bitmap, -180, 1200, paint);
			c.restore();
		}
	}






	//Класс с переменными
	public static class Figure {
		public int strokeColor;
		public int alpha;
		public float strokeWidth;
		public float x;
		public float y;
		public float radius;
		public Paint paintStroke1;
		public Paint paintStroke2;
		public Paint paintFill;
		public float xMutation;
		public float yMutation;
		public int alphaMutation;
		public float radiusMutation;
		public float strokeMutation;
	}

}
