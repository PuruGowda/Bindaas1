package com.wattabyte.bindaasteam.teammanagement;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.dbhelper.BindaasDatabaseAdapter;

public class SelectLeague extends ActionBarActivity {
	
	public static final String LEAGUES = "Leagues";

	public static final String TEAM_NAME = "name";

	public static final String LEAGUE = "league";

	TextView tv;

	ArrayAdapter<String> adapter;
	public ArrayList<String> leagueArray;
	 static String teamName;
	 ListView leagueListView;
	 BindaasDatabaseAdapter helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_league);
		
		tv = (TextView) findViewById(R.id.textView1);
		leagueListView = (ListView) findViewById(R.id.league_list);
		Intent i = getIntent();
		teamName = i.getStringExtra("text");
		Log.i("MSG", teamName);
		tv.setText(teamName);


		ParseQuery<ParseObject> query = ParseQuery.getQuery(LEAGUES);
		query.whereExists("Name");
		
		leagueArray = new ArrayList<String>();
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("MSG", "Retrieved " + scoreList.size() + " leagues");
		            for( ParseObject ob : scoreList)
		            {
		            	leagueArray.add(ob.getString("Name"));
		            Log.i("MSG",ob.getString("Name"));
		            }
		            
		        } else {
		            Log.d("MSG", "Error: " + e.getMessage());
		        }
		      adapter = new ArrayAdapter<String>(SelectLeague.this, 
	            		android.R.layout.simple_list_item_1, leagueArray);
		        leagueListView.setAdapter(adapter);
		        Log.d("MSG", "Next Activity");
		        leagueListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent in = new Intent(SelectLeague.this,SelectPlayers.class);
						in.putExtra(LEAGUE, adapter.getItem(position).toString());
						Log.d("MSG", "Passing Extra");
						in.putExtra(TEAM_NAME, teamName);
						Log.d("MSG", "Passing Extra");
						startActivity(in);
						Log.d("MSG", "Starting to switch Activity");
					}

					
		    });
		   }
		    
		});


			    	 

		
	}

}
