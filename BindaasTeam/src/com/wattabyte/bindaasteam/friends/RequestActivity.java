package com.wattabyte.bindaasteam.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.navigationdrawer.NavigationDrawerActivity;

public class RequestActivity extends ActionBarActivity {
	
	public static final String PLAYERNAME = "playerName";
	public static final String PLAYERID = "playerId";
	
	EditText message;
	Button request;
	String rMessage;
	String playerName;
	String playerId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		Intent i = getIntent();
		playerName = i.getStringExtra(PLAYERNAME);
		playerId = i.getStringExtra(PLAYERID);
		Log.i("Vinay", playerName);
		Log.i("Vinay", playerId);
		
		message = (EditText) findViewById(R.id.userMessage);
		request = (Button) findViewById(R.id.sendRequest);
		
		
		ParseUser pUser = ParseUser.getCurrentUser();
		final String rUserName = pUser.getUsername();
		final String rUserId = pUser.getObjectId();
		
		request.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rMessage = message.getText().toString(); 
				if(rMessage != null && !rMessage.equals("")){
					
					ParseObject request = new ParseObject("Request");
					request.put("PlayerName",""+playerName);
					request.put("PlayerId",""+playerId);
					request.put("RequestName",""+rUserName);
					request.put("RequestUserId",""+rUserId);
					request.put("RequestMessage",""+rMessage);
					request.saveInBackground();
					Toast.makeText(RequestActivity.this, "Successfully sent Request.....", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(RequestActivity.this, NavigationDrawerActivity.class);
					startActivity(i);
				}
				else{
					Toast.makeText(RequestActivity.this, "Please enter the message", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
