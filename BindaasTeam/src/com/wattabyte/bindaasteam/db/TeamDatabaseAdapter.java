package com.wattabyte.bindaasteam.db;



import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wattabyte.bindaasteam.util.Message;

public class TeamDatabaseAdapter {

	
	static class BindaasDBHelper extends SQLiteOpenHelper{
		
		private static final String DATABASE_NAME = "BindaasDataBase";
		private static final String TABLE_NAME = "BINDAASTABLE";
		private static final int DATABASE_VERSION = 1;
		private static final String UID = "_id";
		private static final String TEAM_NAME = "TeamName";
		private static final String LEAGUE_NAME = "LeagueName";
		private static final String POINTS = "Points";
		
		private static final String CREATE = " CREATE TABLE " + TABLE_NAME 
				+ " ("+ UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ TEAM_NAME + " VARCHAR(255), " + LEAGUE_NAME + " VARCHAR(255) ,"+ POINTS+" VARCHAR(255) );";
		private static final String DROP_TABLE = "DROP TABLE  IF EXISTS "
				+ TABLE_NAME;
		
		private Context context;
		
		public BindaasDBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			Message.message(context, "Constructor Called");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				// CREATE TABLE VINAYTABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT,
				// Name VARCHAR(255));
				db.execSQL(CREATE);
				Message.message(context, "onCreate Call");
			} catch (SQLException e) {
				Message.message(context, "" + e);
			}

			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
				Message.message(context, "onUpgrade Call");
			} catch (SQLException e) {
				Message.message(context, "" + e);
			}
			
		}
		
	}
	
}
