package com.example.tasksapp.async.tasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.tasksapp.data.AppDatabase;
import com.example.tasksapp.data.Task;
import com.example.tasksapp.data.TaskDao;

import java.util.List;

public class GetAllAgentAsyncTask extends AsyncTask<Void, Void, List<Task>> {

    private Context appContext;

    public GetAllAgentAsyncTask(Context applicationContext) {
        this.appContext = applicationContext;
    }

    @Override
    protected List<Task> doInBackground(Void... params) {

        AppDatabase db = Room.databaseBuilder(this.appContext, AppDatabase.class, "database-name").build();
        TaskDao taskDao = db.taskDao();
        return taskDao.getAll();

    }

    @Override
    protected void onPostExecute(List<Task> params) {

    }

}
