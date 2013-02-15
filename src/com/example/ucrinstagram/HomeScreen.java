package com.example.ucrinstagram;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.widget.Toast;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.AlertDialog;
import com.example.ucrinstagram.Models.User;

public class HomeScreen extends Activity {
	String caption=null;
	String link1="";
	String link2="";
	String username="apple4life";
	InputStream is;

    ArrayList<String> image_links2 = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

        new getAllImages().execute();

		new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
        .execute(link2);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(caption);

        //testing out creating a new user
        User user1 =  new User("Oliver", "Chou",
                "oliver@mgxcopy.com", "MGX", "mgxtech");

        //testing out get request + gson
        User user2 =  new User(2);

        Toast.makeText(this.getApplicationContext(), user2.firstname, Toast.LENGTH_LONG).show();

        new AlertDialog.Builder(this)
                .setTitle("User 2")
                .setMessage(user2.username)
                .show();
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

	private class getAllImages extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			String result = "";

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user",username));

			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://www.kevingouw.com/cs180/getAllImages.php");
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
			                image_links2.add(json_data.getString("image_url"));
			        }
	                System.out.println(image_links2.get(0));

			}
			catch(JSONException e){
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}
        	System.out.print("RETURN");

			return null;

		}
		protected void onPostExecute(Void Result){
			new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
			.execute(image_links2.get(0));
			new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
			.execute(image_links2.get(1));
			//TextView textView = (TextView)findViewById(R.id.textView1);
			//textView.setText(caption);
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
