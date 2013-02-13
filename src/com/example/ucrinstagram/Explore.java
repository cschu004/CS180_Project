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


public class Explore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute("http://farm9.staticflickr.com/8169/8042410447_cf6a9e6a8e.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView01))
        .execute("http://farm9.staticflickr.com/8457/7885797166_2c48f6e8e6.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView02))
        .execute("http://farm9.staticflickr.com/8158/7513032270_2ca44fd224.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView03))
        .execute("http://farm9.staticflickr.com/8144/7513020030_01abc535da_b.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView04))
        .execute("http://farm6.staticflickr.com/5192/7187102038_cd979bee1b_z.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView06))
        .execute("http://farm9.staticflickr.com/8155/7105388393_b5b873a40f_b.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView08))
        .execute("http://farm6.staticflickr.com/5338/7044525825_e208803c67_c.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView07))
        .execute("http://farm8.staticflickr.com/7140/6898418202_11aefd88f6_c.jpg");
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView05))
        .execute("http://ideabastard.com/sites/default/files/images/instagram.thumbnail.jpeg");
	}

    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_explore, menu);
		return true;
	}
	
    public void home(View view){
    	Intent intent = new Intent(this, HomeScreen.class);
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
	
	public void refresh(View view){
		finish();
		startActivity(getIntent());
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
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
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
