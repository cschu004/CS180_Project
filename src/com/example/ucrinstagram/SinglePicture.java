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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SinglePicture extends Activity {
	String username=Login.username;
	InputStream is; 
	String link;
	String [] tokens;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_picture);
		
	    link = getIntent().getExtras().getString("link");
	    
	    String prefix = "https://s3.amazonaws.com/";
	    String noPrefixStr = link.substring(link.indexOf(prefix)+ prefix.length());
	    tokens = noPrefixStr.split("/");

		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(tokens[1]);
		

		TextView textView2 = (TextView)findViewById(R.id.textView2);
		textView2.setText(tokens[2]);
		
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute(link);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_picture, menu);
		return true;
	}
	
	public void delete(View view){
		System.out.println(username);
		System.out.println(tokens[1]);
		if (username.equals(tokens[1])){
			System.out.println("IN");
			new deleteImage().execute();
		}
	}
	private class deleteImage extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			String result = "";

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			System.out.println(link);
			nameValuePairs.add(new BasicNameValuePair("image_url",link));

			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://www.kevingouw.com/cs180/deleteImage.php");
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
			        }

			}
			catch(JSONException e){
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}
        	
			return null;

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
