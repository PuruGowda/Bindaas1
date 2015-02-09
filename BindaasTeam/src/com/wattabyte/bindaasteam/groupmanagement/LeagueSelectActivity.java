package com.wattabyte.bindaasteam.groupmanagement;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.util.Message;

public class LeagueSelectActivity extends ActionBarActivity {
	
	
	public static final String LEAGUES = "Leagues";
	public static final String LEAGUE_NAME = "Name";
	public static final String PLAYERNAME = "PlayerName";
	public static final String GROUPCREATOR = "GroupCreator";
	
	TextView groupName;
	Intent i;
	String grpName;
	ArrayList<String> leagueArray;
	ArrayAdapter<String> adapter;
	ListView leagueList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league_select);
		
		groupName =  (TextView) findViewById(R.id.groupName);
		leagueList = (ListView) findViewById(android.R.id.list);
		i = getIntent();
		grpName = i.getStringExtra(GroupNameActivity.GROUP_NAME);
		groupName.setText(grpName);
	
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(LEAGUES);
		query.whereExists(LEAGUE_NAME);
		leagueArray = new ArrayList<String>();
		
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				if(e == null){
					
					 Log.d("MSG", "Retrieved " + objects.size() + " leagues");
					 
					 for( ParseObject object : objects)
			            {
			            	leagueArray.add(object.getString(LEAGUE_NAME));
			            Log.i("MSG",object.getString(LEAGUE_NAME));
			            }
					
					
				}else{
					
					 Log.d("MSG", "Not retrieved successfull");
				}
				 adapter = new ArrayAdapter<String>(LeagueSelectActivity.this, 
		            		android.R.layout.simple_list_item_1, leagueArray);
			        leagueList.setAdapter(adapter);
			        
			        leagueList.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
						
							ParseUser pUser = ParseUser.getCurrentUser();
							String userName = pUser.getUsername();
							Log.d("Vinay", userName);
							if (userName !=  null && !userName.equals("") ) {
								
							ParseObject playerGroup = new ParseObject("Group"+userName);
							playerGroup.put("GroupName", ""+grpName);
							playerGroup.put("LeagueName", ""+adapter.getItem(position).toString());
							playerGroup.saveInBackground();
							ParseObject groupCreate = new ParseObject(""+grpName);
							groupCreate.put(PLAYERNAME,""+userName);
							playerGroup.put(GROUPCREATOR,""+userName);
							groupCreate.saveInBackground();
							
							i = new Intent(LeagueSelectActivity.this,FriendsListActivity.class);
							i.putExtra(LEAGUE_NAME, adapter.getItem(position).toString());
							i.putExtra(GroupNameActivity.GROUP_NAME, grpName);
							startActivity(i);
							}else{
							
							}
							
						}
					});
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.league_select, menu);
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
