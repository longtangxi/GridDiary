package com.gridnote.app.db;

import com.gridnote.app.constant.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;	
	public static final String DBNAME = "griddiary.db";
	
	public DataBaseHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	public DataBaseHelper(Context context,int version) {
		super(context, DBNAME, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Constant.CREATE_DIARY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
