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


public class Explore extends Activity {

	String username="apple4life";
	InputStream is; 
    ArrayList<String> image_links2 = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);
        new getRandomImages().execute();
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
    	String link1 = image_links2.get(0);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick2(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(1);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick3(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(2);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick4(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(3);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick5(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(4);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick6(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(8);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick7(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(5);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick8(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(7);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
    
    public void imageClick9(View view){
    	Intent intent = new Intent(this, SinglePicture.class);
       	String link1 = image_links2.get(6);
    	intent.putExtra("link", link1);
    	startActivity(intent);    	
    }
	
	public void refresh(View view){
		finish();
		startActivity(getIntent());
	}
	
	
	private class getRandomImages extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			String result = "";

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://www.kevingouw.com/cs180/getRandomImages.php");
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
			                System.out.println(json_data.getString("image_url"));
			        }

			}
			catch(JSONException e){
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}
        	
			return null;

		}
		protected void onPostExecute(Void Result){
			
			new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
	        .execute(image_links2.get(0));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView01))
	        .execute(image_links2.get(1));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView02))
	        .execute(image_links2.get(2));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView03))
	        .execute(image_links2.get(3));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView04))
	        .execute(image_links2.get(4));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView06))
	        .execute(image_links2.get(5));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView08))
	        .execute(image_links2.get(6));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView07))
	        .execute(image_links2.get(7));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView05))
	        .execute(image_links2.get(8));
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
