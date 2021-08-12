package com.example.my_todo_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.my_todo_application.Adapter.ToDoAdapter;
import com.example.my_todo_application.DB.DatabaseHandler;
import com.example.my_todo_application.ToDoModel.ToDoClass;
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

        toDoAdapter.setTasks(todoList);
        
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "fab is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}