package com.example.ucrinstagram;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

public class Profile extends Activity {
    //final int TAKE_PICTURE = 1;
	private static final int ACTIVITY_SELECT_IMAGE = 1234;
//    private String selectedImagePath;
//    private ImageView img;

	public static String username = HomeScreen.username;
	ArrayList<String> image_links = new ArrayList<String>();
    ImageView[] image;
	
	InputStream is; 
    ArrayList<String> image_links2 = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
//        SharedPreferences sharedPrefs = getSharedPreferences("tempUsername", 0);
//        SharedPreferences defSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String usern = sharedPrefs.getString(username, "username");
//        String nickname = defSharedPrefs.getString("nickname", "nickname");
//        String gender = defSharedPrefs.getString("listpref", "gender");
//        String bio = defSharedPrefs.getString("aboutme", "About Me");
        
        TextView usernametv = (TextView) findViewById(R.id.username);
        TextView nicknametv = (TextView) findViewById(R.id.nickname);
        TextView gendertv = (TextView) findViewById(R.id.gender);
        TextView biotv = (TextView) findViewById(R.id.aboutme);
        TextView followerstv = (TextView) findViewById(R.id.followers);
        TextView datetv = (TextView) findViewById(R.id.profile_creation);
        TextView phototv = (TextView) findViewById(R.id.photos);
        TextView bdaytv = (TextView) findViewById(R.id.bday);
        
        User user1 = new User(username);
        String un = user1.username;
        String nickname = user1.getProfile().nickname;
        String gender = user1.getProfile().gender;
        String bio = user1.getProfile().bio;
        
        Date birthdate = user1.getProfile().birthday;
        String bday = "Birthday: " + birthdate;
        
        Date created = user1.getProfile().created_at;
        String prof_created = "Profile Created On:\n" + created;
        		
        User[] friends = user1.getFriends();
        int fCount = friends.length;
        String friendCount = fCount + " Friends";
        
        Photo[] userphotos = user1.getPhotos();
        int pCount = userphotos.length;
        String photoCount = pCount + " Photos";
        
        usernametv.setText(un);
        nicknametv.setText(nickname);
        gendertv.setText(gender);
        biotv.setText(bio);
        followerstv.setText(friendCount);
        datetv.setText(prof_created);
        phototv.setText(photoCount);
        bdaytv.setText(bday);
        

        //display user photos
//        for(int i = userphotos.length - 1; i >= 0; i--){
//        	System.out.println(userphotos[i].path + '/' + userphotos[i].filename);
//        	image_links.add(userphotos[i].path + '/' + userphotos[i].filename);
//        }

		image = new ImageView[userphotos.length];
    	for (int i = userphotos.length-1; i >=0; i --){
    		image[i] = new ImageView(this);
    		image[i].setImageResource(R.drawable.ic_launcher);
    		image[i].setAdjustViewBounds(true);
    		LayoutParams lp = new LayoutParams(400,400);
    		image[i].setLayoutParams(lp);
    		LinearLayout linlay = (LinearLayout) findViewById(R.id.linearPictures);
    		linlay.addView(image[i]);

//        	System.out.println(userphotos[i].path + '/' + userphotos[i].filename);
        	new DownloadImageTask(image[i]).execute(userphotos[i].path + '/' + userphotos[i].filename);
    	}

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
        //new getAllImages().execute();
//        new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute("http://api.androidhive.info/images/sample.jpg");
        
	}

	@Override
	public void onResume(){
		super.onResume();

        TextView usernametv = (TextView) findViewById(R.id.username);
        TextView nicknametv = (TextView) findViewById(R.id.nickname);
        TextView gendertv = (TextView) findViewById(R.id.gender);
        TextView biotv = (TextView) findViewById(R.id.aboutme);
        TextView followerstv = (TextView) findViewById(R.id.followers);
        TextView datetv = (TextView) findViewById(R.id.profile_creation);
        TextView phototv = (TextView) findViewById(R.id.photos);
        TextView bdaytv = (TextView) findViewById(R.id.bday);
        
        User user1 = new User(username);
        String un = user1.username;
        String nickname = user1.getProfile().nickname;
        String gender = user1.getProfile().gender;
        String bio = user1.getProfile().bio;
        
        Date birthdate = user1.getProfile().birthday;
        String bday = "Birthday: " + birthdate;
        
        Date created = user1.getProfile().created_at;
        String prof_created = "Profile Created On:\n" + created;
        		
        User[] friends = user1.getFriends();
        int fCount = friends.length;
        String friendCount = fCount + " Friends";
        
        Photo[] userphotos = user1.getPhotos();
        int pCount = userphotos.length;
        String photoCount = pCount + " Photos";
        
        usernametv.setText(un);
        nicknametv.setText(nickname);
        gendertv.setText(gender);
        biotv.setText(bio);
        followerstv.setText(friendCount);
        datetv.setText(prof_created);
        phototv.setText(photoCount);
        bdaytv.setText(bday);
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