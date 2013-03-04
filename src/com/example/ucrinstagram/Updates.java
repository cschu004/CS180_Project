package com.example.ucrinstagram;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class Updates extends ListActivity {
	
	private String[] updatesArray;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updates);
			
		new getUpdateData().execute();
		
	}
	
	public void setUpdateArray(String[] array){
		this.updatesArray =  array;
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
	}
	
	private class getUpdateData extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Call web api to get the follwers updates and add them to array
			String[] data = {"Bob: likes a new photo", "Jill: commented on your photo",
								"Pete: uploaded a new photo"};
			return data;
		}
		
		protected void onPostExecute(String[] result){
			setUpdateArray(result);
			
		}
		
	}
	

	
}
