package com.example.ucrinstagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Signup_Login extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup__login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_signup__login, menu);
		return true;
	}

	public void SignUp(View view) {
		Intent intent = new Intent(this, NewMember.class);
		startActivity(intent);
	}

	public void Login(View view) {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
}
