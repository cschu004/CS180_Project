package com.example.ucrinstagram;

import android.widget.Toast;
import com.example.ucrinstagram.Models.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        String username = ((EditText)findViewById(R.id.new_member_username)).getText().toString();
        String password = ((EditText)findViewById(R.id.new_member_password)).getText().toString();

        if (!User.exists(username)){ // check username doesn't exist
            User loginUser = new User(username, password);
            loginUser.create();
            HomeScreen.username = loginUser.username;

            Toast.makeText(this.getApplicationContext(), "Created a new user account!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeScreen.class);
            intent.putExtra("caption", "");
            startActivity(intent);
        } else {
            Toast.makeText(this.getApplicationContext(), "Username already in use.", Toast.LENGTH_LONG).show();
        }
    }
}