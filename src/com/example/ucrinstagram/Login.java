package com.example.ucrinstagram;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

	public static String username="";
	public static String password="";

	InputStream is; 
	boolean Home = true;
	boolean Finished = false;

    
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

    public void HomeScreen(View view){
    	Finished = false;
    	username = ((EditText)findViewById(R.id.member_username)).getText().toString();
    	password = ((EditText)findViewById(R.id.member_password)).getText().toString();
		new checkLogin().execute();
		while(!Finished);

		if(Home){
			Intent intent = new Intent(this, HomeScreen.class);
			intent.putExtra("caption", "");
			startActivity(intent);
		}
		else{
			finish();
			startActivity(getIntent());
		}
    }
    
    private class checkLogin extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... arg0) {
			String result = "";

			ArrayList<NameValuePair> userinfo = new ArrayList<NameValuePair>();
			userinfo.add(new BasicNameValuePair("user",username));
			userinfo.add(new BasicNameValuePair("password",password));


			//http post
			try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://www.kevingouw.com/cs180/checkUser.php");
			        httppost.setEntity(new UrlEncodedFormEntity(userinfo));
			        HttpResponse response = httpclient.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        is = entity.getContent();

			}
			catch(Exception e){
			        Log.e("log_tag", "Error in http connection "+e.toString());
			}
			//convert response to string
			try{

			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			        }
			        is.close();

			        result=sb.toString();

			}catch(Exception e){
			        Log.e("log_tag", "Error converting result "+e.toString());
			}
			 System.out.println("======================");


			//parse json data
			try{
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);
			                //userinfo.add(json_data.getInt("id"));
			                Home = true;
			                System.out.println(json_data.getInt("id"));
			                //System.out.println(json_data.getString("image_url"));
			        }

			}
			catch(JSONException e){
					Home = false;
					System.out.println(Home);
			        Log.e("log_tag", "Error parsing data "+e.toString());
			}


			 System.out.println("======================");
			 //TextView tmp =(TextView)findViewById(R.id.errorlogin);
			 //tmp.setText( "Incorrect username/password combination, please try again:");
			 Finished = true;
			return null;


		}
		protected void onPostExecute(Void Result){
		}

	}
}