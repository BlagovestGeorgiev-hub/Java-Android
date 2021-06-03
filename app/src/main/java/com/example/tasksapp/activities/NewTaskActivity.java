package com.example.tasksapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
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
import java.util.TimeZone;

public class NewTaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Button button = (Button) findViewById(R.id.newTaskSubmit);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editable info = ((EditText)findViewById(R.id.newTaskValueInfo)).getText();
                Editable endDate =((EditText)findViewById(R.id.newTaskValueDate)).getText();
                Editable endTime =((EditText)findViewById(R.id.newTaskValueTime)).getText();
                boolean isCompleted = ((Switch)findViewById(R.id.newTaskValueIsCompleted)).isChecked();
                Task task = new Task();
                task.info = info.toString();
                task.dateCreated = Misc.localToGMT(new Date());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.set(Calendar.DATE, Integer.parseInt(endDate.toString().split("/")[0]));
                calendar.set(Calendar.MONTH, Integer.parseInt(endDate.toString().split("/")[1]) - 1);
                calendar.set(Calendar.YEAR, Integer.parseInt(endDate.toString().split("/")[2]));

                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTime.toString().split(":")[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(endTime.toString().split(":")[1]));


                task.dateCompleted = Misc.localToGMT(calendar.getTime());
                task.isCompleted = isCompleted;

                HOTaskDao.InsertTask(NewTaskActivity.this.getApplicationContext(), task);
            }
        });
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}