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

	String username = Login.username.toLowerCase().replaceAll("\\s", "");
	String[] allLinks;
	Photo[] allPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);
		WebAPI api = new WebAPI();
		allPhoto = api.getAllPhotos();
		allLinks = new String[allPhoto.length];
		for (int i = 0; i < allPhoto.length; i++) {
			System.out.println(allPhoto[i].path + '/' + allPhoto[i].filename);
			allLinks[i] = allPhoto[i].path + '/' + allPhoto[i].filename;
		}
		DownloadImageTask task = new DownloadImageTask((ImageView) findViewById(R.id.imageView1));
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[0]);
		DownloadImageTask task1 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView01));
		task1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[1]);
		DownloadImageTask task2 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView02));
		task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[2]);
		DownloadImageTask task3 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView03));
		task3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[3]);
		DownloadImageTask task4 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView04));
		task4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[4]);
		DownloadImageTask task5 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView06));
		task5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[5]);
		DownloadImageTask task6 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView08));
		task6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[6]);
		DownloadImageTask task7 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView07));
		task7.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[7]);
		DownloadImageTask task8 = new DownloadImageTask((ImageView) findViewById(R.id.ImageView05));
		task8.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,allLinks[8]);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_explore, menu);
		return true;
	}

	public void home(View view) {
		Intent intent = new Intent(this, HomeScreen.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
	}

	public void camera(View view) {
		Intent intent = new Intent(this, Camera.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
	}

	public void updates(View view) {
		Intent intent = new Intent(this, Updates.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		startActivity(intent);
	}

	public void profile(View view) {
		Intent intent = new Intent(this, Profile.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		startActivity(intent);
	}
	
	public void favorites(View view){
		Intent intent = new Intent(this, Favorites.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

		startActivity(intent);
	}

	public void imageClick1(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[0];
		String cap = allPhoto[0].caption;
		String gps = allPhoto[0].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[0].getId();
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick2(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[1];
		String cap = allPhoto[1].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[1].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[1].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick3(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[2];
		String cap = allPhoto[2].caption;
		intent.putExtra("link", link1);
		String gps = allPhoto[2].gps;
		intent.putExtra("gps", gps);
		intent.putExtra("caption", cap);
		int photoid = allPhoto[2].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick4(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[3];
		String cap = allPhoto[3].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[3].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[3].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick5(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[4];
		String cap = allPhoto[4].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[4].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[4].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick6(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[8];
		String cap = allPhoto[8].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[8].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[8].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick7(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[5];
		String cap = allPhoto[5].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[5].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[5].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick8(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[7];
		String cap = allPhoto[7].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[7].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[7].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void imageClick9(View view) {
		Intent intent = new Intent(this, SinglePicture.class);
		String link1 = allLinks[6];
		String cap = allPhoto[6].caption;
		intent.putExtra("link", link1);
		intent.putExtra("caption", cap);
		String gps = allPhoto[6].gps;
		intent.putExtra("gps", gps);
		int photoid = allPhoto[6].getId();
		intent.putExtra("photoid", photoid);
		startActivity(intent);
	}

	public void refresh(View view) {
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
				mIcon11 = BitmapFactory.decodeStream(in, null, options);
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
