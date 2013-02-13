package com.example.ucrinstagram;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesUtil;
import static com.google.android.gms.common.ConnectionResult.*;

public class Signup_Login extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_signup__login);
        SharedPreferences userInfo = getSharedPreferences("UserInfo", 0);
        
        //Check for Google Play Services Framework
        int gpInstalledStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(  gpInstalledStatus != SUCCESS){
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("Google play services was not detected on this device." +
        						" This application requires that the Google Play Services "
        						+ "framework is installed.");
        	builder.setTitle("Google Play Services");
        	builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Signup_Login.this.exitApp();
					
				}
			});
        	builder.create();
        	builder.show();
        }
        
        if( userInfo.getString("token", "NO_TOKEN") != "NO_TOKEN"){
        	startActivity(new Intent(this, HomeScreen.class));
        	finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_signup__login, menu);
        return true;
    }
    
    public void SignUp(View view){
    	Intent intent = new Intent(this, SelectGoogleAccount.class);
    	startActivity(intent);
    	finish();
    }
    
    public void Login(View view){
    	Intent intent = new Intent(this, Login.class);
    	startActivity(intent);
    	finish();
    }
    
    public void exitApp(){
    	finish();
    }
}
