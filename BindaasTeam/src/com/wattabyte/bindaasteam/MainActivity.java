package com.wattabyte.bindaasteam;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.internal.ed;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wattabyte.bindaasteam.navigationdrawer.NavigationDrawerActivity;

public class MainActivity extends ActionBarActivity {

	public static final String PASSWORD2 = "Password";
	public static final String USER_NAME = "UserName";
	public static final String REMEBER = "Remeber";
	public static final String LOGIN_CREDENTIALS = "Login Credentials";
	public static MainActivity instance;
	EditText userName, password;
	Button login, signup;
	String name, pwd;
	boolean flag = false;
	final Context context = this;
	CheckBox remember;
	SharedPreferences sharedPreference;
	SharedPreferences.Editor editor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		
		
		
		try {
			AdView mAdView = (AdView) findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder().build();
			mAdView.loadAd(adRequest);
		} catch (InflateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		login = (Button) findViewById(R.id.login);
		signup = (Button) findViewById(R.id.signup);
		remember = (CheckBox) findViewById(R.id.remember);
		
		sharedPreference = getSharedPreferences(LOGIN_CREDENTIALS, Context.MODE_PRIVATE);
		if (sharedPreference.getBoolean(REMEBER, false) && sharedPreference != null) {
			name = sharedPreference.getString(USER_NAME, "");
			pwd = sharedPreference.getString(PASSWORD2, "");
			ParseUser.logInInBackground(name.trim(), pwd.trim(), new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						Intent in = new Intent(MainActivity.this,
								NavigationDrawerActivity.class);
						startActivity(in);
					} else {
						// Signup failed. Look at the ParseException to
						// see what happened.
						ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
						query.whereEqualTo("username", name);
						query.findInBackground(new FindCallback<ParseObject>() {
							
							@Override
							public void done(List<ParseObject> name, ParseException e) {
								if(e == null)
								{
									flag = true;
								}
								else
								{
									Log.i("MSG", ""+e);
								}
							}
						});
						if(flag == false)
						{
							AlertDialog.Builder alert = new AlertDialog.Builder(
									context);
							alert.setMessage("Invalid user name and password");
							alert.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									});
							AlertDialog alertDialog = alert.create();
							alertDialog.show();
						}
						else
						{
						AlertDialog.Builder alert = new AlertDialog.Builder(
								context);
						alert.setMessage("Invalid user name and password");
						alert.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialog,
											int which) {

									}
								});
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
						}
					}
				}
			});

		}
		
	
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				name = userName.getText().toString();
				pwd = password.getText().toString();
				if (name != null && !name.equals("") && pwd != null
						&& !pwd.equals("")) {
					if (remember.isChecked()) {
						sharedPreference = getSharedPreferences(LOGIN_CREDENTIALS, Context.MODE_PRIVATE);
						editor = sharedPreference.edit();
						editor.putBoolean(REMEBER, true);
						editor.putString(USER_NAME, name);
						editor.putString(PASSWORD2, pwd);
						editor.commit();
					}else{
						sharedPreference = getSharedPreferences(LOGIN_CREDENTIALS, Context.MODE_PRIVATE);
						editor = sharedPreference.edit();
						editor.putBoolean(REMEBER, false);
						editor.commit();
					}

					ParseUser.logInInBackground(name.trim(), pwd.trim(), new LogInCallback() {
						public void done(ParseUser user, ParseException e) {
							if (user != null) {
								Intent in = new Intent(MainActivity.this,
										NavigationDrawerActivity.class);
								startActivity(in);
							} else {
								// Signup failed. Look at the ParseException to
								// see what happened.
								ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
								query.whereEqualTo("username", name);
								query.findInBackground(new FindCallback<ParseObject>() {
									
									@Override
									public void done(List<ParseObject> name, ParseException e) {
										if(e == null)
										{
											flag = true;
										}
										else
										{
											Log.i("MSG", ""+e);
										}
									}
								});
								if(flag == false)
								{
									AlertDialog.Builder alert = new AlertDialog.Builder(
											context);
									alert.setMessage("Invalid user name and password");
									alert.setPositiveButton("Ok",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {

												}
											});
									AlertDialog alertDialog = alert.create();
									alertDialog.show();
								}
								else
								{
								AlertDialog.Builder alert = new AlertDialog.Builder(
										context);
								alert.setMessage("Invalid user name and password");
								alert.setPositiveButton("Ok",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {

											}
										});
								AlertDialog alertDialog = alert.create();
								alertDialog.show();
								}
							}
						}
					});
				} else {
					Toast.makeText(MainActivity.this, "Fill the fields",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		
		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SignUp.class);
				startActivity(i);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	@Override
	public void onBackPressed() {
	    Intent startMain = new Intent(Intent.ACTION_MAIN);      
	        startMain.addCategory(Intent.CATEGORY_HOME);                        
	        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);          
	        startActivity(startMain); 
	  }

	

}
