package com.wattabyte.bindaasteam.teammanagement;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wattabyte.bindaasteam.R;
import com.wattabyte.bindaasteam.util.BindaasUtil;

public class Players extends ActionBarActivity {
	BindaasUtil bu;
	ListView lv;
	ListAdapter adapter;
	public static final String LOG = "MSG";
	ArrayList<String> list;
	ArrayList<String> lists;
	static String teamName;
	TextView tv;
	SelectPlayers sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		Log.i(LOG, "In Player Activity");
		lv = (ListView) findViewById(R.id.player_list);
		// tv = (TextView) findViewById(R.id.textView1);
		list = new ArrayList<String>();
		lists = new ArrayList<String>();
		Log.i(LOG, "Before try");
		// Log.i(LOG, sp.getName());
		// tv.setText(sp.getName());
		try {
//			list = SelectPlayers.getList();

			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);
			lv.setAdapter(adapter);
		} catch (NullPointerException e) {
			Log.i(LOG, "Caught exception " + e);
		}

	}
}
