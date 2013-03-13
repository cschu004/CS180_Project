package com.example.ucrinstagram;

import android.widget.Toast;
import com.example.ucrinstagram.Models.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
	public static String username = "";
	public static String password = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void HomeScreen(View view) {
		username = ((EditText) findViewById(R.id.member_username)).getText()
				.toString();
		password = ((EditText) findViewById(R.id.member_password)).getText()
				.toString();

		User loginUser = new User(username);
		if (loginUser == null) { // check username exists
			Toast.makeText(this.getApplicationContext(),
					"Incorrect Username/Password", Toast.LENGTH_LONG).show();
		} else if (loginUser.checkPassword(password)) { // check is password is
														// correct
			Login.username = username.toLowerCase().replaceAll("\\s", "");
			Intent intent = new Intent(this, HomeScreen.class);
			startActivity(intent);
		} else {
			Toast.makeText(this.getApplicationContext(),
					"Incorrect Username/Password", Toast.LENGTH_LONG).show();
		}
	}
}