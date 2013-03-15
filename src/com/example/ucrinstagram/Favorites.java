package com.example.ucrinstagram;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

public class Favorites extends Activity {
	String caption = null;
	String link1 = "";
	String link2 = "";
	public static String username = Login.username.toLowerCase().replaceAll(
			"\\s", "");
	InputStream is;
	User user1;

	ImageView[] image;
	Photo[] favoritePhotos;
	ImageView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
		user1 = new User(username);
		view = new ImageView(this);

		favoritePhotos = user1.getFavorites();
		if (favoritePhotos.length > 0) {
			image = new ImageView[favoritePhotos.length];
			for (int i = favoritePhotos.length - 1; i >= 0; i--) {
				image[i] = new ImageView(this);
				image[i].setImageResource(R.drawable.loader);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						600);
				image[i].setLayoutParams(lp);
				image[i].setOnLongClickListener(new myLongListener());

				LinearLayout linlay = (LinearLayout) findViewById(R.id.linearLayoutWithLotofContent);

				linlay.addView(image[i]);

				System.out.println(favoritePhotos[i].path + '/'
						+ favoritePhotos[i].filename);
				DownloadImageTask task = new DownloadImageTask(image[i]);
				task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						favoritePhotos[i].path + '/'
								+ favoritePhotos[i].filename);
			}

		} else {
			Toast.makeText(this, "No Favorites added yet", Toast.LENGTH_SHORT).show();
		}
	}

	private class myLongListener implements View.OnLongClickListener {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			for (int i = favoritePhotos.length - 1; i >= 0; i--) {
				if (v == image[i]) {
					Toast toast = new Toast(getApplicationContext());
					view.setImageResource(R.drawable.brokenheart);
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(view);
					toast.show();
					new User(username).removeFavorite(new Photo(
							favoritePhotos[i].getId()));
				}
				finish();
				// onCreate(null);
				Intent intent = getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				// startActivity(getIntent());

			}
			return true;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_home_screen, menu);
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
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				InputStream in = new java.net.URL(urldisplay).openStream();
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
