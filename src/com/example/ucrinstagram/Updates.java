package com.example.ucrinstagram;

import java.util.ArrayList;
import java.util.List;

import com.example.ucrinstagram.Models.Photo;
import com.example.ucrinstagram.Models.User;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Updates extends ListActivity {

	private List<String> updatesArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updates);
		updatesArray = new ArrayList<String>();
		
		buildUpdateContent();

	}
	
	private void buildUpdateContent(){
		String username = Login.username;
		User user = new User(username);
		Photo[] favorites = user.getFavorites();
		User[] friends = user.getFriends();
		for(int i = 0; i < friends.length; i++){
			Photo[] photos = friends[i].getFavorites();
			if(photos.length > 0){
				String updates = "Friend: <font color=#3333FF>" + friends[i].username + "</font> uploaded "
						+ Integer.toString(photos.length) + " new photos.";
				updatesArray.add(updates);
			}
		}
		for(int i = 0; i < favorites.length; i++){
			String tmpString = "";
			if(favorites[i].getComments().length > 0){
				
				tmpString = "Favorite photo: <font color=#3333FF>" + favorites[i].caption + "</font> has " 
							+ Integer.toString(favorites[i].getComments().length) 
							+ " new comments.";
				updatesArray.add(tmpString);
			}
		}
		String[] aUpdate = new String[updatesArray.size()];
		updatesArray.toArray(aUpdate);
		setUpdateArray(aUpdate);
	}

	public void setUpdateArray(String[] array){
		setListAdapter(new UpdateListAdapter(this, android.R.layout.simple_list_item_1, array));
	}

	private class UpdateListAdapter extends ArrayAdapter<String>{
		private Context context;
		private int layoutResourceId;
		private String[] data = null;
		private LayoutInflater mLayoutInflater;
		
		public UpdateListAdapter(Context context, int layoutResourceId, String[] data){
			super(context, layoutResourceId, data);
			this.context = context;
			this.layoutResourceId = layoutResourceId;
			this.data = data;
			this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		
		@Override
		public View getView(int position, View convertview, ViewGroup parent){
			
			View row = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			String mElement = data[position];
			
			TextView textview = (TextView) row.findViewById(android.R.id.text1);
			textview.setText(Html.fromHtml(mElement));
		
			return row;
		}
	}
}