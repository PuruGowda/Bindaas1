package com.wattabyte.bindaasteam.groupmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.friends.AvailableUser;
import com.wattabyte.bindaasteam.friends.RequestActivity;

public class FriendsListActivity extends ActionBarActivity {
	
	
	
	ArrayList<HashMap<String, String>> friendList;
	ListView friendListView ;
	ListAdapter adapter;
	
	TextView groupName , leagueName;
	String grpName , leagName;
	public static final String FRIEND_NAME = "FriendName";
	public static final String FRIEND = "Friend";
	public static final String PLAYERNAME = "PlayerName";
	public static final String GROUPNAME1 = "GroupName";
	public static final String LEAGUE = "leagueName";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_list);
		
		Log.i("MSG", "Inside Friends List Activity");
		groupName = (TextView) findViewById(R.id.groupName);
		leagueName = (TextView) findViewById(R.id.leagueName);
		friendListView = (ListView) findViewById(android.R.id.list);
		Intent i = getIntent();
		grpName = i.getStringExtra(GroupNameActivity.GROUP_NAME);
		leagName = i.getStringExtra(LeagueSelectActivity.LEAGUE_NAME);
		
		groupName.setText(grpName);
		leagueName.setText(leagName);
		
		ParseUser pUser = ParseUser.getCurrentUser();
		final String playerFriend = pUser.getUsername();
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(""+playerFriend);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				if (e == null) {
					friendList =  new ArrayList<HashMap<String,String>>();
					HashMap<String, String> map;
				
					
				 Log.d("Vinay", "Retrieved Frineds of size"+ objects.size());
				 for (ParseObject parseObject : objects) {
					 
					 if(parseObject != null && !parseObject.equals("")){
					 map = new HashMap<String , String>();
						map.put(FRIEND, parseObject.getString(FRIEND_NAME));
						//map.put(LEAGUE, leagName);
						friendList.add(map);
						
					 }
					
				}
				 adapter = new SimpleAdapter(FriendsListActivity.this,
							friendList, R.layout.player_item, new String[] {
									FRIEND }, new int[] {
									R.id.playerName });
					friendListView.setAdapter(adapter);
					
					friendListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(
								AdapterView<?> parent, View view,
								int position, long id) {
							HashMap<String, String> item = friendList.get(position);
							Intent intent = new Intent(FriendsListActivity.this, FriendsRequestActivity.class);
							intent.putExtra(PLAYERNAME, item.get(FRIEND));
							Log.d("MSG", item.get(FRIEND));
							intent.putExtra(GROUPNAME1, grpName);
							Log.d("MSG", grpName);
							intent.putExtra(LEAGUE, leagName);
							Log.i("MSG", leagName);
							startActivity(intent);							
//							ParseObject playerGroup = new ParseObject("Group"+item.get(FRIEND));
//							playerGroup.put("GroupName", ""+grpName);
//							playerGroup.put("LeagueName", ""+leagName);
//							playerGroup.saveInBackground();
//							
							ParseObject groupCreate = new ParseObject(""+grpName);
							groupCreate.put(PLAYERNAME,""+playerFriend);
							groupCreate.saveInBackground();
							
						}
					});
					
				}
				else{
					Log.d("Vinay", "Retrieved Frineds Unsuccessful");
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends_list, menu);
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
