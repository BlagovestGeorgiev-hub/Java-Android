package com.example.tasksapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasksapp.R;
import com.example.tasksapp.adapters.ListTasksAdapter;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.List;
import java.util.stream.Collectors;

public class ListTasksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.listTasksRecyclerView);
        List<Task> tasks = HOTaskDao.GetAllTasks(ListTasksActivity.this.getApplicationContext()).stream().filter(t -> !t.isCompleted).collect(Collectors.toList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        ListTasksAdapter listAdapter = new ListTasksAdapter(tasks, this.getApplicationContext());
        recycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

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