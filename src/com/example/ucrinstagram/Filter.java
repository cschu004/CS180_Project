package com.example.ucrinstagram;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Filter extends Activity {
	String filePath;
	ImageView myImage2;
	Bitmap beforeBmp;
	Bitmap afterBmp;

	final static int KERNAL_WIDTH = 3;
	final static int KERNAL_HEIGHT = 3;

	int[][] kernalBlur = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);

		filePath = getIntent().getExtras().getString("picture");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 5;
		beforeBmp = BitmapFactory.decodeFile(filePath, options);
		myImage2 = (ImageView) findViewById(R.id.imageView1);
		myImage2.setScaleType(ScaleType.FIT_XY);
		afterBmp = beforeBmp;
		myImage2.setImageBitmap(beforeBmp);
	}
	
	public Bitmap ConvertToSepia(Bitmap sampleBitmap) {
		ColorMatrix sepiaMatrix = new ColorMatrix();
		float[] sepMat = { 0.3930000066757202f, 0.7689999938011169f,
				0.1889999955892563f, 0, 0, 0.3490000069141388f,
				0.6859999895095825f, 0.1679999977350235f, 0, 0,
				0.2720000147819519f, 0.5339999794960022f, 0.1309999972581863f,
				0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 };
		sepiaMatrix.set(sepMat);
		final ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(
				sepiaMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas = new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}

	public Bitmap ConvertToBlackNWhite(Bitmap sampleBitmap) {
		ColorMatrix sepiaMatrix = new ColorMatrix();
		float[] sepMat = { 0.33f, 0.59f, 0.11f, 0, 0, 0.33f, 0.59f, 0.11f, 0,
				0, 0.33f, 0.59f, 0.11f, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 };
		sepiaMatrix.set(sepMat);
		final ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(
				sepiaMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas = new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}

	private Bitmap processingBitmap(Bitmap src, int[][] knl) {
		Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());

		int bmWidth = src.getWidth();
		int bmHeight = src.getHeight();
		int bmWidth_MINUS_2 = bmWidth - 2;
		int bmHeight_MINUS_2 = bmHeight - 2;

		for (int i = 1; i <= bmWidth_MINUS_2; i++) {
			for (int j = 1; j <= bmHeight_MINUS_2; j++) {
				int[][] subSrc = new int[KERNAL_WIDTH][KERNAL_HEIGHT];
				for (int k = 0; k < KERNAL_WIDTH; k++) {
					for (int l = 0; l < KERNAL_HEIGHT; l++) {
						subSrc[k][l] = src.getPixel(i - 1 + k, j - 1 + l);
					}
				}
				int subSumA = 0;
				int subSumR = 0;
				int subSumG = 0;
				int subSumB = 0;

				for (int k = 0; k < KERNAL_WIDTH; k++) {
					for (int l = 0; l < KERNAL_HEIGHT; l++) {
						subSumA += Color.alpha(subSrc[k][l]) * knl[k][l];
						subSumR += Color.red(subSrc[k][l]) * knl[k][l];
						subSumG += Color.green(subSrc[k][l]) * knl[k][l];
						subSumB += Color.blue(subSrc[k][l]) * knl[k][l];
					}
				}

				if (subSumA < 0) {
					subSumA = 0;
				} else if (subSumA > 255) {
					subSumA = 255;
				}

				if (subSumR < 0) {
					subSumR = 0;
				} else if (subSumR > 255) {
					subSumR = 255;
				}

				if (subSumG < 0) {
					subSumG = 0;
				} else if (subSumG > 255) {
					subSumG = 255;
				}

				if (subSumB < 0) {
					subSumB = 0;
				} else if (subSumB > 255) {
					subSumB = 255;
				}

				dest.setPixel(i, j,
						Color.argb(subSumA, subSumR, subSumG, subSumB));
			}
		}

		return dest;
	}

	public File savePhoto(Bitmap bmp) {
		File imageFileFolder = new File(
				Environment.getExternalStorageDirectory(), "UCRinstagram");
		imageFileFolder.mkdir();
		FileOutputStream out = null;
		Calendar c = Calendar.getInstance();
		String date = fromInt(c.get(Calendar.MONTH))
				+ fromInt(c.get(Calendar.DAY_OF_MONTH))
				+ fromInt(c.get(Calendar.YEAR))
				+ fromInt(c.get(Calendar.HOUR_OF_DAY))
				+ fromInt(c.get(Calendar.MINUTE))
				+ fromInt(c.get(Calendar.SECOND));
		File imageFileName = new File(imageFileFolder, date.toString() + ".jpg");

		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
			imageFileName.createNewFile();
			out = new FileOutputStream(imageFileName);
			// out.flush();
			out.write(bytes.toByteArray());
			out.close();
			out = null;
			return imageFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return imageFileName;
		}
	}

	public String fromInt(int val) {
		return String.valueOf(val);
	}

	public void clickDone(View view) {
		File path = savePhoto(afterBmp);
		filePath = path.getAbsolutePath();
		Intent myIntent = new Intent(this, PostPicture.class);
		myIntent.putExtra("picture", filePath);
		startActivity(myIntent);
	}

	public void clickNormal(View view) {
		afterBmp = beforeBmp;
		myImage2.setImageBitmap(afterBmp);
	}

	public void clickSepia(View view) {
		afterBmp = ConvertToSepia(beforeBmp);
		myImage2.setImageBitmap(afterBmp);
	}

	public void clickBlackNWhite(View view) {
		afterBmp = ConvertToBlackNWhite(beforeBmp);
		myImage2.setImageBitmap(afterBmp);
	}

	public void clickSharpen(View view) {
		afterBmp = processingBitmap(beforeBmp, kernalBlur);
		myImage2.setImageBitmap(afterBmp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_filter, menu);
		return true;
	}

}
