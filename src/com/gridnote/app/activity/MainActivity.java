package com.gridnote.app.activity;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.gridnote.app.R;
import com.gridnote.app.db.DataBaseHelper;
import com.gridnote.app.db.DiaryDB;
import com.gridnote.app.model.Diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView timeTextView = null;
	private TextView weekTextView = null;
	private Spinner weatherSpinner = null;
	private EditText firstAnswer = null;
	private EditText secondAnswer = null;
	private EditText thirdAnswer = null;
	private EditText forthAnswer = null;
	private EditText fifthAnswer = null;
	private EditText sixthAnswer = null;
	private Calendar cal = Calendar.getInstance();
	private Date date = null;
	private SimpleDateFormat simpleDateFormat = null;
	public static final int WEEKDAYS = 7;
	public static String[] WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
	"星期六" };
	
	private Button btnsave;
	private Button btncalendar;
	private Button btnread;
	
	private DataBaseHelper dbHelper;
	public String nowDate = null;
	public String nowWeek = null;
 
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		date = cal.getTime();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		timeTextView = (TextView) findViewById(R.id.time);
		weekTextView = (TextView) findViewById(R.id.week);
		weatherSpinner = (Spinner) findViewById(R.id.weather);
		
		firstAnswer = (EditText) findViewById(R.id.first_answer);
		secondAnswer = (EditText) findViewById(R.id.second_answer);
		thirdAnswer = (EditText) findViewById(R.id.third_answer);
		forthAnswer = (EditText) findViewById(R.id.forth_answer);
		fifthAnswer = (EditText) findViewById(R.id.fifith_answer);
		sixthAnswer = (EditText) findViewById(R.id.sixth_answer);
		
		nowDate = simpleDateFormat.format(date);
		nowWeek = DateToWeek(date);
		Log.d("1", nowDate);
		Log.d("2", nowWeek);
		
		timeTextView.setText(nowDate);
		weekTextView.setText(nowWeek);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weather
				, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		weatherSpinner.setAdapter(adapter);
		weatherSpinner.getSelectedItem().toString();
		btnsave = (Button) findViewById(R.id.btnsave);
		btncalendar = (Button) findViewById(R.id.btncalendar);
		btnread = (Button) findViewById(R.id.btnread);
		
		
		btnsave.setOnClickListener(new MyClickListener());
		btncalendar.setOnClickListener(new MyClickListener());
		btnread.setOnClickListener(new MyClickListener());

		dbHelper = new DataBaseHelper(this);
		
	}
	
	class MyClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnsave:
				SaveAnswers();
				break;
			case R.id.btnread:
				Log.d("3", "nowDate");
				ReadingView();

				break;
			case R.id.btncalendar:
				CalendarView();
				break;
			default:
				break;
			}
		}
	}
	//进入日历模式
	private void CalendarView() {
		Intent intent = new Intent(MainActivity.this,CalendarMode.class);
		startActivity(intent);
	}
	//进入阅读模式
	private void ReadingView() {
		Intent intent = new Intent(MainActivity.this,ReadMode.class);
		intent.putExtra("extra_date", nowDate);
		intent.putExtra("extra_week", nowWeek);
		startActivity(intent);
	}
	//保存当日日记
	private void SaveAnswers() {
		DiaryDB diaryDB = new DiaryDB(MainActivity.this);
		Diary diary = new Diary();
		
		diary.setDate(timeTextView.getText().toString());
		diary.setWeek(weekTextView.getText().toString());
		diary.setWeather(weatherSpinner.getSelectedItem().toString());
		diary.setFirstAnswer(firstAnswer.getText().toString());
		diary.setSecondAnswer(secondAnswer.getText().toString());
		diary.setThirdAnswer(thirdAnswer.getText().toString());
		diary.setForthAnswer(forthAnswer.getText().toString());
		diary.setFifthAnswer(fifthAnswer.getText().toString());
		diary.setSixthAnswer(sixthAnswer.getText().toString());

		String key = timeTextView.getText().toString();
		diaryDB.insert(diary,key);
		Log.d("test","测试通过 ");
	}
	
	public static String DateToWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayIndex <1 || dayIndex > WEEKDAYS){
			return null;
		}
		return WEEK[dayIndex - 1];//变换顺序
	}
	
}

