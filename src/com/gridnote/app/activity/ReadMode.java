package com.gridnote.app.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import com.gridnote.app.R;
import com.gridnote.app.db.DataBaseHelper;
import com.gridnote.app.db.DiaryDB;
import com.gridnote.app.model.Diary;

import android.R.array;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;



public class ReadMode extends Activity {
	
	private Diary diary = null;
	private DiaryDB diaryDB = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.read_mode);
		Intent intent = getIntent();
		String datefrommain = intent.getStringExtra("extra_date");
		String weekfrommain = intent.getStringExtra("extra_week");
		Log.d("10",datefrommain);
		ListView listView = (ListView) findViewById(R.id.show);
		diaryDB = new DiaryDB(this);
		diary = new Diary();
		diaryDB.query(diary,datefrommain);
		
		String fir_question = (String) this.getResources().getText(R.string.first_question);
		String sec_question = (String) this.getResources().getText(R.string.second_question);
		String thi_question = (String) this.getResources().getText(R.string.third_question);
		String for_question = (String) this.getResources().getText(R.string.forth_question);
		String fif_question = (String) this.getResources().getText(R.string.fifth_question);
		String six_question = (String) this.getResources().getText(R.string.sixth_question);
		
		String questions[] = new String[]
				{fir_question,sec_question,thi_question
				,for_question,fif_question,six_question
				};
		String answers[] = new String[]
				{diary.getFirstAnswer(),diary.getSecondAnswer(),diary.getThirdAnswer()
				,diary.getForthAnswer(),diary.getFifthAnswer(),diary.getSixthAnswer()
				};
		List<Map<String, Object>> listItems = 
				new ArrayList<Map<String,Object>>();
		
		//将指定日期的回答数据存入ArrayList
		for(int i = 0 ; i < questions.length ; i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();		
			map.put("question",questions[i]);
			map.put("answer", answers[i]);
			listItems.add(map);
		}
			
		SimpleAdapter adapter = new SimpleAdapter(ReadMode.this
				,listItems,
				R.layout.answerfregment,new String[] {"question","answer"}
		,new int[] {R.id.question,R.id.answer});
		listView.setAdapter(adapter);
	}
}
