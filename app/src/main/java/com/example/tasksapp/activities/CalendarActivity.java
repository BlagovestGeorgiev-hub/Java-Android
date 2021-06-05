package com.example.tasksapp.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        android.app.DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this.getApplicationContext(), InactiveTasksActivity.class);
                intent.putExtra("fromCalendar", true);
                intent.putExtra("year", year);
                intent.putExtra("monthOfYear", monthOfYear);
                intent.putExtra("dayOfMonth", dayOfMonth);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CalendarActivity.this.getApplicationContext().startActivity(intent);

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,  R.style.CalendarTheme, onDateSetListener, mYear, mMonth, mDay);

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    CalendarActivity.super.onBackPressed();
                }
            }
        });

        datePickerDialog.show();
    }
}
