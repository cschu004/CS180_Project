package com.example.ucrinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class PrefsActivity extends PreferenceActivity {
	final int ACTIVITY_SELECT_IMAGE = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);

	}

}
