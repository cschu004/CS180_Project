package com.example.ucrinstagram;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeScreen extends Activity {
	String caption=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
	    caption = getIntent().getExtras().getString("caption");
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute("https://s3.amazonaws.com/ucrinstagram/"+caption);
        TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setText(caption);
        //===
		new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
        .execute("https://s3.amazonaws.com/ucrinstagram/"+caption);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(caption);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_screen, menu);
		return true;
	}
	
    public void explore(View view){
    	Intent intent = new Intent(this, Explore.class);
    	startActivity(intent);    	
    }
    
    public void camera(View view){
    	Intent intent = new Intent(this, Camera.class);
    	startActivity(intent);    	
    }
    
    public void profile(View view){
    	Intent intent = new Intent(this, Profile.class);
    	startActivity(intent);    	
    }
    

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		  		BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 5;
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in,null,options);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
	}
}
