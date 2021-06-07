package com.example.tasksapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.tasksapp.Misc;
import com.example.tasksapp.R;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EditTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        long time = intent.getLongExtra("time", 0);
        boolean isCompl = intent.getBooleanExtra("isCompleted", false);
        int id = intent.getIntExtra("id", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date date = Misc.gmttoLocalDate(calendar.getTime());

        Calendar next = Calendar.getInstance();
        next.setTime(date);
        int mYear = next.get(Calendar.YEAR);
        int mMonth = next.get(Calendar.MONTH) + 1;
        int mDay = next.get(Calendar.DAY_OF_MONTH);
        int mHour = next.get(Calendar.HOUR_OF_DAY);
        int mMins = next.get(Calendar.MINUTE);

        String dateToShow = (mDay < 10 ? "0" + mDay : mDay) + "/" + (mMonth < 10 ? "0" + mMonth : mMonth) + "/" + mYear;
        String timeToShow = (mHour < 10 ? "0" + mHour : mHour) + ":" + (mMins < 10 ? "0" + mMins : mMins);

        EditText editTaskValueInfo = (EditText) findViewById(R.id.editTaskValueInfo);
        editTaskValueInfo.setText(info);

        EditText editTaskValueDate = (EditText) findViewById(R.id.editTaskValueDate);
        editTaskValueDate.setText(dateToShow);

        EditText editTaskValueTime = (EditText) findViewById(R.id.editTaskValueTime);
        editTaskValueTime.setText(timeToShow);

        Switch isCompleted = (Switch) findViewById(R.id.editTaskValueIsCompleted);
        isCompleted.setChecked(isCompl);

        Button button = (Button) findViewById(R.id.editTaskSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editTaskValueInfo = ((EditText) findViewById(R.id.editTaskValueInfo)).getText();
                ;
                Editable editTaskValueDate = ((EditText) findViewById(R.id.editTaskValueDate)).getText();
                ;
                Editable editTaskValueTime = ((EditText) findViewById(R.id.editTaskValueTime)).getText();
                ;
                boolean isCompleted = ((Switch) findViewById(R.id.editTaskValueIsCompleted)).isChecked();
                Task task = new Task();
                task.uid = id;
                Misc.fillTask(editTaskValueInfo, editTaskValueDate, editTaskValueTime, isCompleted, task);
                HOTaskDao.UpdateTask(EditTaskActivity.this.getApplicationContext(), task);

                Intent intent = new Intent(EditTaskActivity.this.getApplicationContext(), ListTasksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                EditTaskActivity.this.getApplicationContext().startActivity(intent);
            }
        });

        Button cancel = (Button) findViewById(R.id.editTaskCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskActivity.super.onBackPressed();
            }
        });

        Button delete = (Button) findViewById(R.id.editTaskDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Task> tasks = HOTaskDao.GetAllTasks(EditTaskActivity.this);
                List<Task> filter = tasks.stream().filter(task -> task.uid == id).collect(Collectors.toList());
                HOTaskDao.DeleteTask(EditTaskActivity.this, filter.get(0));
                Intent intent = new Intent(EditTaskActivity.this.getApplicationContext(), ListTasksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                EditTaskActivity.this.getApplicationContext().startActivity(intent);
            }
        });


    }
}