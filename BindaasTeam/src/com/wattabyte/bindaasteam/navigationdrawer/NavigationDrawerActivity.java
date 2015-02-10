package com.wattabyte.bindaasteam.navigationdrawer;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.MainActivity;
import com.wattabyte.bindaasteam.R;

public class NavigationDrawerActivity extends ActionBarActivity implements
		OnItemClickListener {
	
	public static final String REMEBER = "Remeber";
	public static final String LOGIN_CREDENTIALS = "Login Credentials";
	
	SharedPreferences sharedPreference;
	SharedPreferences.Editor editor;

	public static NavigationDrawerActivity instance;
	public static String fragmentName = "ProfileFragment()";

	public static NavigationDrawerActivity getInstance() {
		return instance;
	}

	private DrawerLayout drawerLayout;

	private ListView listView1;

	public static final String NAME = "Name";
	public static final String NAMES = "Names";
	private ActionBarDrawerToggle drawerListener;

	private String[] titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Fragment fragment = new ProfileFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.mainContent, fragment)
				.commit();

		ParseQuery<ParseObject> query = ParseQuery.getQuery(NAMES);
		query.whereExists(NAME);
		query.setLimit(2000);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> names, ParseException e) {
				if (e == null) {
					ParseUser currentUser = ParseUser.getCurrentUser();
					String userName = currentUser.getUsername();
					String teamName = "Team" + userName;
					String groupName = "Group" + userName;
					boolean flag = false;
					for (ParseObject name : names) {

						if (userName.equals(name.getString(NAME))) {
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						ParseObject name = new ParseObject(NAMES);
						name.put(NAME, "" + userName);
						name.saveInBackground();
					}
					for (ParseObject name : names) {

						if (teamName.equals(name.getString(NAME))) {
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						ParseObject team = new ParseObject(NAMES);
						team.put(NAME, "" + teamName);
						team.saveInBackground();
					}
					for (ParseObject name : names) {

						if (groupName.equals(name.getString(NAME))) {
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						ParseObject group = new ParseObject(NAMES);
						group.put(NAME, "" + groupName);
						group.saveInBackground();
					}
				} else {

				}

			}
		});

		instance = this;
		setContentView(R.layout.activity_navigation);

		try {
			AdView mAdView = (AdView) findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder().build();
			mAdView.loadAd(adRequest);
		} catch (InflateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		titles = getResources().getStringArray(R.array.menu);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer2, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {

				Toast.makeText(NavigationDrawerActivity.this,
						"on drawer close", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onDrawerOpened(View drawerView) {

				Toast.makeText(NavigationDrawerActivity.this, "on drawer open",
						Toast.LENGTH_SHORT).show();
			}
		};
		drawerLayout.setDrawerListener(drawerListener);

		listView1 = (ListView) findViewById(R.id.drawerListView);

		listView1.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles));

		listView1.setOnItemClickListener(this);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		int id = item.getItemId();
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		} else if (id == R.id.action_logout) {
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				ParseUser.logOut();
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				sharedPreference = getSharedPreferences(LOGIN_CREDENTIALS, Context.MODE_PRIVATE);
				if (sharedPreference != null ) {
					editor = sharedPreference.edit();
					editor.putBoolean(REMEBER, false);
					editor.commit();
				}
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}

	public void selectItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new ProfileFragment();
			break;
		case 1:
			fragment = new FriendsFragment();
			break;
		case 2:
			fragment = new TeamManagementFragment();
			break;
		case 3:
			fragment = new GroupManagementFragment();
			break;
		case 4:
			fragment = new FreindRequestFragment();
			break;
		case 5:
			fragment = new GroupRequestFragment();
			break;
		case 6:
			fragment = new HowToPlayFragment();
			break;
		default:
			fragment = new ProfileFragment();

		}
		// update the main content by replacing fragments
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.mainContent, fragment).commit();

			// update selected item and title, then close the drawer
			listView1.setItemChecked(position, true);
			setTitle(titles[position]);
			drawerLayout.closeDrawer(listView1);
		}

	}

	public void setTitle(String title) {

		getSupportActionBar().setTitle(title);
	}

}
