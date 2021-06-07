package com.example.tasksapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasksapp.Misc;
import com.example.tasksapp.R;
import com.example.tasksapp.adapters.ListTasksAdapter;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InactiveTasksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive_tasks);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.listInactiveTasksRecyclerView);

        Intent intent = getIntent();
        boolean fromCalendar = intent.getBooleanExtra("fromCalendar", false);

        List<Task> tasks = new ArrayList<Task>();
        if (fromCalendar){
            int year = intent.getIntExtra("year", 1900);
            int month = intent.getIntExtra("monthOfYear", 1);
            int day = intent.getIntExtra("dayOfMonth", 1);

            List<Task> allTasks = HOTaskDao.GetAllTasks(InactiveTasksActivity.this.getApplicationContext());

            tasks.addAll(allTasks
                    .stream()
                    .filter(t -> {
                        Date toToLocal = Misc.gmttoLocalDate(t.dateCompleted);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(toToLocal);
                        boolean yearComp = cal.get(Calendar.YEAR) == year;
                        boolean monthComp = (cal.get(Calendar.MONTH) + 1 ) == month;
                        boolean dayComp = cal.get(Calendar.DAY_OF_MONTH) == day;
                        return t.isCompleted && yearComp && monthComp && dayComp;
                    })
                    .collect(Collectors.toList()));
        }else {
            tasks.addAll(HOTaskDao.GetAllTasks(InactiveTasksActivity.this.getApplicationContext()).stream().filter(t -> t.isCompleted).collect(Collectors.toList()));
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        ListTasksAdapter listAdapter = new ListTasksAdapter(tasks, this.getApplicationContext(), true, this);
        recycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}