package com.example.tasksapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasksapp.R;
import com.example.tasksapp.activities.EditTaskActivity;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksAdapter.ViewHolder> {
    private List<Task> _tasks;
    private Context _context;
    private boolean _inactiveTasksFlag;
    private AppCompatActivity _aca;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Switch _switch;
        private final TextView textView;

        private final TextView textViewTime;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            _switch = (Switch) view.findViewById(R.id.listItemSwitch);
            textView = (TextView) view.findViewById(R.id.listItemInfo);
            textViewTime = (TextView) view.findViewById(R.id.listItemTime);
        }

        public TextView getTextView() {
            return textView;
        }

        public Switch getSwitch() {
            return _switch;
        }

        public TextView getTextViewTime() {
            return textViewTime;
        }
    }

    public ListTasksAdapter(List<Task> tasks, Context context, boolean inactiveTasksFlag, AppCompatActivity acp) {
        _tasks = tasks;
        _context = context;
        _inactiveTasksFlag = inactiveTasksFlag;
        _aca = acp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String infoString = _tasks.get(position).info;
        String infoSubString;
        if (infoString.length() > 20) {
            infoSubString = infoString.substring(0, 20);
            infoSubString = infoSubString + "...";
        } else {
            infoSubString = infoString;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        _aca.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        if (width<500&&infoString.length() > 12){
            infoSubString = infoString.substring(0, 12);
            infoSubString = infoSubString + "...";
        }
        holder.getTextView().setText(infoSubString);
        holder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, EditTaskActivity.class);
                intent.putExtra("id", _tasks.get(position).uid);
                intent.putExtra("info", _tasks.get(position).info);
                intent.putExtra("time", _tasks.get(position).dateCompleted.getTime());
                intent.putExtra("isCompleted", _tasks.get(position).isCompleted);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(intent);
            }
        });
        Switch aSwitch = holder.getSwitch();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (_inactiveTasksFlag != isChecked) {
                    Task task = _tasks.get(position);
                    task.isCompleted = !task.isCompleted;
                    HOTaskDao.UpdateTask(_context, task);
                    remove(position);
                    _aca.invalidateOptionsMenu();

                }
            }
        });
        aSwitch.setChecked(_tasks.get(position).isCompleted);
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(_tasks.get(position).dateCompleted);
        holder.getTextViewTime().setText(todayAsString);
    }

    @Override
    public int getItemCount() {
        return _tasks.size();
    }

    public void remove(int position) {
        _tasks.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
