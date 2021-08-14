package com.example.my_todo_application.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_todo_application.AddNewTask;
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

    public Context getContext() {
        return activity;
    }

    public void delete(int position) {
        ToDoClass item = taskList.get(position);
        dbh.deleteTask(item.getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void edit(int position) {
        ToDoClass item = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        CheckBox task;
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.TodoCheckBox);
            cardView = view.findViewById(R.id.CardView);
            cardView.setOnLongClickListener(this);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "Press Long", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean onLongClick(View v) {
            showpopupmenu(v);
            return false;
        }

        private void showpopupmenu(View v) {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.inflate(R.menu.menu_list);
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Edit:
                            edit(getAdapterPosition());
                            return true;

                        case R.id.Delete:
                            delete(getAdapterPosition());
                    }
                    return false;
                }
            });
            menu.show();
        }
    }
}
