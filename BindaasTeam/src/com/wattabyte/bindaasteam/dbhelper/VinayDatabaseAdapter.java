package com.wattabyte.bindaasteam.dbhelper;

import com.wattabyte.bindaasteam.util.Message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VinayDatabaseAdapter  {
	
	VinayHelper helper;
	
	public VinayDatabaseAdapter(Context context ) {
		helper =new  VinayHelper(context);
	}
	
	public long insertData( String name ,String password){
		
		SQLiteDatabase db =  helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(VinayHelper.USER_NAME, name);
		contentValues.put(VinayHelper.PASSWORD, password);
		long id = db.insert(VinayHelper.TABLE_NAME, null, contentValues);
		return id;
	}
	
	public String getAllData(){
		SQLiteDatabase db = helper.getWritableDatabase();
		//select_id, Name , Password From VinayTable;
		String[] columns = {VinayHelper.UID , VinayHelper.USER_NAME, VinayHelper.PASSWORD};
		Cursor cursor =  db.query(VinayHelper.TABLE_NAME, columns, null, null, null, null, null);
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
		String[] columns = { VinayHelper.USER_NAME, VinayHelper.PASSWORD};
		Cursor cursor =  db.query(VinayHelper.TABLE_NAME, columns, VinayHelper.USER_NAME + " ='" + name +"'", null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext()) {
			int index1	= cursor.getColumnIndex(VinayHelper.USER_NAME);
			int index2	= cursor.getColumnIndex(VinayHelper.PASSWORD);
			 String  personName = cursor.getString(index1);
			 String password = cursor.getString(index2);
			 buffer.append( personName + " " + password+"\n");
		}
		
		return buffer.toString();
	}

	static class VinayHelper extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "vinaydatabase";
		private static final String TABLE_NAME = "VINAYTABLE";
		private static final int DATABASE_VERSION = 11;
		private static final String UID = "_id";
		private static final String USER_NAME = "UserName";
		private static final String PASSWORD = "Password";
		
		private Context context;
		private static final String CREATE = " CREATE TABLE " + TABLE_NAME 
				+ " ("+ UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ USER_NAME + " VARCHAR(255), " + PASSWORD + " VARCHAR(255) );";
		private static final String DROP_TABLE = "DROP TABLE  IF EXISTS "
				+ TABLE_NAME;

		public VinayHelper(Context context) {
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
