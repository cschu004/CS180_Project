package com.example.ucrinstagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Filter extends Activity {
	String filePath;
	ImageView myImage2;
	Bitmap bmp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
	    filePath = getIntent().getExtras().getString("picture");
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		bmp = BitmapFactory.decodeFile(filePath,options);
        myImage2 = (ImageView) findViewById(R.id.imageView1);
        myImage2.setScaleType(ScaleType.FIT_XY);
        //Drawable d = new BitmapDrawable(getResources(),bmp);
        myImage2.setImageBitmap(bmp);
	}
	
	public Bitmap ConvertToSepia(Bitmap sampleBitmap){
		ColorMatrix sepiaMatrix =new ColorMatrix();
		float[] sepMat={0.3930000066757202f, 0.7689999938011169f, 0.1889999955892563f, 0, 0,
						0.3490000069141388f, 0.6859999895095825f, 0.1679999977350235f, 0, 0, 
						0.2720000147819519f, 0.5339999794960022f, 0.1309999972581863f, 0, 0, 
						0, 0, 0, 1, 0,
						0, 0, 0, 0, 1};
		sepiaMatrix.set(sepMat);
		final ColorMatrixColorFilter colorFilter= new ColorMatrixColorFilter(sepiaMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint=new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas =new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}
	
	public Bitmap ConvertToBlackNWhite(Bitmap sampleBitmap){
		ColorMatrix sepiaMatrix =new ColorMatrix();
		float[] sepMat={0.33f, 0.59f, 0.11f, 0, 0,
						0.33f, 0.59f, 0.11f, 0, 0, 
						0.33f, 0.59f, 0.11f, 0, 0, 
						0, 0, 0, 1, 0,
						0, 0, 0, 0, 1};
		sepiaMatrix.set(sepMat);
		final ColorMatrixColorFilter colorFilter= new ColorMatrixColorFilter(sepiaMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint=new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas =new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}
	public void clickDone(View view){
    	Intent myIntent = new Intent(this, PostPicture.class);
    	myIntent.putExtra("picture", filePath);
    	startActivity(myIntent);
	}
	
	public void clickNormal(View view){
        myImage2.setImageBitmap(bmp);
	}
	
	public void clickSepia(View view){
        Bitmap test = ConvertToSepia(bmp);
        myImage2.setImageBitmap(test);
	}
	
	public void clickBlackNWhite(View view){
        Bitmap test = ConvertToBlackNWhite(bmp);
        myImage2.setImageBitmap(test);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_filter, menu);
		return true;
	}

}
