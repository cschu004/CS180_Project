package com.example.ucrinstagram;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.ucrinstagram.Models.Comment;
import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeScreen extends Activity {
	
	// TODO Pull user name from login
	public static String username= "";
	public static String password= "";
	
	Bitmap[] bitmapList = null;
	HomeListElement[] hElements = null;
	InputStream is;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		username = Login.username;
		
		final User user = new User(username);
		User[] friends = user.getFriends();
		List<HomeListElement> hlElements = new ArrayList<HomeListElement>();
		for(int i = 0; i < friends.length; i++){
			Photo[] friendPhotos = friends[i].getPhotos();
			
			for(int j = 0; j < friendPhotos.length; j++){
				int photoID = friendPhotos[j].getId();
				String pUser = friends[i].username;
				String pURL = friendPhotos[j].path + "/" + friendPhotos[j].filename;
				Comment[] pComments = friendPhotos[j].getComments();
				String commentsString = "";
				for(int k = 0; k < pComments.length; k++){
					String tmp = pComments[k].body + "\n";
					commentsString += tmp;
				}
				Bitmap pBitmap = null;
				try {
					pBitmap = new DownloadPhotoTask(pURL).execute().get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hlElements.add(new HomeListElement(pUser, pBitmap, photoID, commentsString));
			}			
		}
		HomeListElement[] hleArray = new HomeListElement[hlElements.size()];
		hlElements.toArray(hleArray);
        inflateHomescreenList(hleArray);
        }
	
	private class HomeListElement{
		String user;
		Bitmap image;
		int imageID;
		String comments;
		boolean commentBoxVisible;
		
		HomeListElement(String user, Bitmap image, int id, String comments){
			this.user = user;
			this.image = image;
			this.imageID = id;
			this.comments = comments;
			this.commentBoxVisible = false;
		}
		
		public String getUser(){ return this.user; }
		public Bitmap getImage(){ return this.image; }
		public String getComments(){ return this.comments; }
	}
	
	private void inflateHomescreenList(HomeListElement[] hElements){
		ListView lv = (ListView) findViewById(R.id.homescreen_data_list);
		lv.setAdapter(new HomeListAdapter(this, R.layout.home_screen_list_item, hElements));
	}
	
	private class HomeListAdapter extends ArrayAdapter<HomeListElement>{
		private Context context;
		private int layoutResourceId;
		private HomeListElement[] data = null;
		private LayoutInflater mLayoutInflater;
		
		public HomeListAdapter(Context context, int layoutResourceId, HomeListElement[] data){
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
			
			final HomeListElement mElement = data[position];
			
			TextView userTextView = (TextView) row.findViewById(R.id.homescreen_list_element_user);
			ImageView imageView = (ImageView) row.findViewById(R.id.homescreen_list_element_image);
			final EditText editComment = (EditText) row.findViewById(R.id.homescreen_list_element_editComment);
			final Button postButton = (Button)	row.findViewById(R.id.homescreen_post_comment);
			final TextView commentsTextView = (TextView) row.findViewById(R.id.homescreen_list_element_comments);
			userTextView.setText(mElement.getUser());
			imageView.setImageBitmap(mElement.getImage());
			commentsTextView.setText(mElement.getComments());
			
			//TODO Hide till comment button click
			if(!mElement.commentBoxVisible){
				editComment.setVisibility(View.GONE);
				postButton.setVisibility(View.GONE);
			}
			
			
			Button commentButton = (Button) row.findViewById(R.id.homescreen_list_element_button_comment);
			commentButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mElement.commentBoxVisible = true;
					editComment.setVisibility(View.VISIBLE);
					postButton.setVisibility(View.VISIBLE);
				}
			});
			
			postButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String comment = editComment.getText().toString();
					Photo photo = new Photo(mElement.imageID);
					Comment mComment = new Comment(comment);
					photo.addComment(mComment);
					String previousComment = commentsTextView.getText().toString();
					comment = previousComment + "\n" + comment;
					commentsTextView.setText(comment);
					mElement.commentBoxVisible = false;
					editComment.setVisibility(View.GONE);
					postButton.setVisibility(View.GONE);
					
				}
			});
			
			Button favoriteButton = (Button) findViewById(R.id.homescreen_list_element_button_like);
			favoriteButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Photo photo = new Photo(mElement.imageID);
					User user = new User(username);
					user.addFavorite(photo);	
				}
			});
		
			return row;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home_screen, menu);
		return true;
	}
	
	public void onClickRefresh(MenuItem item){
		User user = new User(username);
		User[] friends = user.getFriends();
		List<HomeListElement> hlElements = new ArrayList<HomeListElement>();
		for(int i = 0; i < friends.length; i++){
			Photo[] friendPhotos = friends[i].getPhotos();
			
			for(int j = 0; j < friendPhotos.length; j++){
				int photoID = friendPhotos[j].getId();
				String pUser = friends[i].username;
				String pURL = friendPhotos[j].path + "/" + friendPhotos[j].filename;
				Comment[] pComments = friendPhotos[j].getComments();
				String commentsString = "";
				for(int k = 0; k < pComments.length; k++){
					String tmp = pComments[k].body + "\n";
					commentsString += tmp;
				}
				Bitmap pBitmap = null;
				try {
					pBitmap = new DownloadPhotoTask(pURL).execute().get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hlElements.add(new HomeListElement(pUser, pBitmap, photoID, commentsString));
			}			
		}
		HomeListElement[] hleArray = new HomeListElement[hlElements.size()];
		hlElements.toArray(hleArray);
        inflateHomescreenList(hleArray);
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

	private class DownloadPhotoTask extends AsyncTask<Void, Void, Bitmap>{
		String url;
		DownloadPhotoTask(String url){
			this.url = url;
		}
		@Override
		protected Bitmap doInBackground(Void...arg0){
			Bitmap image = null;
		      try {
		  		BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
		        InputStream in = new java.net.URL(url).openStream();
		        image = BitmapFactory.decodeStream(in,null,options);
		      } catch (FileNotFoundException e ){
		    	  Log.e("instagram error", e.getMessage());
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return image;
		}
	}
}
