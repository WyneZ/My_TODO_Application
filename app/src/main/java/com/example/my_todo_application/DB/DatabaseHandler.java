package com.example.my_todo_application.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.my_todo_application.ToDoModel.ToDoClass;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    private static final String TODO_TABLE = "TODO_TABLE";
    private static final String ID = "ID";
    private static final String TASK = "TASK";
    private static final String STATUS = "STATUS";

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, "ToDodata.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TODO_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //Create table again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoClass task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<ToDoClass> getAllTasks() {
        List<ToDoClass> returnList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cursor!=null) {
                if(cursor.moveToFirst()) {
                    //loop through the cursor(result set) and create new objects.Put them into the returnList
                    do{
                        ToDoClass item = new ToDoClass();
                        item.setId(cursor.getInt(0));
                        item.setTask(cursor.getString(1));
                        item.setStatus(cursor.getInt(2));
                        returnList.add(item);

                    }while(cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return returnList;
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, "ID=?", new String[] {String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, "ID=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, "ID=?", new String[] {String.valueOf(id)});
    }
}
