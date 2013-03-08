package com.example.ucrinstagram;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.ucrinstagram.Models.Comment;
import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
	
	private LruCache<String, Bitmap> mMemoryCache;
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		username = Login.username;
		
		buildHomescreen();
        }
	
	public void buildHomescreen(){
		//Used for image cache
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
	        }
	    };
		
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
				hlElements.add(new HomeListElement(pUser, pURL, photoID, commentsString));
			}			
		}
		HomeListElement[] hleArray = new HomeListElement[hlElements.size()];
		hlElements.toArray(hleArray);
        inflateHomescreenList(hleArray);
        
	}
	
	private class HomeListElement{
		String user;
		String imageURL;
		int imageID;
		String comments;
		boolean commentBoxVisible;
		
		HomeListElement(String user, String imageURL, int id, String comments){
			this.user = user;
			this.imageURL = imageURL;
			this.imageID = id;
			this.comments = comments;
			this.commentBoxVisible = false;
		}
	}
	
	private void inflateHomescreenList(HomeListElement[] hElements){
		mListView = (ListView) findViewById(R.id.homescreen_data_list);
		HomeListAdapter hla = new HomeListAdapter(this, R.layout.home_screen_list_item, hElements);
		mListView.setAdapter(hla);
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
			userTextView.setText(mElement.user);
			loadBitmap(mElement.imageURL, imageView, position);
			commentsTextView.setText(mElement.comments);
			
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
		buildHomescreen();
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
		int hlaIndex;
		
		DownloadPhotoTask(String url, int index){
			this.url = url;
			this.hlaIndex = index;
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
		
		protected void onPostExecute(Bitmap result){
			addBitmapToMemoryCache(url, result);
			ListView list = (ListView) mListView.findViewById(R.id.homescreen_data_list);
			int start = list.getFirstVisiblePosition();
			for(int i=start, j=list.getLastVisiblePosition();i<=j;i++)
			    if(hlaIndex == i){
			        View view = list.getChildAt(i-start);
			        ((ImageView) view.findViewById(R.id.homescreen_list_element_image)).setImageBitmap(result);
			        break;
			    }
		}
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }
	}
	
	public Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}
	public void loadBitmap(String key, ImageView mImageView, int index) {
	    final Bitmap bitmap = getBitmapFromMemCache(key);
	    if (bitmap != null) {
	        mImageView.setImageBitmap(bitmap);
	    } else {
	        mImageView.setImageResource(R.drawable.loader);
	        new DownloadPhotoTask(key, index).execute();
	    }
	}
}
