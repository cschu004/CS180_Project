package com.example.ucrinstagram;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectGoogleAccount extends Activity {
	static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.email";
	
	private Spinner mAccountTypesSpinner;
	private String[] mNamesArray;
	private String mEmail;
	private String mToken;
	private TextView mOut;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_google_account);
		
		mOut = (TextView) findViewById(R.id.selectGoogleSignInError);
		
		mNamesArray = getAccountNames();
		mAccountTypesSpinner = initializeSpinner(R.id.selectGoogleAccountSpinner, mNamesArray);
		initializeFetchButton();
		
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
        Button signInButton = (Button) findViewById(R.id.selectGoogleSignInButton);
        
        signInButton.setOnClickListener(new View.OnClickListener() {

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
                Intent intent = new Intent(SelectGoogleAccount.this, NewMember.class);
                intent.putExtra("email", mEmail);
                intent.putExtra("token", mToken);
                startActivity(intent);
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
				token = GoogleAuthUtil.getToken(SelectGoogleAccount.this, email, scope);
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
		
		protected void onPostExecute(String result){
			SelectGoogleAccount.this.setToken(result);
		} 
	 };
	 
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
