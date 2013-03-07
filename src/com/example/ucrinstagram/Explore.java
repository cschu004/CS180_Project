package com.example.ucrinstagram;

import java.io.InputStream;
import java.util.concurrent.Executor;

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

import com.example.ucrinstagram.Models.Photo;


public class Explore extends Activity {

	String username=Login.username.toLowerCase().replaceAll("\\s","");
	String [] allLinks;
	Photo[] allPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);
        WebAPI api = new WebAPI();
        allPhoto = api.getAllPhotos();
		allLinks = new String [allPhoto.length];
		for (int i = 0; i < allPhoto.length; i ++){
			System.out.println(allPhoto[i].path+'/'+allPhoto[i].filename);
			allLinks[i]=allPhoto[i].path+'/'+allPhoto[i].filename;
		}
		
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute(allLinks[0]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView01))
        .execute(allLinks[1]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView02))
        .execute(allLinks[2]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView03))
        .execute(allLinks[3]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView04))
        .execute(allLinks[4]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView06))
        .execute(allLinks[5]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView08))
        .execute(allLinks[6]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView07))
        .execute(allLinks[7]);
		new DownloadImageTask((ImageView) findViewById(R.id.ImageView05))
        .execute(allLinks[8]);	
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
    
    public void imageClick1(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
    	String link1 = allLinks[0];
    	String cap	= allPhoto[0].caption;
    	int photoid = allPhoto[0].getId();
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	intent.putExtra("photoid", photoid);
    	startActivity(intent);    	
    }
    
    public void imageClick2(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[1];
    	String cap	= allPhoto[1].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick3(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[2];
    	String cap	= allPhoto[2].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick4(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[3];
    	String cap	= allPhoto[3].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick5(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[4];
    	String cap	= allPhoto[4].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick6(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[8];
    	String cap	= allPhoto[8].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick7(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[5];
    	String cap	= allPhoto[5].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick8(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[7];
    	String cap	= allPhoto[7].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
    	startActivity(intent);    	
    }
    
    public void imageClick9(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = allLinks[6];
    	String cap	= allPhoto[6].caption;
    	intent.putExtra("link", link1);
    	intent.putExtra("caption", cap);
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
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 5;
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
