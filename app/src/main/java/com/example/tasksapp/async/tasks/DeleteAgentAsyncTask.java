package com.example.tasksapp.async.tasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.tasksapp.data.AppDatabase;
import com.example.tasksapp.data.Task;
import com.example.tasksapp.data.TaskDao;

public class DeleteAgentAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context appContext;
    private Task task;

    public DeleteAgentAsyncTask(Task task, Context applicationContext) {
        this.task = task;
        this.appContext = applicationContext;
    }

    @Override
    protected Void doInBackground(Void... params) {
        AppDatabase db = Room.databaseBuilder(this.appContext, AppDatabase.class, "database-name").build();
        TaskDao taskDao = db.taskDao();
        taskDao.delete(task);
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {

    }
}