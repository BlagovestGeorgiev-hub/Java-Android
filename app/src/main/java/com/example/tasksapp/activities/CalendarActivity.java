package com.example.tasksapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.tasksapp.R;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(CalendarActivity.this, R.style.CalendarTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            }
        }, mYear, mMonth, mDay).show();
    }
}
