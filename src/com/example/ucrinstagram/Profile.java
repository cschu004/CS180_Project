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
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity {
    //final int TAKE_PICTURE = 1;
	private static final int ACTIVITY_SELECT_IMAGE = 1234;
//    private String selectedImagePath;
//    private ImageView img;

	String username="apple4life";
	InputStream is; 
    ArrayList<String> image_links2 = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        TextView usernametv = (TextView) findViewById(R.id.username);
        TextView nicknametv = (TextView) findViewById(R.id.nickname);
        TextView gendertv = (TextView) findViewById(R.id.gender);
        TextView biotv = (TextView) findViewById(R.id.aboutme);
        SharedPreferences sharedPrefs = getSharedPreferences("tempUsername", 0);
        SharedPreferences defSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPrefs.getString("username", "username");
        String nickname = defSharedPrefs.getString("nickname", "nickname");
        String gender = defSharedPrefs.getString("listpref", "gender");
        String bio = defSharedPrefs.getString("aboutme", "About Me");
        
        usernametv.setText(username);
        nicknametv.setText(nickname);
        gendertv.setText(gender);
        biotv.setText(bio);
        
		
		
        // Loader image - will be shown before loading image
        int loader = R.drawable.loader;
 
        // Imageview to show
        ImageView image = (ImageView) findViewById(R.id.image);
 
        // Image url
        String image_url = "http://api.androidhive.info/images/sample.jpg";
 
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
 
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(image_url, loader, image);
		
//		WebView myWebView = (WebView) findViewById(R.id.webview);                   
//		myWebView.loadUrl("http://img191.imageshack.us/img191/7379/tronlegacys7i7wsjf.jpg");
        new getAllImages().execute();
//        new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute("http://api.androidhive.info/images/sample.jpg");
        
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		TextView usernametv = (TextView) findViewById(R.id.username);
        TextView nicknametv = (TextView) findViewById(R.id.nickname);
        TextView gendertv = (TextView) findViewById(R.id.gender);
        TextView biotv = (TextView) findViewById(R.id.aboutme);
        SharedPreferences sharedPrefs = getSharedPreferences("tempUsername", 0);
        SharedPreferences defSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPrefs.getString("username", "username");
        String nickname = defSharedPrefs.getString("nickname", "nickname");
        String gender = defSharedPrefs.getString("listpref", "gender");
        String bio = defSharedPrefs.getString("aboutme", "About Me");
        
        usernametv.setText(username);
        nicknametv.setText(nickname);
        gendertv.setText(gender);
        biotv.setText(bio);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile, menu);
		return true;
	}
	

	
    public void home(View view){
    	Intent intent = new Intent(this, HomeScreen.class);
    	startActivity(intent);    	
    }
	
    public void explore(View view){
    	Intent intent = new Intent(this, Explore.class);
    	startActivity(intent);    	
    }
    
    public void camera(View view){
    	Intent intent = new Intent(this, Camera.class);
    	startActivity(intent);    	
    }
    
    public void settings(View view){
    	Intent intent = new Intent(this, PrefsActivity.class);
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
    	String link1 = image_links2.get(5);
    	intent.putExtra("link", link1);
    	startActivity(intent);
    }
    
    public void logout(){
        // Clearing all data from Shared Preferences
        SharedPreferences settings = getSharedPreferences("DB_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("user");
        editor.remove("pass");
        editor.clear();
        editor.commit();
        
    	Intent intent = new Intent(this, Login.class);
    	startActivity(intent);    	
    }
    
    public void startGallery(View view){
    	Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture for UCRinstagram"),ACTIVITY_SELECT_IMAGE);
		//done();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
     //Bitmap bmp = null;
        if(resultCode == RESULT_OK && requestCode == ACTIVITY_SELECT_IMAGE){
        	//Upload the picture and associate it with the proper account

        	//reload the profile page
        	Intent intent = new Intent(this, Profile.class);
        	startActivity(intent);  
        	
        	
//            Uri selectedImageUri = data.getData();
//            selectedImagePath = getPath(selectedImageUri);
//            System.out.println("Image Path : " + selectedImagePath);
//            img.setImageURI(selectedImageUri);
////        }
//        new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute(selectedImagePath);

   }

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
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView01))
			.execute(image_links2.get(1));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView02))
			.execute(image_links2.get(2));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView03))
			.execute(image_links2.get(3));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView04))
			.execute(image_links2.get(4));
			new DownloadImageTask((ImageView) findViewById(R.id.ImageView05))
			.execute(image_links2.get(5));
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
