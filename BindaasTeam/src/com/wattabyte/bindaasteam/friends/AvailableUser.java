package com.wattabyte.bindaasteam.friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.MainActivity;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.util.Message;

public class AvailableUser extends ActionBarActivity {

	public static final String FRIEND_NAME = "FriendName";
	public static final String PLAYERNAME = "playerName";
	public static final String PLAYERID = "playerId";
	public static final String USER_NAME = "username";

	ParseUser user;
	String userName;
	ArrayList<HashMap<String, String>> playerList;
	ListView playerListView;
	ListAdapter adapter;
	EditText friendSearch;
	final ArrayList<String> friendArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_user);

		// To Initialize to display Frineds
		playerListView = (ListView) findViewById(android.R.id.list);

		// To get Current User
		user = ParseUser.getCurrentUser();
		userName = user.getUsername();

		// Find Friends Available In Parse
		ParseQuery<ParseUser> query = ParseQuery.getUserQuery();

		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					playerList = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> map;
					for (ParseUser parseUser : objects) {
						if (!userName.equals(parseUser.getUsername())) {

							map = new HashMap<String, String>();
							map.put(PLAYERNAME, parseUser.getUsername());
							map.put(PLAYERID, parseUser.getObjectId());
							playerList.add(map);

						}

					}
					// Setting for display Friends List
					adapter = new SimpleAdapter(AvailableUser.this, playerList,
							R.layout.player_item, new String[] { PLAYERNAME },
							new int[] { R.id.playerName });
					playerListView.setAdapter(adapter);

					// On Click Changing the list to send Request
					playerListView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									HashMap<String, String> item = playerList
											.get(position);
									Intent intent = new Intent(
											AvailableUser.this,
											RequestActivity.class);
									intent.putExtra(PLAYERNAME,
											item.get(PLAYERNAME));
									Log.d("Vinay", item.get(PLAYERNAME));
									intent.putExtra(PLAYERID,
											item.get(PLAYERID));
									Log.d("Vinay", item.get(PLAYERID));
									startActivity(intent);

								}
							});
				}
			}
		});

		// Setting Search for Friends on click to Edit Text
		friendSearch = (EditText) findViewById(R.id.friendsSearch);

		friendSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// To get Current User
				user = ParseUser.getCurrentUser();
				userName = user.getUsername();
				// Find Friends Available In Parse
				ParseQuery<ParseUser> query = ParseQuery.getUserQuery();
				String name = s.toString();
				if (name != null && !name.equals("")) {
					query.whereEqualTo(USER_NAME, name);

					query.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> objects,
								ParseException e) {
							if (e == null) {
								playerList = new ArrayList<HashMap<String, String>>();
								HashMap<String, String> map;

								for (ParseUser parseUser : objects) {

									if (!userName.equals(parseUser
											.getUsername())) {

										map = new HashMap<String, String>();
										map.put(PLAYERNAME,
												parseUser.getUsername());
										map.put(PLAYERID,
												parseUser.getObjectId());
										playerList.add(map);

									}

								}
								// Setting for display Friends List
								adapter = new SimpleAdapter(AvailableUser.this,
										playerList, R.layout.player_item,
										new String[] { PLAYERNAME },
										new int[] { R.id.playerName });
								playerListView.setAdapter(adapter);

								// On Click Changing the list to send Request
								playerListView
										.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(
													AdapterView<?> parent,
													View view, int position,
													long id) {
												HashMap<String, String> item = playerList
														.get(position);
												Intent intent = new Intent(
														AvailableUser.this,
														RequestActivity.class);
												intent.putExtra(PLAYERNAME,
														item.get(PLAYERNAME));
												Log.d("Vinay",
														item.get(PLAYERNAME));
												intent.putExtra(PLAYERID,
														item.get(PLAYERID));
												Log.d("Vinay",
														item.get(PLAYERID));
												startActivity(intent);

											}
										});
							} else {
								Toast.makeText(AvailableUser.this,
										"User Unretrieved", Toast.LENGTH_SHORT)
										.show();
							}
						}
					});
				}else{
					// Find Friends Available In Parse
					ParseQuery<ParseUser> query3 = ParseQuery.getUserQuery();

					query3.findInBackground(new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> objects, ParseException e) {
							if (e == null) {
								playerList = new ArrayList<HashMap<String, String>>();
								HashMap<String, String> map;
								for (ParseUser parseUser : objects) {
									if (!userName.equals(parseUser.getUsername())) {

										map = new HashMap<String, String>();
										map.put(PLAYERNAME, parseUser.getUsername());
										map.put(PLAYERID, parseUser.getObjectId());
										playerList.add(map);

									}

								}
								// Setting for display Friends List
								adapter = new SimpleAdapter(AvailableUser.this, playerList,
										R.layout.player_item, new String[] { PLAYERNAME },
										new int[] { R.id.playerName });
								playerListView.setAdapter(adapter);

								// On Click Changing the list to send Request
								playerListView
										.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> parent,
													View view, int position, long id) {
												HashMap<String, String> item = playerList
														.get(position);
												Intent intent = new Intent(
														AvailableUser.this,
														RequestActivity.class);
												intent.putExtra(PLAYERNAME,
														item.get(PLAYERNAME));
												Log.d("Vinay", item.get(PLAYERNAME));
												intent.putExtra(PLAYERID,
														item.get(PLAYERID));
												Log.d("Vinay", item.get(PLAYERID));
												startActivity(intent);

											}
										});
							}
						}
					});
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.available_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent intent , chooser;
		if (id == R.id.logout) {
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				ParseUser.logOut();
				intent = new Intent(getApplicationContext(),MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			return true;
		}
		
		else if(id == R.id.inviteMessage){
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.wattabyte.bindaasteam");
			chooser = Intent.createChooser(intent, "Messaging");
			startActivity(chooser);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
