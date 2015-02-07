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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.groupmanagement.GroupFinalActivity;
import com.wattabyte.bindaasteam.groupmanagement.GroupManagementActivity;
import com.wattabyte.bindaasteam.groupmanagement.GroupNameActivity;

public class GroupManagementFragment extends Fragment {
	
	Button add;
	Intent intent;
	
	public static final String GROUP_NAME = "GroupName";
	public static final String LEAGUE_NAME = "LeagueName";
	
	ListView groupListView;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> groupList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.groups_fragment, container , false);
		add = (Button) root.findViewById(R.id.add_groups);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					intent = new Intent(NavigationDrawerActivity.getInstance(), GroupNameActivity.class);

					startActivity(intent);

				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		groupListView = (ListView) root.findViewById(R.id.listView1);
        ParseUser pUser = ParseUser.getCurrentUser();
        String playerName = pUser.getUsername();
        
        ParseQuery<ParseObject> query =  ParseQuery.getQuery("Group"+playerName);
        query.whereExists(GROUP_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> groups, ParseException e) {
				
				if (e == null) {
					Toast.makeText(NavigationDrawerActivity.getInstance(), "Players Retrieved"+groups.size(), 
							Toast.LENGTH_LONG).show();
					groupList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					for (ParseObject parseObject : groups) {
						if (e == null) {
						map = new HashMap<String , String>();
						map.put(GROUP_NAME, parseObject.getString(GROUP_NAME));
						map.put(LEAGUE_NAME, parseObject.getString(LEAGUE_NAME));
						groupList.add(map);
						} else {
							Log.d("MSG", "Error: " + e.getMessage());
						}
						
						Log.d("MSG", groupList.toString());
						adapter = new SimpleAdapter(NavigationDrawerActivity.getInstance(), groupList,
								R.layout.group_item, new String[]{GROUP_NAME,LEAGUE_NAME},
								new int[]{R.id.groupName , R.id.leagueName});
						groupListView.setAdapter(adapter);
						
						groupListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								HashMap<String, String> item = groupList.get(position);
								Intent i = new Intent(NavigationDrawerActivity.getInstance(), GroupFinalActivity.class);
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
