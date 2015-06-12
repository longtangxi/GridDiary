package com.gridnote.app.activity;




import com.gridnote.app.R;
import com.gridnote.app.view.TwelveMonthCalendar;
import com.gridnote.app.view.TwelveMonthCalendar.OnDaySelectListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;



public class CalendarMode extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_mode);
		init();
	}
    private void init(){
        TwelveMonthCalendar c = (TwelveMonthCalendar) findViewById(R.id.tmc_calendar);
        c.setOnDaySelectListener(new OnDaySelectListener() {
            
            @Override
            public void onDaySelectListener(String date) {
                Toast.makeText(CalendarMode.this, date, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
