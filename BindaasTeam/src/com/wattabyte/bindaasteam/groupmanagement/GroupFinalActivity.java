package com.wattabyte.bindaasteam.groupmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.navigationdrawer.NavigationDrawerActivity;
import com.wattabyte.bindaasteam.teammanagement.SelectPlayers;

public class GroupFinalActivity extends ActionBarActivity {
	
	public static final String GROUP_NAME = "GroupName";
	public static final String LEAGUE_NAME = "LeagueName";
	public static final String PLAYER_NAME = "PlayerName";
	public static final String TEAM_NAME = "Name";
	public static final String POINTS = "Points";
	
	ListView resultListView, lv;
	ListAdapter adapter;
	 ArrayAdapter<String> arrayadapter;
	ArrayList<HashMap<String, String>> resultList;
	 ArrayList<String> res = new ArrayList<String>();
	 ArrayList<String> fres = new ArrayList<String>();	 
	static String teamName;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_final);
		lv = (ListView) findViewById(R.id.listView);
		Intent i = getIntent();
		final String groupName  = i.getStringExtra(GROUP_NAME);
		Log.d("MSG", groupName);
		final String leagueName = i.getStringExtra(LEAGUE_NAME);
		Log.d("MSG", leagueName);
		resultListView = (ListView) findViewById(R.id.listView);
		Log.i("MSG", "Inside group final activity");
//		ParseQuery<ParseObject> query = ParseQuery.getQuery(""+groupName);
//		query.whereExists(PLAYER_NAME);
//		query.findInBackground(new FindCallback<ParseObject>() {
//			
//			@Override
//			public void done(List<ParseObject> groups, ParseException e) {
//				if(e == null){
//					Toast.makeText(GroupFinalActivity.this, "Groups Retrieved "+groups.size(), 
//							Toast.LENGTH_SHORT).show();
//				
//					String groupHead = null;
//				for (ParseObject parseObject : groups) {
//					
//					Log.d("MSG", parseObject.getString(PLAYER_NAME));
//					groupHead = parseObject.getString(PLAYER_NAME);
//					
//					ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Team"+parseObject.getString(PLAYER_NAME));
//					
//					query2.whereEqualTo(LEAGUE_NAME,leagueName);
//					query2.findInBackground(new FindCallback<ParseObject>() {
//						
//						@Override
//						public void done(List<ParseObject> leagues, ParseException e2) {
//							if (e2 == null) {
////								Toast.makeText(GroupFinalActivity.this, "Groups Retrieved "+leagues.size(), 
////										Toast.LENGTH_SHORT).show();
//								Log.i("MSG", "Inside"+leagues.size());
//								
//								for (ParseObject parseObject2 : leagues) {
//									
//									ParseQuery<ParseObject> query3 = ParseQuery.getQuery(""+parseObject2.getString(GROUP_NAME));
//									query3.whereExists(TEAM_MEMBER);
//									query3.findInBackground(new FindCallback<ParseObject>() {
//										
//										@Override
//										public void done(List<ParseObject> teams, ParseException e3) {
//											if (e3 == null) {
////												Toast.makeText(GroupFinalActivity.this, "Groups Retrieved "+teams.size(), 
////														Toast.LENGTH_SHORT).show();
//												int j = 0;
//												String name =null;
//												resultList = new ArrayList<String>();
//												HashMap<String, String> map;
//												for (ParseObject parseObject3 : teams) {
//													
//													name = parseObject3.getClassName();
////													Log.d("MSG",parseObject3.getClassName());
//													
////													Log.d("MSG",parseObject3.getString(POINTS));
//													j = j+Integer.parseInt(parseObject3.getString(POINTS));
////													Log.d("MSG",""+j);
//													
//												}
//												Log.i("MSG",name+"        "+j);
//												String finalRes = name+"         "+j;
//												for(int i=0; i<res.length; i++)
//												{
//													res[i] = finalRes;
//												
//												}
//												resultList.add(finalRes);
//												Log.i("MSG",resultList+"");
//												arrayadapter = new ArrayAdapter<String>(GroupFinalActivity.this, 
//														android.R.layout.simple_list_item_1,resultList);
//												
//								}
////											else {
////												Toast.makeText(GroupFinalActivity.this, "Groups UnRetrieved ", 
////														Toast.LENGTH_SHORT).show();
////											}
//								}
//								});
//																	
//								}
//								lv.setAdapter(arrayadapter);
//								Log.i("MSG", ""+res);
//							} else {
//								Log.d("MSG", "Error: " + e2.getMessage());
//							}
//							
//						}
//					});
//					
//					
//				}
//				
////				Log.i("MSG", groupHead+"  "+name+"        "+j);
//				}
//				else{
//					Log.d("MSG", "Error: " + e.getMessage());
//				}
//				
//		
//		
//			}
//		});
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(""+groupName);
		query.whereExists(PLAYER_NAME);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> names, ParseException e) {
//				int i = 0;
				if(e == null)
				{
					for(ParseObject name:names)
					{
						res.add(name.getString("PlayerName"));
						Log.i("MSG", name.getString("PlayerName"));
					}
				}
				else
				{
					Log.i("MSG", ""+e);
					Toast.makeText(GroupFinalActivity.this, ""+e, Toast.LENGTH_SHORT).show();
				}
				Log.i("MSG", "Comming out");
			
		
		Iterator<String> itr = res.iterator();
		while(itr.hasNext())
		{
			//Log.i("MSG", itr.next());
			String name = itr.next();
						ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Team"+name);
						query1.whereEqualTo("LeagueName",leagueName);
						query1.findInBackground(new FindCallback<ParseObject>() {
							@Override
							public void done(List<ParseObject> finalRes, ParseException e) {
								resultList = new ArrayList<HashMap<String, String>>();
								HashMap<String, String> map;
								for(ParseObject obj : finalRes)
								{
								if(e == null)
								{
									map = new HashMap<String, String>();
									String gname = obj.getString("GroupName");
									map.put(GROUP_NAME,gname );
									Log.i("MSG",obj.getString("GroupName"));
									String po = obj.getString("Points");
									map.put(POINTS,po);
									Log.i("MSG",obj.getString("Points"));
									resultList.add(map);
									Log.i("MSG", resultList.toString());
									fres.add(gname+"           "+po);
									Log.i("MSG", "fres"+fres.toString());
//									adapter = new SimpleAdapter(GroupFinalActivity.this,
//											resultList, R.layout.final_list, new String[] {
//													GROUP_NAME, POINTS}, new int[] {
//													R.id.groupName, R.id.points});
//									adapter = new SimpleAdapter(GroupFinalActivity.this, fres,
//											R.layout.final_list, new String[]{GROUP_NAME,POINTS},
//											new int[]{R.id.groupName , R.id.po});
									adapter = new ArrayAdapter<String>(GroupFinalActivity.this,
											android.R.layout.simple_list_item_1, fres);
									lv.setAdapter(adapter);
									lv.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> parent, View view,
												int position, long id) {
											Log.i("MSG", "HAI");
											
										}
									});
								}
								else
								{
									Log.i("MSG", ""+e);
								}
								
								}
//								adapter = new SimpleAdapter(GroupFinalActivity.this,
//										resultList, R.layout.final_list, new String[] {
//												GROUP_NAME, POINTS}, new int[] {
//												R.id.groupName, R.id.points});
//								lv.setAdapter(adapter);
				
							}
							
		});
						
		}
		
}
					
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_final, menu);
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
