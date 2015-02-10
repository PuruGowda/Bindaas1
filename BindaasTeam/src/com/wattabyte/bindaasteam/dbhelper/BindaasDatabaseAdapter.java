package com.wattabyte.bindaasteam.dbhelper;

import com.wattabyte.bindaasteam.util.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BindaasDatabaseAdapter  {
	
	BindaasHelper helper;
	
	public BindaasDatabaseAdapter(Context context ) {
		helper =new  BindaasHelper(context);
	}
	
	public long insertData( String teamName ,String leagueName ){
		
		SQLiteDatabase db =  helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
//		String points = "";
		contentValues.put(BindaasHelper.TEAM_NAME, teamName);
		contentValues.put(BindaasHelper.LEAGUE_NAME, leagueName);
		long id = db.insert(BindaasHelper.TABLE_NAME, null, contentValues);
		return id;
	}
	
	public String getAllData(){
		SQLiteDatabase db = helper.getWritableDatabase();
		//select_id, Name , Password From VinayTable;
		String[] columns = {BindaasHelper.UID , BindaasHelper.TEAM_NAME, BindaasHelper.LEAGUE_NAME};
		Cursor cursor =  db.query(BindaasHelper.TABLE_NAME, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			 int cid = cursor.getInt(0);
			 String  name = cursor.getString(1);
			 String password = cursor.getString(2);
			 buffer.append(cid+ " " + name + " " + password+"\n");
		}
		return buffer.toString();
	}
	
	public String getData(String name){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		//select_id, Name , Password From VinayTable;
		String[] columns = { BindaasHelper.TEAM_NAME, BindaasHelper.LEAGUE_NAME};
		Cursor cursor =  db.query(BindaasHelper.TABLE_NAME, columns, BindaasHelper.TEAM_NAME + " ='" + name +"'", null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			int index1	= cursor.getColumnIndex(BindaasHelper.TEAM_NAME);
			int index2	= cursor.getColumnIndex(BindaasHelper.LEAGUE_NAME);
			 String  personName = cursor.getString(index1);
			 String password = cursor.getString(index2);
			 buffer.append( personName + " " + password+"\n");
		}
		
		return buffer.toString();
	}

	static class BindaasHelper extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "TeamDatabase";
		private static final String TABLE_NAME = "TeamTable";
		private static final int DATABASE_VERSION = 1;
		private static final String UID = "_id";
		private static final String TEAM_NAME = "TeamName";
		private static final String LEAGUE_NAME = "GroupName";
		private static final String POINTS = "Points";
//		private static final String MEMBERS = "Members";
		
		private Context context;
		private static final String CREATE = " CREATE TABLE " + TABLE_NAME 
				+ " ("+ UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ TEAM_NAME + " VARCHAR(255), " + LEAGUE_NAME + " VARCHAR(255),"+ POINTS+" VARCHAR(255) );";
		private static final String DROP_TABLE = "DROP TABLE  IF EXISTS "
				+ TABLE_NAME;

		public BindaasHelper(Context context) {
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
				// TODO Auto-generated catch block
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
