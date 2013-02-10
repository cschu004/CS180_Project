package com.example.ucrinstagram;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class PostPicture extends Activity {

    private AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( "AKIAJVITZZSHZ4EDHDMA", "" ) );                    
    String filePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_picture);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	
	    filePath = getIntent().getExtras().getString("picture");
		System.out.println("TEST: "+filePath);
    	Bitmap bmp = BitmapFactory.decodeFile(filePath);
        ImageView myImage2 = (ImageView) findViewById(R.id.imageView1);
        myImage2.setScaleType(ScaleType.FIT_XY);
        myImage2.setImageBitmap(bmp);
        
	}

	public void clickShare(View view){
        new S3PutObjectTask().execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_post_picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class S3PutObjectTask extends AsyncTask<Void,Void,Void>{
		
		@Override
		protected Void doInBackground(Void... arg0) {
			s3Client.createBucket("ucrinstagram");
			PutObjectRequest por = new PutObjectRequest("ucrinstagram","pictureName",new java.io.File(filePath));
			s3Client.putObject(por);
			return null;
		}
	}
	
}
