package com.example.ucrinstagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewProfileInfo extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_profile_info);
				
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		TextView usernameTextView = (TextView) findViewById(R.id.newProfileUsername);
		usernameTextView.setText(username);
		
		Button createProfileButton = (Button) findViewById(R.id.createProfile);
		createProfileButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView usernameTextView = (TextView) findViewById(R.id.newProfileUsername);
				EditText nameEdit = (EditText) findViewById(R.id.newProfileEditName);
				EditText bioEdit = (EditText) findViewById(R.id.newProfileEditBio);
				
				if( CreateProfile(usernameTextView.getText().toString(),
								nameEdit.getText().toString(),
								bioEdit.getText().toString())
					){
					startActivity(new Intent(NewProfileInfo.this, HomeScreen.class));
					NewProfileInfo.this.finish();
				}
			}
		});
		
		
	}
	
	private boolean CreateProfile(String username, String name, String Profile){
		//Todo Send profile info to server. Get response from server. Return true upon success response
		return true;
	}

}
