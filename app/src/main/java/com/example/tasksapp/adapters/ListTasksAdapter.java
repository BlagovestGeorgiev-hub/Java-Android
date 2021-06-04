package com.example.tasksapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasksapp.R;
import com.example.tasksapp.activities.EditTaskActivity;
import com.example.tasksapp.data.HOTaskDao;
import com.example.tasksapp.data.Task;

import java.util.List;

public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksAdapter.ViewHolder> {
    private List<Task> _tasks;
    private Context _context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Switch _switch;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            _switch = (Switch) view.findViewById(R.id.listItemSwitch);
            textView = (TextView) view.findViewById(R.id.listItemInfo);
        }

        public TextView getTextView() {
            return textView;
        }

        public Switch getSwitch() {
            return _switch;
        }
    }

    public ListTasksAdapter(List<Task> tasks, Context context) {
        _tasks = tasks;
        _context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(_context).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(_tasks.get(position).info);
        holder.getTextView().setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, EditTaskActivity.class);
                intent.putExtra("id", _tasks.get(position).uid);
                intent.putExtra("info", _tasks.get(position).info);
                intent.putExtra("time", _tasks.get(position).dateCompleted.getTime());
                intent.putExtra("isCompl", _tasks.get(position).isCompleted);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(intent);
            }
        });
        Switch aSwitch = holder.getSwitch();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Task task = _tasks.get(position);
                    task.isCompleted = true;
                    HOTaskDao.UpdateTask(_context, task);
                    remove(position);

                }
            }
        });
        aSwitch.setChecked(_tasks.get(position).isCompleted);
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
