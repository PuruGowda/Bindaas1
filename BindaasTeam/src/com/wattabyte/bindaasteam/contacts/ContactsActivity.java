package com.wattabyte.bindaasteam.contacts;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wattabyte.bindaasteam.R;



public class ContactsActivity extends ActionBarActivity implements OnItemClickListener{
	
	private ArrayList<ContactUtil> listContacts;
    private ListView lvContacts;
    ConatctsAdapter adapterContacts;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact); 
    listContacts = new ContactFetcher(this).fetchAll();
    lvContacts = (ListView) findViewById(android.R.id.list);
     adapterContacts = new ConatctsAdapter(this, listContacts);
    lvContacts.setAdapter(adapterContacts);
    lvContacts.setOnItemClickListener(this);
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.fblogout, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
   
   
    return super.onOptionsItemSelected(item);
}


@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
	
	
}




}
