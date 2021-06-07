package com.example.tasksapp.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasksapp.Misc;
import com.example.tasksapp.R;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Date dt = new Date();
        Calendar next = Calendar.getInstance();
        next.setTime(dt);
        int mYear = next.get(Calendar.YEAR);
        int mMonth = next.get(Calendar.MONTH) + 1;
        int mDay = next.get(Calendar.DAY_OF_MONTH);
        List<Task> allTasks = HOTaskDao.GetAllTasks(this);
        List<Task> n = allTasks.stream().filter(t -> {
            Date toToLocal = Misc.gmttoLocalDate(t.dateCompleted);
            Calendar cal = Calendar.getInstance();
            cal.setTime(toToLocal);
            boolean yearComp = cal.get(Calendar.YEAR) == mYear;
            boolean monthComp = (cal.get(Calendar.MONTH) + 1 ) == mMonth;
            boolean dayComp = cal.get(Calendar.DAY_OF_MONTH) == mDay;
            return !t.isCompleted && yearComp && monthComp && dayComp;
        }).collect(Collectors.toList());
        MenuItem item = menu.add ("N:" + n.size());
        item.setShowAsAction(1);
        item.setOnMenuItemClickListener (new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem item){
                Intent intent = new Intent(BaseActivity.this.getApplicationContext(), ListTasksActivity.class);
                intent.putExtra("fromNotificationButton", true);
                intent.putExtra("year", mYear);
                intent.putExtra("monthOfYear", mMonth);
                intent.putExtra("dayOfMonth", mDay);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseActivity.this.getApplicationContext().startActivity(intent);
                return true;
            }
        });

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create_new_task) {

            Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_view_active_tasks) {

            Intent intent = new Intent(getApplicationContext(), ListTasksActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_view_completed_tasks) {

            Intent intent = new Intent(getApplicationContext(), InactiveTasksActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_view_calendar) {

            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
