package com.wattabyte.bindaasteam.teammanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.R.id;
import com.wattabyte.bindaasteam.R.layout;
import com.wattabyte.bindaasteam.R.menu;
import com.wattabyte.bindaasteam.groupmanagement.GroupFinalActivity;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TeamListViewActivity extends ActionBarActivity {

	public static final String GROUP_NAME = "GroupName";
	public static final String LEAGUE_NAME = "LeagueName";
	public static final String TEAM_MEMBER = "Name";
	public static final String GOLD = "Gold";
	public static final String ROLE = "Role";
	ListView teamMemberListView;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> teamMemberList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_list_view);
		Intent i = getIntent();
		 String groupName  = i.getStringExtra(GROUP_NAME);
		Log.d("MSG", groupName);
		String leagueName = i.getStringExtra(LEAGUE_NAME);
		Log.d("MSG", leagueName);
		teamMemberListView = (ListView) findViewById(R.id.listView);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(""+groupName);
		query.whereExists(TEAM_MEMBER);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> teams, ParseException e) {
				if (e == null) {
					Toast.makeText(TeamListViewActivity.this, "Teams Retrieved "+teams.size(), 
							Toast.LENGTH_SHORT).show();
					teamMemberList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					for (ParseObject parseObject : teams) {
						map = new HashMap<String , String>();
						map.put(TEAM_MEMBER, parseObject.getString(TEAM_MEMBER));
						Log.d("MSG", parseObject.getString(TEAM_MEMBER));
						map.put(ROLE, parseObject.getString(ROLE));
						Log.d("MSG", parseObject.getString(ROLE));
						map.put(GOLD, parseObject.getString(GOLD));
						Log.d("MSG", parseObject.getString(GOLD));
						
						teamMemberList.add(map);
						Log.d("MSG", teamMemberList.toString());
						adapter = new SimpleAdapter(TeamListViewActivity.this,teamMemberList ,
								R.layout.team_member_item, new String[]{TEAM_MEMBER,ROLE,GOLD}, 
								new int[]{R.id.teamMemberName,R.id.role,R.id.gold});
						teamMemberListView.setAdapter(adapter);
					}
				} else {
					Toast.makeText(TeamListViewActivity.this, "Teams Not Retrieved ", 
							Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team_list_view, menu);
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
