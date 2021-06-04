package com.example.tasksapp;

import android.annotation.SuppressLint;
import android.text.Editable;

import com.example.tasksapp.data.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Misc {

    public static Date localToGMT(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = new Date(sdf.format(date));
        return gmt;
    }

    public static Date gmttoLocalDate(Date date) {

        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
        return local;
    }

    public static void fillTask(Editable info, Editable endDate, Editable endTime, boolean isCompleted, Task task) {
        task.info = info.toString();
        task.dateCreated = Misc.localToGMT(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.DATE, Integer.parseInt(endDate.toString().split("/")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(endDate.toString().split("/")[1]) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(endDate.toString().split("/")[2]));

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTime.toString().split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(endTime.toString().split(":")[1]));


        task.dateCompleted = Misc.localToGMT(calendar.getTime());
        task.isCompleted = isCompleted;
    }
}
