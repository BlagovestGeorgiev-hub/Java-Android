package com.example.tasksapp.async.tasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.tasksapp.data.AppDatabase;
import com.example.tasksapp.data.Task;
import com.example.tasksapp.data.TaskDao;

import java.util.Arrays;
import java.util.List;

public class GetByIdTaskAgentAsyncTask extends AsyncTask<Integer, Void, List<Task>> {

    private Context appContext;

    public GetByIdTaskAgentAsyncTask(Context applicationContext) {
        this.appContext = applicationContext;
    }

    @Override
    protected List<Task> doInBackground(Integer... params) {

        AppDatabase db = Room.databaseBuilder(this.appContext, AppDatabase.class, "database-name").build();
        TaskDao taskDao = db.taskDao();
        return taskDao.loadAllByIds(Arrays.stream(params).mapToInt(Integer::intValue).toArray());

    }

    @Override
    protected void onPostExecute(List<Task> params) {

    }

}