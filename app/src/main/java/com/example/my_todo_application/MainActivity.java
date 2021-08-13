package com.example.my_todo_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.my_todo_application.Adapter.ToDoAdapter;
import com.example.my_todo_application.DB.DatabaseHandler;
import com.example.my_todo_application.ToDoModel.ToDoClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FloatingActionButton fab;
    private ToDoAdapter toDoAdapter;
    private List<ToDoClass> todoList;

    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dbh = new DatabaseHandler(this);
        dbh.openDatabase();

        rv = findViewById(R.id.Rv);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        toDoAdapter = new ToDoAdapter(dbh, MainActivity.this);
        rv.setAdapter(toDoAdapter);

        todoList = dbh.getAllTasks();

        ToDoClass tasks = new ToDoClass();
        tasks.setId(1);
        tasks.setTask("This is Task");
        tasks.setStatus(0);

        todoList.add(tasks);

        toDoAdapter.setTasks(todoList);
        
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "fab is clicked", Toast.LENGTH_SHORT).show();
                showAddNewTask();
            }
        });
    }

    private void showAddNewTask() {
        BottomSheetDialog bsd = new BottomSheetDialog(this);
        bsd.setContentView(R.layout.new_task);

        EditText newTaskText = bsd.findViewById(R.id.addTaskText);
        Button saveBtn = bsd.findViewById(R.id.SaveBtn);

        boolean isUpdate = false;

        bsd.show();
    }

    //For PopUpMenu
    private void popup(View v) {
        PopupMenu pm = new PopupMenu(MainActivity.this, v);
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Edit:
                        Toast.makeText(MainActivity.this, "Editting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Delete:
                        Toast.makeText(MainActivity.this, "Deleting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_list, pm.getMenu());
        pm.show();
    }

}