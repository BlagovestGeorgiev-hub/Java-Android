package com.example.tasksapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface  TaskDao {

    @Query("SELECT * FROM task")
    public List<Task> getAll();

    @Query("SELECT * FROM task WHERE uid IN (:taskIds)")
    public List<Task> loadAllByIds(int[] taskIds);

    @Insert
    public void insertAll(Task... tasks);

    @Update
    public void updateTask(Task task);

    @Delete
    public void delete(Task task);
}
