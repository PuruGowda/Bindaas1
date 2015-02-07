package com.wattabyte.bindaasteam.navigationdrawer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
import com.wattabyte.bindaasteam.groupmanagement.GroupFinalActivity;
import com.wattabyte.bindaasteam.teammanagement.Players;
import com.wattabyte.bindaasteam.teammanagement.SelectPlayers;
import com.wattabyte.bindaasteam.teammanagement.TeamListViewActivity;
import com.wattabyte.bindaasteam.teammanagement.TeamName;
import com.wattabyte.bindaasteam.util.BindaasUtil;

public class TeamManagementFragment extends Fragment {
	

	public static final String GROUP_NAME = "GroupName";
	public static final String LEAGUE_NAME = "LeagueName";
	Button button;
	TextView tv;
	Intent i;
	BindaasUtil bu;
	ListView teamListView;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> teamList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.team_management_fragment, container , false);
		button = (Button) root.findViewById(R.id.button1);
		 button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					 i = new Intent(NavigationDrawerActivity.getInstance(),TeamName.class);
					startActivity(i);
					//finish();
				}
			});
	        
	        teamListView = (ListView) root.findViewById(R.id.listView1);
	        ParseUser pUser = ParseUser.getCurrentUser();
	        String playerName = pUser.getUsername();
	        
	        ParseQuery<ParseObject> query =  ParseQuery.getQuery("Team"+playerName);
	        query.whereExists(GROUP_NAME);
	        query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> groups, ParseException e) {
					
					if (e == null) {
						Toast.makeText(NavigationDrawerActivity.getInstance(), "Players Retrieved"+groups.size(), 
								Toast.LENGTH_LONG).show();
						teamList = new ArrayList<HashMap<String, String>>();
						HashMap<String, String> map;
						for (ParseObject parseObject : groups) {
							if (e == null) {
							map = new HashMap<String , String>();
							map.put(GROUP_NAME, parseObject.getString(GROUP_NAME));
							map.put(LEAGUE_NAME, parseObject.getString(LEAGUE_NAME));
							teamList.add(map);
							} else {
								Log.d("MSG", "Error: " + e.getMessage());
							}
							
							Log.d("MSG", teamList.toString());
							adapter = new SimpleAdapter(NavigationDrawerActivity.getInstance(), teamList,
									R.layout.group_item, new String[]{GROUP_NAME,LEAGUE_NAME},
									new int[]{R.id.groupName , R.id.leagueName});
							teamListView.setAdapter(adapter);
							
							teamListView.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									HashMap<String, String> item = teamList.get(position);
									Intent i = new Intent(NavigationDrawerActivity.getInstance(), 
											TeamListViewActivity.class);
									i.putExtra(GROUP_NAME, item.get(GROUP_NAME));
									Log.d("MSG", item.get(GROUP_NAME));
									i.putExtra(LEAGUE_NAME, item.get(LEAGUE_NAME));
									Log.d("MSG", item.get(LEAGUE_NAME));
									startActivity(i);
									Log.d("MSG", "After switch activity");
									
								}
							});
						}
					} else {
						Toast.makeText(NavigationDrawerActivity.getInstance(), "Unsuccessful Players Retrieved", 
								Toast.LENGTH_LONG).show();
						
					}
					
					
				}
			});
	       
		return root;
	}
}
