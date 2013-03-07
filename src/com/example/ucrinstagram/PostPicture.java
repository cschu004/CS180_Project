package com.example.ucrinstagram;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

public class PostPicture extends Activity {

	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials("",
					""));

	String filePath;
	EditText et;

	public String username = Login.username.toLowerCase().replaceAll(
			"\\s", "");
	final String s3Link = "https://s3.amazonaws.com/ucrinstagram/";
	String caption;
	String link;
	String fileName;
	String gps_city="";
	User user1;

	private TextView latituteField;
	private TextView longitudeField, cityField;
	private double lat;
	private double lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_picture);
		// Show the Up button in the action bar.
		// getActionBar().setDisplayHomeAsUpEnabled(true);

		// Toast.makeText(this.getApplicationContext(),username,
		// Toast.LENGTH_LONG).show();
		user1 = new User(username);
		Toast.makeText(this.getApplicationContext(), username,
				Toast.LENGTH_LONG).show();

		filePath = getIntent().getExtras().getString("picture");

		String[] tokens = filePath.split("/");
		fileName = tokens[tokens.length - 1];

		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 8;
		Bitmap bmp = BitmapFactory.decodeFile(filePath);
		ImageView myImage2 = (ImageView) findViewById(R.id.imageView1);
		myImage2.setScaleType(ScaleType.FIT_XY);
		myImage2.setImageBitmap(bmp);
		et = (EditText) findViewById(R.id.editText1);

		latituteField = (TextView) findViewById(R.id.textView1);
		longitudeField = (TextView) findViewById(R.id.textView2);
		cityField = (TextView) findViewById(R.id.textView3);
		latituteField.setText("");
		longitudeField.setText("");
		cityField.setText("");
	}

	public void clickShareLocation(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		switch (view.getId()) {
		case R.id.checkBox1:
			if (checked)
				getLocation();
			else {
				latituteField.setText("");
				longitudeField.setText("");
				cityField.setText("");
			}

		}
	}

	private void getLocation() {
		// Get the location manager
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);

		LocationListener loc_listener = new LocationListener() {
			public void onLocationChanged(Location l) {
			}

			public void onProviderEnabled(String p) {
			}

			public void onProviderDisabled(String p) {
			}

			public void onStatusChanged(String p, int status, Bundle extras) {
			}
		};
		locationManager
				.requestLocationUpdates(bestProvider, 0, 0, loc_listener);
		location = locationManager.getLastKnownLocation(bestProvider);

		try {
			lat = location.getLatitude();
			lon = location.getLongitude();
			latituteField.setText(Double.toString(lat));
			longitudeField.setText(Double.toString(lon));
			Geocoder gcd = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses;
				addresses = gcd.getFromLocation(lat, lon, 1);
				if (addresses.size() > 0) {
					gps_city = addresses.get(0).getLocality();
					cityField.setText(addresses.get(0).getLocality());
				} else
					cityField.setText("Unable to deteremine your location.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			lat = -1.0;
			lon = -1.0;
		}
	}

	public void clickShare(View view) {

		caption = et.getText().toString();
		link = s3Link + username;

		Photo photo1 = new Photo(link, fileName, caption);
		photo1.gps = gps_city;
		user1.addPhoto(photo1);

		new S3PutObjectTask().execute();

		Intent intent = new Intent(this, HomeScreen.class);
		startActivity(intent);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class S3PutObjectTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			s3Client.createBucket("ucrinstagram");
			PutObjectRequest por = new PutObjectRequest("ucrinstagram",
					username + "/" + fileName, new java.io.File(filePath));
			por.setCannedAcl(CannedAccessControlList.PublicRead);
			s3Client.putObject(por);
			return null;
		}
	}

}
