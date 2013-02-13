package com.example.ucrinstagram;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewMember extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_member);
		
		Button checkButton = (Button) findViewById(R.id.button_new_member_test);
		checkButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.new_member_username);
				if(CheckUsernameAvailable(editText.getText().toString())){
					Intent intent = new Intent(NewMember.this, NewProfileInfo.class);
					intent.putExtra("username", editText.getText().toString());
					startActivity(intent);
					NewMember.this.finish();
				} else {
					TextView notAvailable = (TextView) findViewById(R.id.notAvailable);
					notAvailable.setText("Username is not available");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_member, menu);
		return true;
	}

    public void HomeScreen(View view){
    	Intent intent = new Intent(this, HomeScreen.class);
    	startActivity(intent);    	
    }
    
    public boolean CheckUsernameAvailable(String username){
    	//Todo: Way to verify username availability from server
    	if(username == "realgenob")
    		return false;
    	return true;
    }
    
}
