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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.notification.ReplyActivity;

public class FreindRequestFragment extends Fragment {

	public static final String PLAYER_NAME = "PlayerName";
	public static final String REQUEST = "Request";
	private static final String REQUEST_USER_ID = "RequestUserId";
	private static final String REQUEST_MESSAGE = "RequestMessage";
	private static final String REQUEST_NAME = "RequestName";
	public static final String REQUESTNAME = "requestName";
	public static final String REQUESTMESSAGE = "requestMessage";
	public static final String REQUESTNAMEID = "requestNameId";
	public static final String OBJECTID = "objectId";

	ArrayList<HashMap<String, String>> requestList;
	ListView requestListView;
	ListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.notification_fragment, container,
				false);
		ParseUser pUser = ParseUser.getCurrentUser();
		String userName = pUser.getUsername();
		requestListView = (ListView) root.findViewById(R.id.requestList);

		ParseQuery<ParseObject> query = ParseQuery.getQuery(REQUEST);
		query.whereEqualTo(PLAYER_NAME, userName);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> request, ParseException e) {
				if (e == null) {

					requestList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					for (ParseObject parseObject : request) {
						if (e == null) {
							map = new HashMap<String, String>();
							map.put(OBJECTID, parseObject.getObjectId());
							Log.i("MSG","Object ID   "+parseObject.getObjectId());
							map.put(REQUESTNAME,parseObject.getString(REQUEST_NAME));
							Log.d("score","Retrieved "+ parseObject.getString(REQUEST_NAME));
							map.put(REQUESTMESSAGE,parseObject.getString(REQUEST_MESSAGE));
							Log.d("score","Retrieved "+ parseObject.getString(REQUEST_MESSAGE));
							map.put(REQUESTNAMEID,parseObject.getString(REQUEST_USER_ID));
							Log.d("score","Retrieved "+ parseObject.getString(REQUEST_USER_ID));
							requestList.add(map);
						} else {
							Log.d("MSG", "Error: " + e.getMessage());
						}
						adapter = new SimpleAdapter(NavigationDrawerActivity
								.getInstance(), requestList,
								R.layout.request_item, new String[] {
							REQUESTNAME, REQUESTMESSAGE },
							new int[] { R.id.requestName,
							R.id.requestMessage });
						requestListView.setAdapter(adapter);
						requestListView
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(
									AdapterView<?> parent, View view,
									int position, long id) {
								Log.d("Vinay",
										"Inside On item click listener AdapterView");
								HashMap<String, String> item = requestList
										.get(position);
								Intent intent = new Intent(NavigationDrawerActivity.getInstance(),ReplyActivity.class);
								intent.putExtra(OBJECTID,item.get(OBJECTID));
								intent.putExtra(REQUESTNAME,item.get(REQUESTNAME));
								Log.d("Vinay", item.get(REQUESTNAME));
								intent.putExtra(REQUESTMESSAGE,item.get(REQUESTMESSAGE));
								Log.d("Vinay", item.get(REQUESTMESSAGE));
								intent.putExtra(REQUESTNAMEID,item.get(REQUESTNAMEID));
								Log.d("Vinay", item.get(REQUESTNAMEID));
								startActivity(intent);
								Log.d("Vinay","Inside On item click listener AdapterView");
							}
						});

					}
				}
			}
		});

		return root;
	}

}
