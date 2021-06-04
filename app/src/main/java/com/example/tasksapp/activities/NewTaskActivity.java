package com.example.tasksapp.activities;

import android.content.Intent;
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
                Misc.fillTask(info, endDate, endTime, isCompleted, task);

                HOTaskDao.InsertTask(NewTaskActivity.this.getApplicationContext(), task);

                Intent intent = new Intent(NewTaskActivity.this.getApplicationContext(), ListTasksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                NewTaskActivity.this.getApplicationContext().startActivity(intent);
            }
        });


        EditText edittext= (EditText) findViewById(R.id.newTaskValueDate);






    }




    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}