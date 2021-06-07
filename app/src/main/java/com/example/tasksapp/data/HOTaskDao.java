package com.example.tasksapp.data;

import android.content.Context;

import com.example.tasksapp.async.tasks.DeleteAgentAsyncTask;
import com.example.tasksapp.async.tasks.GetAllAgentAsyncTask;
import com.example.tasksapp.async.tasks.GetByIdTaskAgentAsyncTask;
import com.example.tasksapp.async.tasks.InsertTaskAgentAsyncTask;
import com.example.tasksapp.async.tasks.UpdateTaskAgentAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HOTaskDao {

    public static List<Task> GetAllTasks(Context context){
        try {
            return new GetAllAgentAsyncTask(context).execute().get();
        } catch (ExecutionException e) {
            // log the exception
        } catch (InterruptedException e) {
            // log the exception
        }
        return new ArrayList<Task>();
    }

    public static void InsertTask(Context context, Task task){
        new InsertTaskAgentAsyncTask(task, context).execute();
    }

    public static void UpdateTask(Context context, Task task){
        new UpdateTaskAgentAsyncTask(task, context).execute();
    }

    public static void DeleteTask(Context context, Task task){
        new DeleteAgentAsyncTask(task, context).execute();
    }


}
