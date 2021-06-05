package com.example.tasksapp.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasksapp.R;
import com.example.tasksapp.adapters.ListTasksAdapter;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.List;
import java.util.stream.Collectors;

public class InactiveTasksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inactive_tasks);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.listInactiveTasksRecyclerView);
        List<Task> tasks = HOTaskDao.GetAllTasks(InactiveTasksActivity.this.getApplicationContext()).stream().filter(t -> t.isCompleted).collect(Collectors.toList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        ListTasksAdapter listAdapter = new ListTasksAdapter(tasks, this.getApplicationContext(), true);
        recycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}