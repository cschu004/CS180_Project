package com.example.ucrinstagram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeScreen extends Activity {
	String caption=null;
	String link1="";
	String link2="";
	public static String username= "apple4life";
	public static String password= "gb4gb1";
	InputStream is;
	
	Bitmap[] bitmapList = null;
	homeListElement[] hElements = null;
    ArrayList<String> image_links2 = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen2);
        new getAllImages().execute();
	}
	
	private class homeListElement{
		String user;
		Bitmap image;
		String caption;
		String comments;
		
		homeListElement(String user, Bitmap image, String caption, String comments){
			this.user = user;
			this.image = image;
			this.caption = caption;
			this.comments = comments;
		}
		
		public String getUser(){ return this.user; }
		public Bitmap getImage(){ return this.image; }
		public String getCaption(){ return this.caption; }
		public String getComments(){ return this.comments; }
	}
	
	private void inflateHomescreenList(homeListElement[] hElements){
		ListView lv = (ListView) findViewById(R.id.homescreen_data_list);
		lv.setAdapter(new HomeListAdapter(this, R.layout.home_screen_list_item, hElements));
	}
	
	private class HomeListAdapter extends ArrayAdapter<homeListElement>{
		private Context context;
		private int layoutResourceId;
		private homeListElement[] data = null;
		private LayoutInflater mLayoutInflater;
		
		public HomeListAdapter(Context context, int layoutResourceId, homeListElement[] data){
			super(context, layoutResourceId, data);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.data = data;
			this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(int position, View convertview, ViewGroup parent){
			
			View row = mLayoutInflater.inflate(R.layout.home_screen_list_item, parent, false);
			
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			homeListElement mElement = data[position];
			
			TextView userTextView = (TextView) row.findViewById(R.id.homescreen_list_element_user);
			ImageView imageView = (ImageView) row.findViewById(R.id.homescreen_list_element_image);
			TextView captionTextView = (TextView) row.findViewById(R.id.homescreen_list_element_caption);
			TextView commentsTextView = (TextView) row.findViewById(R.id.homescreen_list_element_comments);
			userTextView.setText(mElement.getUser());
			imageView.setImageBitmap(mElement.getImage());
			captionTextView.setText(mElement.getCaption());
			commentsTextView.setText(mElement.getComments());
		
			return row;
		}
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
    
    public void updates(View view){
    	Intent intent = new Intent(this, Updates.class);
    	startActivity(intent);
    }

    public void profile(View view){
    	Intent intent = new Intent(this, Profile.class);
    	startActivity(intent);
    }

	private class getAllImages extends AsyncTask<Void,Void,String[]>{
		private List<String> imageURLs = new ArrayList<String>();
			
		@Override
		protected String[] doInBackground(Void... arg0) {
			String result = "";

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user",username));
			nameValuePairs.add(new BasicNameValuePair("password", password));

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
			        Log.e("verbose", "post response: " + result);
			}catch(Exception e){
			        Log.e("log_tag", "Error converting result "+e.toString());
			}

			//parse json data
			try{
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);
			                imageURLs.add(json_data.getString("image_url"));
			                System.out.println(json_data.getString("image_url"));

			        }

			}
			catch(JSONException e){
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}
			
			String[] imageArray = new String[imageURLs.size()];
			imageURLs.toArray(imageArray);
			
			// Dummy urls are being passed because access denied from amazonaws for all pics except apple4life/nexus
			String[] dummyUrls = { "http://us.123rf.com/400wm/400/400/lenm/lenm0907/lenm090700067/5171841-computer-support-with-clipping-path.jpg",
				"http://us.123rf.com/400wm/400/400/shchipakin/shchipakin1110/shchipakin111000044/11432574-angry-businessman-pouring-cup-of-coffee-on-laptop-computer.jpg",
				"http://www.computerservices1.com/pictures/computer.png",
				"http://www.intelliadmin.com/images2/Homer%20Crash%20Computer.jpg"};
			
			
			return dummyUrls;

		}
		protected void onPostExecute(String[] result){
			new DownloadImageTask().execute(result);
		}

	}

	private class DownloadImageTask extends AsyncTask<String[], Void, Bitmap[]> {
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		  protected Bitmap[] doInBackground(String[]... urls) {
			  for(int i = 0; i < urls[0].length; i++){
			      String urldisplay = urls[0][i];
			      if(urldisplay != null){
				      Bitmap image = null;
				      try {
				  		BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
				        InputStream in = new java.net.URL(urldisplay).openStream();
				        image = BitmapFactory.decodeStream(in,null,options);
				        bitmaps.add(image);
				      } catch (FileNotFoundException e ){
				    	  Log.e("instagram error", e.getMessage());
				      } catch (Exception e) {
				          Log.e("Error", e.getMessage());
				          e.printStackTrace();
				      }
			      }
			  }
			  Bitmap[] bitmapArray = new Bitmap[bitmaps.size()];
			  bitmaps.toArray(bitmapArray);
		      return bitmapArray;
		  }

		  protected void onPostExecute(Bitmap[] result) {
		      bitmapList = result;
		      Log.e("verbose", "bitmapList size: " + result.length);
		      
		      //TODO get photos. the following are dummy photos
		      
		      //TODO get comments. the following are dummy comments
		      
		      //TODO get user
		      
		      //TODO get caption
		      
		      String[] dummyCaptions = {"Happy Computer", "Code Monkey", "Aleinware", "Techie Homer"};
		      String[] dummyUsers = {"Homer", "Bart", "Marge", "Moe"};
		      
		      // create homeListElements
		      homeListElement[] hArray = new homeListElement[result.length];
		      for(int i = 0; i < result.length; i++){
		    	  hArray[i] = new homeListElement(dummyUsers[i], result[i], dummyCaptions[i], "Comment: Nice photo!");
		      }
		      inflateHomescreenList(hArray);
		  }
	}



}