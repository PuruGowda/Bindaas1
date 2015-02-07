package com.wattabyte.bindaasteam.groupmanagement;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsRequestActivity extends ActionBarActivity {
	
	String userName,grpName, message, leagueName, message1;
	TextView requestMessage;
	Button yes,no;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_join_request);
		
		Intent i = getIntent();
		grpName = i.getStringExtra(FriendsListActivity.GROUPNAME1);
		userName = i.getStringExtra(FriendsListActivity.PLAYERNAME);
		leagueName = i.getStringExtra(FriendsListActivity.LEAGUE);
		Log.i("MSG", userName+"            "+grpName+"         "+leagueName);
		ParseUser pUser = ParseUser.getCurrentUser();
		final String rUserName = pUser.getUsername();
		final String rUserId = pUser.getObjectId();
		
		requestMessage = (TextView) findViewById(R.id.requestMessage);
		yes = (Button) findViewById(R.id.yes);
		no = (Button) findViewById(R.id.no);
		message = "Request to join "+rUserName+"'s "+grpName+"group";
		message1 = "Do u wish sending a request to "+userName+". ";
		requestMessage.setText(message1);
		
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Log.i("MSG", "Successfully sent request");	
			ParseObject request = new ParseObject("GroupRequest");
			request.put("PlayerName",""+userName);
			request.put("GroupName",""+grpName);
			request.put("LeagueName",""+leagueName);
			request.put("RequestName",""+rUserName);
			request.put("RequestUserId",""+rUserId);
			request.put("RequestMessage",""+message);
			request.saveInBackground();
			Toast.makeText(FriendsRequestActivity.this, "Request sent to"+userName, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Log.i("MSG", "Request not sent");
			finish();
			}
		});
	}

}
