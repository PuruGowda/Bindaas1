package com.wattabyte.bindaasteam.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;

public class GroupReplyActivity extends ActionBarActivity {
	public static final String REQUESTNAME = "requestName";
	public static final String REQUESTMESSAGE = "requestMessage";
	public static final String REQUESTNAMEID = "requestNameId";
	public static final String GROUPNAME = "groupName";
	public static final String OBJECTID = "objectId";
	public static final String LEAGUENAME = "leagueName";
	
	TextView reqName, reqMessage;
	Button accept , reject;
	String rUserName;
	String requestName, grpName, leagueName;
	String oId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		Log.d("MSG", "Inside Reply Activity");
		
		Intent i = getIntent();
		Log.d("MSG", "Inside getIntent");
		requestName = i.getStringExtra(REQUESTNAME);
		grpName = i.getStringExtra(GROUPNAME);
		oId = i.getStringExtra(OBJECTID);
		leagueName = i.getStringExtra(LEAGUENAME);
		String requestMessage = i.getStringExtra(REQUESTMESSAGE);
		final String requestNameId = i.getStringExtra(REQUESTNAMEID);
		Log.d("MSG", "After getting strings");
		reqName = (TextView) findViewById(R.id.requestName);
		reqMessage = (TextView) findViewById(R.id.requestMessage);
		accept = (Button) findViewById(R.id.accept);
		reject = (Button) findViewById(R.id.reject);
		Log.d("MSG", "After gsetting");
		
		reqName.setText(requestName);
		reqMessage.setText(requestMessage);
		
		ParseUser pUser = ParseUser.getCurrentUser();
		rUserName = pUser.getUsername();
		final String rUserId = pUser.getObjectId();
		
		accept.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				Log.i("MSG", "Onclick accept Group request msg");
				ParseObject groupAccept = new ParseObject(""+grpName);
				groupAccept.put("PlayerName", ""+requestName);
//				groupAccept.put("FriendId", ""+requestNameId);
				groupAccept.saveInBackground();
				ParseObject groupRequest = new ParseObject("Group"+rUserName);
				groupRequest.put("GroupName", ""+grpName);
				groupRequest.put("LeagueName",""+leagueName);
				groupRequest.saveInBackground();
				
				ParseObject group = new ParseObject(""+grpName);
				group.put("PlayerName", rUserName);
				group.saveInBackground();
				
				// for accepting and deleting the request
				ParseObject myObj = new ParseObject("GroupRequest");
				myObj.createWithoutData("GroupRequest", oId).deleteEventually();
				Log.i("MSG", "Group request msg Successfully deleted the request");
				finish();
			}
		});
		ParseAnalytics.trackAppOpened(getIntent());
		
		reject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//for rejecting and deleting the request
				Log.i("MSG", "On click Reject");
				ParseObject.createWithoutData("GroupRequest", oId).deleteEventually();
				Log.i("MSG", "Group request msg Successfully deleted the request");
				finish();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reply, menu);
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
