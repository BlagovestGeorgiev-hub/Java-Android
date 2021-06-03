package com.example.tasksapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tasksapp.R;

public class ListTasksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_new_task)
        {
            Intent intent=new Intent(getApplicationContext(), NewTaskActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}