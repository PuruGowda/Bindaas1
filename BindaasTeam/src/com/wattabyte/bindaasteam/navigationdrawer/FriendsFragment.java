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
import com.wattabyte.bindaasteam.friends.AvailableUser;


public class FriendsFragment extends Fragment {

	Button add;
	Intent intent;

	public static final String MSG = "FriendsFragment";
	public static final String FRIEND_NAME= "FriendName";
	ListView friendsListView;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> friendList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.friends_fragment, container , false);
		add = (Button) root.findViewById(R.id.add_friends);
			friendsListView = (ListView) root.findViewById(R.id.friendsListView);
		Log.d(MSG, "Before setOnclick Listener");

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {

					intent = new Intent(NavigationDrawerActivity.getInstance(), AvailableUser.class);

					startActivity(intent);

				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		ParseUser pUser = ParseUser.getCurrentUser();
        String playerName = pUser.getUsername();
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery(""+playerName);
		query.whereExists(FRIEND_NAME);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> friends, ParseException e) {
				if (e == null) {
					Toast.makeText(NavigationDrawerActivity.getInstance(), "Friends Retrieved"+friends.size(), 
							Toast.LENGTH_LONG).show();
					friendList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					for (ParseObject parseObject : friends) {
						map = new HashMap<String , String>();
						map.put(FRIEND_NAME, parseObject.getString(FRIEND_NAME));
						friendList.add(map);
						adapter = new SimpleAdapter(NavigationDrawerActivity.getInstance(), friendList,
								R.layout.friend_item, new String[]{FRIEND_NAME},
								new int[]{R.id.friendName});
						friendsListView.setAdapter(adapter);
					}
				} else {
					Toast.makeText(NavigationDrawerActivity.getInstance(), "Friends Unetrieved", 
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		return root;
	}

}
