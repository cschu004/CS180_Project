package com.example.ucrinstagram;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class SinglePicture extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_picture);
		
	    String link = getIntent().getExtras().getString("link");
	    
	    String prefix = "https://s3.amazonaws.com/";
	    String noPrefixStr = link.substring(link.indexOf(prefix)+ prefix.length());
	    String [] tokens = noPrefixStr.split("/");

		TextView textView = (TextView)findViewById(R.id.homescreen_list_element_comments);
		textView.setText(tokens[1]);
		

		TextView textView2 = (TextView)findViewById(R.id.textView2);
		textView2.setText(tokens[2]);
		
		new DownloadImageTask((ImageView) findViewById(R.id.homescreen_list_element_image))
        .execute(link);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_picture, menu);
		return true;
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
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
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
