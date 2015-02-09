package com.wattabyte.bindaasteam.notification;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.teammanagement.TeamName;

public class ReplyActivity extends ActionBarActivity {
	
	public static final String REQUEST = "Request";
	public static final String FRIEND_ID = "FriendId";
	public static final String FRIEND_NAME = "FriendName";
	public static final String REQUESTNAME = "requestName";
	public static final String REQUESTMESSAGE = "requestMessage";
	public static final String REQUESTNAMEID = "requestNameId";
	public static final String OBJECTID = "objectId";
	
	TextView reqName, reqMessage;
	Button accept , reject;
	String rUserName;
	String requestName;
	String oId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		Log.d("MSG", "Inside Reply Activity");
		
		Intent i = getIntent();
		Log.d("MSG", "Inside getIntent");
		requestName = i.getStringExtra(REQUESTNAME);
		oId = i.getStringExtra(OBJECTID);
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
				Log.i("MSG", "Onclick accept");
				ParseQuery<ParseObject> query = ParseQuery.getQuery(""+rUserName);
				query.whereExists(FRIEND_NAME);
				final ArrayList<String> friendArray = new ArrayList<String>();
				query.findInBackground(new FindCallback<ParseObject>() {
					
					@Override
					public void done(List<ParseObject> friends, ParseException e) {
						if (e == null) {
							for (ParseObject friend : friends) {
								friendArray.add(friend.getString(FRIEND_NAME));
							}
								boolean flag = false;
								for (String string : friendArray) {
									if (requestName.equals(string)) {
										Toast.makeText(ReplyActivity.this,
												"Equal", Toast.LENGTH_SHORT)
												.show();
										flag = false;
										break;
									} else {
										Toast.makeText(ReplyActivity.this,
												"Not Equal",
												Toast.LENGTH_SHORT).show();
										flag = true;
									}
								
							if(flag){
							ParseObject freindAccept = new ParseObject(""+rUserName);
							freindAccept.put(FRIEND_NAME, ""+requestName);
							freindAccept.put(FRIEND_ID, ""+requestNameId);
							freindAccept.saveInBackground();
							ParseObject freindRequest = new ParseObject(""+requestName);
							freindRequest.put(FRIEND_NAME, ""+rUserName);
							freindRequest.put(FRIEND_ID, ""+rUserId);
							freindRequest.saveInBackground();
							// for accepting and deleting the request
							ParseObject myObj = new ParseObject(REQUEST);
							myObj.createWithoutData(REQUEST, oId).deleteEventually();
							Log.i("MSG", "Successfully deleted the request");
							}
							else{
								// for accepting and deleting the request
								ParseObject myObj = new ParseObject(REQUEST);
								myObj.createWithoutData(REQUEST, oId).deleteEventually();
								Log.i("MSG", "Successfully deleted the request");
							}
						}	
						}else {

						}
					}
				});
				
				}	
			
		});
		ParseAnalytics.trackAppOpened(getIntent());
		
		reject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//for rejecting and deleting the request
				Log.i("MSG", "On click Reject");
				ParseObject.createWithoutData(REQUEST, oId).deleteEventually();
				Log.i("MSG", "Successfully deleted the request");
				
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
