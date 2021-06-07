package com.example.tasksapp.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

public class ListTasksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        Intent intent = getIntent();
        boolean fromNotificationButton = intent.getBooleanExtra("fromNotificationButton", false);

        List<Task> tasks = new ArrayList<Task>();
        if (fromNotificationButton) {
            int year = intent.getIntExtra("year", 1900);
            int month = intent.getIntExtra("monthOfYear", 1);
            int day = intent.getIntExtra("dayOfMonth", 1);

            List<Task> allTasks = HOTaskDao.GetAllTasks(ListTasksActivity.this.getApplicationContext());

            tasks.addAll(allTasks
                    .stream()
                    .filter(t -> {
                        Date toToLocal = Misc.gmttoLocalDate(t.dateCompleted);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(toToLocal);
                        boolean yearComp = cal.get(Calendar.YEAR) == year;
                        boolean monthComp = (cal.get(Calendar.MONTH) + 1) == month;
                        boolean dayComp = cal.get(Calendar.DAY_OF_MONTH) == day;
                        return !t.isCompleted && yearComp && monthComp && dayComp;
                    })
                    .collect(Collectors.toList()));
        } else {
            tasks.addAll(HOTaskDao.GetAllTasks(ListTasksActivity.this.getApplicationContext()).stream().filter(t -> !t.isCompleted).collect(Collectors.toList()));
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.listTasksRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        ListTasksAdapter listAdapter = new ListTasksAdapter(tasks, this.getApplicationContext(), false, this);
        recycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        createNotificationChannel();
        Intent intentNotification = new Intent(this, ListTasksActivity.class);
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotification, 0);

        Calendar next = Calendar.getInstance();
        next.setTime(new Date());
        next.add(Calendar.DAY_OF_MONTH, 1);
        int mYear = next.get(Calendar.YEAR);
        int mMonth = next.get(Calendar.MONTH) + 1;
        int mDay = next.get(Calendar.DAY_OF_MONTH);
        List<Task> allTasks = HOTaskDao.GetAllTasks(this);
        List<Task> n = allTasks.stream().filter(t -> {
            Date toToLocal = Misc.gmttoLocalDate(t.dateCompleted);
            Calendar cal = Calendar.getInstance();
            cal.setTime(toToLocal);
            boolean yearComp = cal.get(Calendar.YEAR) == mYear;
            boolean monthComp = (cal.get(Calendar.MONTH) + 1 ) == mMonth;
            boolean dayComp = cal.get(Calendar.DAY_OF_MONTH) == mDay;
            return !t.isCompleted && yearComp && monthComp && dayComp;
        }).collect(Collectors.toList());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "task_app_chanel_01")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Tasks application")
                .setContentText("You have " + n.size() + " tasks tomorrow ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, builder.build());
    }

    // TODO : one notification per day
    //        notifications id must be unique
    //        refactor the smelly code
    //        commit and finish 

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("task_app_chanel_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}