package com.wattabyte.bindaasteam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends ActionBarActivity {
	Button signup;
	EditText  userName, password , email;
	String un , pwd , mail;
	private static final String LOG = "MSG";
	boolean emailVerified;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		 
		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		email = (EditText) findViewById(R.id.email);
		
		signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				un = userName.getText().toString();
				pwd = password.getText().toString();
				mail = email.getText().toString();
				if(un != null  && !un.equals("") && pwd != null  && !pwd.equals("") && mail != null  && !mail.equals("")){
					
					ParseUser user = new ParseUser();
					user.setUsername(un);
					user.setPassword(pwd);
					user.setEmail(mail);
					user.saveInBackground();
					user.signUpInBackground(new SignUpCallback() {
						  public void done(ParseException e) {
						    if (e == null) {
						    	// Hooray! Let them use the app now.
						    	Log.i(LOG,"Welcome "+ un);
						    	Toast.makeText(SignUp.this, un + "   "+ pwd+"   "+ mail, Toast.LENGTH_LONG).show();
						    	AlertDialog.Builder alert = new AlertDialog.Builder(context);
								alert.setMessage("A link has been sent to your mail, "
										+ "click on link sent to your mail to continue");
								alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										finish();
						
									}
								});
									
								AlertDialog alertDialog = alert.create();
								alertDialog.show();
						    } else {
						      // Sign up didn't succeed. Look at the ParseException
						      // to figure out what went wrong
						    	
						    	AlertDialog.Builder alert = new AlertDialog.Builder(context);
								alert.setMessage("Sorry!! User name already exists");
								alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent  = new Intent(SignUp.this, SignUp.class);
					     	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					     	            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					     	            startActivity(intent);						
									}
								});
									
								AlertDialog alertDialog = alert.create();
								alertDialog.show();
								Log.i(LOG,""+ e);
						    }
						  }
						});
					
				}
				else
				{
					Toast.makeText(SignUp.this, "Fill the mandatory fields", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}

}
