package com.gridnote.app.db;


import java.util.List;

import com.gridnote.app.model.Diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DiaryDB{

	private DataBaseHelper helper = null;
	
	public DiaryDB(Context context) {
		helper = new DataBaseHelper(context);
	}
	
	public void insert(Diary diary,String key){
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from GRIDDIARY where date like ?"
				, new String[] {"%" + key + "%"});
		if(cursor != null)
		{
			while (cursor.moveToNext()) 
			{
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				db.execSQL("delete from GRIDDIARY where id = " + id);
			}
		}
		ContentValues values = new ContentValues();
		values.put("date", diary.getDate());
		values.put("week", diary.getWeek());
		values.put("weather", diary.getWeather());
		values.put("firstanswer", diary.getFirstAnswer());
		values.put("secondanswer", diary.getSecondAnswer());
		values.put("thirdanswer", diary.getThirdAnswer());
		values.put("forthanswer", diary.getForthAnswer());
		values.put("fifthanswer", diary.getFifthAnswer());
		values.put("sixthanswer", diary.getSixthAnswer());
		db.insert("GRIDDIARY", null, values);
		db.close();
	}
	
private void Log(String string) {
		// TODO Auto-generated method stub
		
	}

	/*	public void delete(String date) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from GRIDDIARY where date = " + 10);
		db.close();
	}*/
	public void delete(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("delete from GRIDDIARY where id = " + id);
		db.close();
	}
	
/*	public void query(List<Diary> diaries) {

		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("GRIDDIARY", null, null, 
				null, null, null, "id desc");
		diaries.clear();
		while (cursor.moveToNext()) {
			Diary diary = new Diary();*/
	public void query(Diary diary,String day) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
			"select * from GRIDDIARY where date like ?", new String[] {"%" + day + "%"});
			cursor.moveToFirst();
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String week = cursor.getString(cursor.getColumnIndex("week"));
			String weather = cursor.getString(cursor.getColumnIndex("weather"));
			
			String firstanswer = cursor.getString(cursor.getColumnIndex("firstanswer"));
			String secondanswer = cursor.getString(cursor.getColumnIndex("secondanswer"));
			String thirdanswer = cursor.getString(cursor.getColumnIndex("thirdanswer"));
			String forthanswer = cursor.getString(cursor.getColumnIndex("forthanswer"));
			String fifthanswer = cursor.getString(cursor.getColumnIndex("fifthanswer"));
			String sixthanswer = cursor.getString(cursor.getColumnIndex("sixthanswer"));
			
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			
			diary.setDate(date);
			diary.setWeek(week);
			diary.setWeather(weather);
			diary.setFirstAnswer(firstanswer);
			diary.setSecondAnswer(secondanswer);
			diary.setThirdAnswer(thirdanswer);
			diary.setForthAnswer(forthanswer);
			diary.setFifthAnswer(fifthanswer);
			diary.setSixthAnswer(sixthanswer);
			diary.setId(id);

		cursor.close();
		db.close();
	}
	
	public void deleteAll(){
		String delete_sql = "delete from GRIDDIARY";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(delete_sql);
		db.close();
	}

}
