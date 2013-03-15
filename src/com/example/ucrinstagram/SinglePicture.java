package com.example.ucrinstagram;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

public class SinglePicture extends Activity {
	String username = Login.username.toLowerCase().replaceAll("\\s", "");
	InputStream is;
	String link;
	String caption;
	String gps;
	int photoId;
	String[] tokens;
	ImageView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_picture);

		link = getIntent().getExtras().getString("link");
		caption = getIntent().getExtras().getString("caption");
		photoId = getIntent().getExtras().getInt("photoid");
		gps = getIntent().getExtras().getString("gps");

		String prefix = "https://s3.amazonaws.com/";
		String noPrefixStr = link.substring(link.indexOf(prefix)
				+ prefix.length());
		tokens = noPrefixStr.split("/");

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText(tokens[1]);
		textView.setTextSize(18);
		textView.setTextColor(Color.BLUE);

		TextView textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText(caption);

		TextView textView3 = (TextView) findViewById(R.id.textView3);
		textView3.setText(gps);

		/*
		 * WebAPI api = new WebAPI(); Photo tempPhoto = api.getPhoto(photoId);
		 * 
		 * Comment[] pComments = tempPhoto.getComments(); for(int k = 0; k <
		 * pComments.length; k++){ String tmp = pComments[k].body + "\n";
		 * System.out.println(tmp); }
		 */
		DownloadImageTask task = new DownloadImageTask(
				(ImageView) findViewById(R.id.imageView1));
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, link);
		// new DownloadImageTask((ImageView)
		// findViewById(R.id.imageView1)).execute(link);
		ImageView i = (ImageView) findViewById(R.id.imageView1);
		i.setOnLongClickListener(new myLongListener());
		view = new ImageView(this);

	}

	private class myLongListener implements View.OnLongClickListener {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub

			Toast toast = new Toast(getApplicationContext());
			view.setImageResource(R.drawable.heart);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(view);
			toast.show();
			new User(username).addFavorite(new Photo(photoId));
			return true;
		}

	}

	public void profileOther(View view) {
		Intent intent = new Intent(this, ProfileOther.class);
		intent.putExtra("username", tokens[1]);
		startActivity(intent);
	}

	public void addFavorite(View view) {
		new User(Login.username).addFavorite(new Photo(photoId));
		Toast.makeText(this.getApplicationContext(),
				"Adding photo: " + caption, Toast.LENGTH_LONG).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_picture, menu);
		return true;
	}

	public void delete(View view) {
		System.out.println(username);
		System.out.println(tokens[1]);
		if (username.equals(tokens[1])) {
			WebAPI api = new WebAPI();
			Photo tempPhoto = api.getPhoto(photoId);
			tempPhoto.deletePhoto();
			Toast.makeText(this.getApplicationContext(), "Deleted",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this.getApplicationContext(),
					"You don't own this picture", Toast.LENGTH_LONG).show();
		}
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
