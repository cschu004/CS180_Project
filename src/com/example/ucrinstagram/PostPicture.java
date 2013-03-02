package com.example.ucrinstagram;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class PostPicture extends Activity {

    private AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( "", "" ) );                    
    String filePath;
    EditText et;
	InputStream is; 
	
	public static String username=HomeScreen.username;
    String caption;
    String link;

    ArrayList<String> image_links = new ArrayList<String>();
    
    private TextView latituteField;
    private TextView longitudeField;
    private double lat;
    private double lon;
    //private String provider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_picture);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	
	    filePath = getIntent().getExtras().getString("picture");
		System.out.println("TEST: "+filePath);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
    	Bitmap bmp = BitmapFactory.decodeFile(filePath,options);
        ImageView myImage2 = (ImageView) findViewById(R.id.imageView1);
        myImage2.setScaleType(ScaleType.FIT_XY);
        myImage2.setImageBitmap(bmp);
        et = (EditText)findViewById(R.id.editText1);
        
        
        latituteField = (TextView) findViewById(R.id.textView1);
        longitudeField = (TextView) findViewById(R.id.textView2);
        getLocation ();
	}	
	  private void getLocation ()
	  {
	    // Get the location manager
	    LocationManager locationManager = (LocationManager)getSystemService (LOCATION_SERVICE);
	    Criteria criteria = new Criteria ();
	    String bestProvider = locationManager.getBestProvider (criteria, false);
	    Location location = locationManager.getLastKnownLocation (bestProvider);

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
	locationManager.requestLocationUpdates(bestProvider,0 ,0, loc_listener);
	location = locationManager.getLastKnownLocation (bestProvider);   

	try
	{
	   lat = location.getLatitude ();
	   lon = location.getLongitude ();
	   latituteField.setText(Double.toString(lat));
	   longitudeField.setText(Double.toString(lon));
	}
	catch (NullPointerException e)
	{
	  lat = -1.0;
	  lon = -1.0;
	}
	  }
	
	public void clickShare(View view){
        System.out.println(et.getText().toString());
        caption = et.getText().toString();
        link = "https://s3.amazonaws.com/ucrinstagram/"+username+"/"+caption;
        
        new S3PutObjectTask().execute();
        new getjSON().execute();

    	Intent intent = new Intent(this,HomeScreen.class);
    	intent.putStringArrayListExtra("links", image_links);
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
			PutObjectRequest por = new PutObjectRequest("ucrinstagram",username+"/"+caption,new java.io.File(filePath));
			por.setCannedAcl(CannedAccessControlList.PublicRead);
			s3Client.putObject(por);
			return null;
		}
	}
	
	private class getjSON extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			String result = "";
			//the year data to send
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user",username));
			nameValuePairs.add(new BasicNameValuePair("password","password123"));
			nameValuePairs.add(new BasicNameValuePair("gender","1"));

			nameValuePairs.add(new BasicNameValuePair("caption",caption));
			nameValuePairs.add(new BasicNameValuePair("image_url",link));

			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://www.kevingouw.com/cs180/addCaptionAndLink.php");
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        is = entity.getContent();

			}
			catch(Exception e){
			        Log.e("log_tag", "Error in http connection "+e.toString());
			}
			//convert response to string
			try{

			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			        }
			        is.close();
			 
			        result=sb.toString();
			}catch(Exception e){
			        Log.e("log_tag", "Error converting result "+e.toString());
			}
			 
			//parse json data
			try{
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);
			               /* Log.i("log_tag","id: "+json_data.getInt("id")+
			                        ", name: "+json_data.getString("user")+
			                        ", sex: "+json_data.getInt("sex")+
			                        ", birthyear: "+json_data.getInt("birthyear")
			                );*/
			                System.out.println("LINK:"+json_data.getString("image_url"));
			                image_links.add(json_data.getString("image_url"));
			        }
			}
			catch(JSONException e){
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}
			return null;
		}
	}
	
}
