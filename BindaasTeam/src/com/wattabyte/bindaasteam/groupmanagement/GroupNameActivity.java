package com.wattabyte.bindaasteam.groupmanagement;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wattabyte.bindaasteam.R;

public class GroupNameActivity extends ActionBarActivity {

	public static final String GROUP_NAME = "groupName";
	public static final String BINDAAS_TEAM_NAME = "BindaasTeamNames";
	public static final String BINDAAS_NAMES = "BindaasNames";
	
	public static final String NAMES = "Names";
	public static final String NAME = "Name";
	
	EditText groupName;
	Button next;
	
	private static final String LOG = "MSG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_name);

		groupName = (EditText) findViewById(R.id.groupName);
		
		next = (Button) findViewById(R.id.next);

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery(NAMES);
			query.whereExists(NAME);
			final ArrayList<String> nameArray = new ArrayList<String>();
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> names, ParseException e) {
					if (e == null) {
						Toast.makeText(GroupNameActivity.this,""+names.size() , Toast.LENGTH_LONG).show();
						for (ParseObject name : names) {
							nameArray.add(name.getString(NAME));
						}
						Log.d("MSG", nameArray.toString());
						String groupNameText = groupName.getText().toString();
						if (groupNameText != null && !groupNameText.equals("")) {
							boolean flag = false ;
							for (String string : nameArray) {
								if (groupNameText.equals(string)) {
									Toast.makeText(GroupNameActivity.this,"Equal", Toast.LENGTH_SHORT).show();
									flag = false;
									break;
								} else {
									Toast.makeText(GroupNameActivity.this,"Not Equal", Toast.LENGTH_SHORT).show();
									flag = true;
								}
							}
							if(flag){
								
								Intent i = new Intent(GroupNameActivity.this,
										LeagueSelectActivity.class);
								i.putExtra(GROUP_NAME, groupNameText);
								
								ParseObject name = new ParseObject(""+NAMES);
								name.put(NAME,groupNameText);
								name.saveInBackground();
								Log.i(LOG, "Starting Activity");
								
								startActivity(i);
								Toast.makeText(GroupNameActivity.this,""+flag , Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(GroupNameActivity.this,""+flag , Toast.LENGTH_SHORT).show();
								Toast.makeText(GroupNameActivity.this,"Change The Team Name Team Name Already Exist" , Toast.LENGTH_SHORT).show();
							}
		
						} else {
							Toast.makeText(GroupNameActivity.this, "Please Enter the Empty Fields", Toast.LENGTH_SHORT).show();
						}
											
					} else {
						Toast.makeText(GroupNameActivity.this,"Not Retrieved" , Toast.LENGTH_LONG).show();
					}
					
				}
			});
			

			}
		});


	}

}
