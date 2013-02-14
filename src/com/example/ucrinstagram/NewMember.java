package com.example.ucrinstagram;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewMember extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_member);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_member, menu);
		return true;
	}

    public void HomeScreen(View view){
    	EditText edittext = (EditText) findViewById(R.id.new_member_username);
    	SharedPreferences userInfo = getSharedPreferences("tempUsername", 0);
		Editor userInfoEditor = userInfo.edit();
		userInfoEditor.putString("username", edittext.getText().toString());
		userInfoEditor.commit();
    	Intent intent = new Intent(this, HomeScreen.class);
    	startActivity(intent);    	
    }
    
}
