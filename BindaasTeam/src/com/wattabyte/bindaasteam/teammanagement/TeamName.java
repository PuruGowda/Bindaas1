package com.wattabyte.bindaasteam.teammanagement;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.util.BindaasUtil;
import com.wattabyte.bindaasteam.util.CheckName;
import com.wattabyte.bindaasteam.util.Message;

public class TeamName extends ActionBarActivity {

	public static final String NAMES = "Names";
	public static final String NAME = "Name";
	public static final String GROUP = "Group";
	public static final String TEAM = "Team";
	EditText editText;
	Button button;
	BindaasUtil Bu;
	// static String tName;
	// public final static ArrayList<String> tN = new ArrayList<String>();
	private static final String LOG = "MSG";
	 boolean flag = false;

	CheckName checkNames;

	// public TeamName()
	// {
	//
	// }
	//
	// public TeamName(String tname)
	// {
	// tN.add(tname);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_name);
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String name = editText.getText().toString();	
				
				if (name.substring(0,4).equals(GROUP)) {
					Message.message(TeamName.this, "Name Already exists");
					Log.d("MSG", "Entered inside check group name");
				}
				else if (name.substring(0,3).equals(TEAM)) {
					Log.d("MSG", "Entered inside check team name");
					Message.message(TeamName.this, "Name Already exists");
				}
				else{				
					ParseQuery<ParseObject> query = ParseQuery.getQuery(NAMES);
					query.whereExists(NAME);
					final ArrayList<String> nameArray = new ArrayList<String>();
					query.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> names,
								ParseException e) {
							if (e == null) {
								Toast.makeText(TeamName.this,
										"" + names.size(), Toast.LENGTH_LONG)
										.show();
								for (ParseObject name : names) {
									nameArray.add(name.getString(NAME));
								}
								Log.d("MSG", nameArray.toString());
								String teamName = editText.getText().toString();
								if (teamName != null && !teamName.equals("")) {

									boolean flag = false;
									for (String string : nameArray) {
										if (teamName.equals(string)) {
											Toast.makeText(TeamName.this,
													"Equal", Toast.LENGTH_SHORT)
													.show();
											flag = false;
											break;
										} else {
											Toast.makeText(TeamName.this,
													"Not Equal",
													Toast.LENGTH_SHORT).show();
											flag = true;
										}
									}
									if (flag) {

										Intent i = new Intent(TeamName.this,
												SelectLeague.class);
										i.putExtra("text", editText.getText()
												.toString());
										// new
										// TeamName(editText.getText().toString());
										ParseObject name = new ParseObject(""
												+ NAMES);
										name.put(NAME, teamName);
										name.saveInBackground();
										Log.i(LOG, "Starting Activity");
										// Log.i(LOG, tN.toString());
										startActivity(i);
										Toast.makeText(TeamName.this,
												"" + flag, Toast.LENGTH_SHORT)
												.show();
									} else {
										Toast.makeText(TeamName.this,
												"" + flag, Toast.LENGTH_SHORT)
												.show();
										Toast.makeText(
												TeamName.this,
												"Change The Team Name Team Name Already Exist",
												Toast.LENGTH_SHORT).show();
									}

								} else {
									Toast.makeText(TeamName.this,
											"Please Enter the Empty Fields",
											Toast.LENGTH_SHORT).show();
								}
							} else {
								Toast.makeText(TeamName.this, "Not Retrieved",
										Toast.LENGTH_LONG).show();
							}
						}
					});

				} 
			}
		});
		
		
		
	}
		
}
