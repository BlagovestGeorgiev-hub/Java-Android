package com.example.tasksapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "date_created")
    public Date dateCreated;

    @ColumnInfo(name = "date_completed")
    public Date dateCompleted;

    @ColumnInfo(name = "is_completed")
    public boolean isCompleted;

    @ColumnInfo(name = "is_notified_day_before_end_date")
    public boolean isNotifiedDayBeforeEndDate;
}
