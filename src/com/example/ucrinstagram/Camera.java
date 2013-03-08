package com.example.ucrinstagram;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;

public class Camera extends Activity {
    final int TAKE_PICTURE = 1;
    final int ACTIVITY_SELECT_IMAGE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_camera, menu);
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
    
    public void updates(View view){
    	Intent intent = new Intent(this, Updates.class);
    	startActivity(intent);
    }
    
    public void profile(View view){
    	Intent intent = new Intent(this, Profile.class);
    	startActivity(intent);    	
    }
    
    
    public void done(String path){
    	//Intent myIntent = new Intent(this, PostPicture.class);
    	Intent myIntent = new Intent(this, Filter.class);
    	myIntent.putExtra("picture", path);
    	startActivity(myIntent);
    }
    
    public void startCamera(View view){
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(intent, TAKE_PICTURE);   
    }

    public void startGallery(View view){
	    Intent intent = new Intent();
	    intent.setType("image/*");
	    intent.setAction(Intent.ACTION_GET_CONTENT);//
	    startActivityForResult(Intent.createChooser(intent, "Select Picture for UCRinstagram"),ACTIVITY_SELECT_IMAGE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
    	//Bitmap bmp = null;
    	String filePath = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};
	
	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();
	
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            filePath = cursor.getString(columnIndex);
	            cursor.close();
	            
	            System.out.println(filePath);

       }
       // else {
         //       Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_LONG).show();
      // }


        if(resultCode == RESULT_OK && requestCode == ACTIVITY_SELECT_IMAGE){  
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            System.out.println(filePath);
            cursor.close();

        }
       // else {
           // Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_LONG).show();
        //}
        if (filePath!=null){
        	done(filePath);
        }
   }
    
}
