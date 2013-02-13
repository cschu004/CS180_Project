package com.example.ucrinstagram;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Login extends Activity {
	
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.email";
    public static final String EXTRA_ACCOUNTNAME = "extra_accountname";

	private Spinner mAccountTypesSpinner;

    private TextView mOut;

    static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

    private String[] mNamesArray;
    private String mEmail;
    private String mToken;

    public static String TYPE_KEY = "type_key";
    public static enum Type {FOREGROUND, BACKGROUND, BACKGROUND_WITH_SYNC}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mNamesArray = getAccountNames();
        mAccountTypesSpinner = initializeSpinner(
                R.id.accounts_spinner, mNamesArray);

        initializeFetchButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
    public void HomeScreen(View view){
    	Intent intent = new Intent(this, HomeScreen.class);
    	startActivity(intent);    	
    }
    private String[] getAccountNames() {
        AccountManager mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }
    private Spinner initializeSpinner(int id, String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, values);
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
    }
    
    private void initializeFetchButton() {
        Button getToken = (Button) findViewById(R.id.button1);
        
        getToken.setOnClickListener(new View.OnClickListener() {

			@Override
            public void onClick(View v) {
                int accountIndex = mAccountTypesSpinner.getSelectedItemPosition();
                if (accountIndex < 0) {
                    // this happens when the sample is run in an emulator which has no google account
                    // added yet.
                    show("No account available. Please add an account to the phone first.");
                    return;
                }
                mEmail = mNamesArray[accountIndex];
                new GetToken(mEmail, SCOPE,
                        REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
                startActivity(new Intent(Login.this, HomeScreen.class));
                finish();
            }
        });
    }
    public void show(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOut.setText(message);
            }
        });
    }
    public void showErrorDialog(final int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Dialog d = GooglePlayServicesUtil.getErrorDialog(
                  code,
                  Login.this,
                  REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
              d.show();
            }
        });
    }
    private class GetToken extends AsyncTask<Void, Void, String> {
		
		 private String email;
		 private String scope;
		 
		 public GetToken(String email, String scope, int requestCode){
			 this.email = email;
			 this.scope = scope;
		 }
			@Override
		protected String doInBackground(Void...voids) {
			String token = null;
			try {
				token = GoogleAuthUtil.getToken(Login.this, email, scope);
			} catch (UserRecoverableAuthException uraexception) {
				Log.v("UserRecoverableAuthException", uraexception.getMessage());
				startActivityForResult(uraexception.getIntent(), REQUEST_CODE_RECOVER_FROM_AUTH_ERROR);
			} catch (GoogleAuthException fatalException) {
		          Log.v("GoogleAuthException", fatalException.getMessage());
			} catch (IOException e) {
				Log.v("IOException", e.getMessage());
				e.printStackTrace();
			}
			return token;
		}
			
		@Override
		protected void onPostExecute(String result) {
			Login.this.setToken(result);
		}
	 }
    
	public void setToken(String token){
		this.mToken = token;
		SharedPreferences userInfo = getSharedPreferences("UserInfo", 0);
		Editor userInfoEditor = userInfo.edit();
		userInfoEditor.putString("email", mEmail);
		userInfoEditor.putString("token", mToken);
		userInfoEditor.commit();
	}
    
    protected void onActivityResult(final int requestCode, final int resultCode,
	         final Intent data) {
	     if (requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR){
	    	 new GetToken(mEmail, SCOPE,
                     REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
	     }
	 }
}
