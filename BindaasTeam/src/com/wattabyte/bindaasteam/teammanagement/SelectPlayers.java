package com.wattabyte.bindaasteam.teammanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.util.BindaasUtil;

public class SelectPlayers extends ActionBarActivity {

	String league;
	TextView teamName, playerGold, playerno, leagueName, points;
	Button next;
	ListView playerListView;
//	TextView goldCount;
	View v;
	LayoutInflater inflater;
	public static final String NAME = "Name";
	public static final String ROLE = "Role";
	public static final String GOLD = "Gold";
	public static final String POINTS = "Points";
	
	public static final String TEAM_NAME = "name";
	public static final String LEAGUE = "league";
	
//	Constants for Mapping the Team Name
	public static final String TEAM_CREATOR = "TeamCreator";
	public static final String TEAM_LEAGUE_NAME = "LeagueName";
	public static final String TEAM_GROUP_NAME = "GroupName";
	
	
	ArrayList<HashMap<String, String>> playerRows;
	ArrayList<String> list = new ArrayList<String>();
	ListAdapter adapter;
	int count = 0;
	int gCount = 110;

	// ArrayAdapter<String> adapter;
	BindaasUtil bu;

	@Override
	protected void onCreate(Bundle savedInstanceState)  throws NullPointerException{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_player);
		Log.d("MSG", "on create activity");

//		Initialise to set heading for player select details 
		
		teamName = (TextView) findViewById(R.id.teamName);
		leagueName= (TextView) findViewById(R.id.leagueName);
		playerGold = (TextView) findViewById(R.id.pGold);
		points = 
		next = (Button) findViewById(R.id.next);
		
//		to get team name and league name from previous activity
		
		Intent i = getIntent();
		final String tName  = i.getStringExtra(TEAM_NAME);
		final String lName = i.getStringExtra(LEAGUE);
		
//		to set league name, team name and player gold at the headding 
		
		teamName.setText(tName);
		leagueName.setText(lName);
		playerGold.setText(""+gCount);
//		To initialise the list view
		playerListView = (ListView) findViewById(android.R.id.list);
		Log.d("MSG", "after setting the listview");
		
//		Retrieve list player from parse table with the league  name
		ParseQuery<ParseObject> query = ParseQuery.getQuery(""+lName);
		Log.d("Msg", "Setting up to get the player in the league name");
		
		query.whereExists(NAME);
		
		query.findInBackground(new FindCallback<ParseObject>() {
//			Adding 
			@Override
			public void done(List<ParseObject> playerList, ParseException e)  {
				if(e == null){
					Toast.makeText(SelectPlayers.this, "Players Retrieved"+playerList.size(), 
							Toast.LENGTH_LONG).show();
					playerRows = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					
					for (ParseObject player : playerList) {
						if (e == null) {
							
//							Log.i("MSG", "mapping");
							map = new HashMap<String, String>();
							map.put(NAME, player.getString(NAME));
//							Log.i("MSG", player.getString(NAME));
							map.put(ROLE, player.getString(ROLE));
//							Log.i("MSG", player.getString(ROLE));
							map.put(GOLD, player.getString(GOLD));
//							Log.i("MSG", player.getString(GOLD));
							map.put(POINTS, player.getString(POINTS));
							playerRows.add(map);
						} else {
							Log.d("MSG", "Error: " + e.getMessage());
						}
						Log.d("MSG", playerRows.toString());
						adapter = new SimpleAdapter(SelectPlayers.this,
								playerRows, R.layout.player_list, new String[] {
										NAME, ROLE, GOLD }, new int[] {
										R.id.playerName, R.id.playerRole, R.id.playerGold });
						Log.d("MSG", "after adapter");
						playerListView.setAdapter(adapter);
						
						playerListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {

								String gold = ((TextView) view
										.findViewById(R.id.playerGold)).getText()
										.toString();
								Log.d("MSG", "Obtaining GOld");
								int i = Integer.parseInt(gold);
								if (count <= 2 && (gCount - i) > 0) {
									count++;
									Log.d("MSG", "1st If COndition");
									gCount = gCount - i;

									if (gCount > 0) {
										
										Log.i("MSG", "Remaining gold is  "
												+ gCount);
										playerGold.setText(String.valueOf(gCount));
										ParseObject teamCreation = new ParseObject(""+tName);
										HashMap<String, String> item = playerRows.get(position);
										teamCreation.put(NAME, ""+item.get(NAME));
										teamCreation.put(ROLE, ""+item.get(ROLE));
										teamCreation.put(GOLD, ""+item.get(GOLD));
										teamCreation.put(POINTS,""+item.get(POINTS));
										teamCreation.saveInBackground();
									} else {
										Log.i("MSG", "Sorry you have " + gCount
												+ "golds");

									}
								}
								if (count > 2) {
									Toast.makeText(SelectPlayers.this, "Maximum Players Has Been Added", 
											Toast.LENGTH_SHORT).show();
									next.setVisibility(View.VISIBLE);
									
								}
								
							}
						});
						
					}
					
				}else {
					Toast.makeText(SelectPlayers.this, "Unsuccessful Players Retrieved", 
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser pUser = ParseUser.getCurrentUser();
				String playerName = pUser.getUsername();
				ParseObject userTeam = new ParseObject("Team"+playerName);
				userTeam.put(TEAM_CREATOR,""+playerName);
				userTeam.put(TEAM_GROUP_NAME,""+tName);
				userTeam.put(TEAM_LEAGUE_NAME,""+lName);
				userTeam.saveInBackground();
				
			}
		});


	}

	
}