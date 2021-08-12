package com.example.my_todo_application.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_todo_application.DB.DatabaseHandler;
import com.example.my_todo_application.MainActivity;
import com.example.my_todo_application.R;
import com.example.my_todo_application.ToDoModel.ToDoClass;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoClass> taskList;
    private MainActivity activity;
    private DatabaseHandler dbh;

    public ToDoAdapter(DatabaseHandler dbh, MainActivity activity) {
        this.dbh = dbh;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dbh.openDatabase();
        final ToDoClass tasks = taskList.get(position);
        holder.task.setText(tasks.getTask());
        holder.task.setChecked(toBoolean(tasks.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dbh.updateStatus(tasks.getId(), 1);
                }
                else {
                    dbh.updateStatus(tasks.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int i) {
        return i != 0;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTasks(List<ToDoClass> todoList) {
        this.taskList = todoList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.TodoCheckBox);
        }
    }
}
