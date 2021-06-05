package com.example.tasksapp.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasksapp.R;

public abstract class BaseActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_new_task){

            Intent intent=new Intent(getApplicationContext(), NewTaskActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_view_active_tasks){

            Intent intent=new Intent(getApplicationContext(), ListTasksActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_view_completed_tasks){
            Intent intent=new Intent(getApplicationContext(), InactiveTasksActivity.class);
            startActivity(intent);
        }if (item.getItemId() == R.id.action_view_calendar){
            Intent intent=new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
