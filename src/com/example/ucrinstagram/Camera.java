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
    //byte[] byteArray;

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
    
    public void done(String path){
    	Intent myIntent = new Intent(this, PostPicture.class);
    	myIntent.putExtra("picture", path);
    	startActivity(myIntent);
    }
    
    public void startCamera(View view){
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    	startActivityForResult(intent, TAKE_PICTURE);   
    	//done();
    }

    public void startGallery(View view){
	    Intent intent = new Intent();
	    intent.setType("image/*");
	    intent.setAction(Intent.ACTION_GET_CONTENT);//
	    startActivityForResult(Intent.createChooser(intent, "Select Picture for UCRinstagram"),ACTIVITY_SELECT_IMAGE);
	    //done();
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
	            
	        	//bmp = BitmapFactory.decodeFile(filePath);
	            System.out.println(filePath);

	        	//ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        	//bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
	        	//byteArray = stream.toByteArray();
		       // ImageView myImage2 = (ImageView) findViewById(R.id.imageView);
		       //myImage2.setImageBitmap(bmp);
       }
        else {
                //Toast.makeText(getBaseContext(), "Please capture again", Toast.LENGTH_LONG).show();
       }


        if(resultCode == RESULT_OK && requestCode == ACTIVITY_SELECT_IMAGE){  
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            System.out.println(filePath);
            cursor.close();

            //Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
	        //ImageView myImage2 = (ImageView) findViewById(R.id.imageView);
	        //myImage2.setImageBitmap(yourSelectedImage);
        }

        done(filePath);
   }
    
}
