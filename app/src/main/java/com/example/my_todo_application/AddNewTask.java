package com.example.my_todo_application;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.my_todo_application.DB.DatabaseHandler;
import com.example.my_todo_application.ToDoModel.ToDoClass;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private TextView newTaskText;
    private Button saveBtn;

    private DatabaseHandler dbh;

    public static AddNewTask abc() {
        return new AddNewTask();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_task, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.addTaskText);
        saveBtn = getView().findViewById(R.id.SaveBtn);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            assert  task != null;
            if(task.length() > 0)
                saveBtn.setTextColor(Color.BLUE);
        }
        dbh = new DatabaseHandler(getActivity());
        dbh.openDatabase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalisUpdate = isUpdate;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                if(finalisUpdate) {
                    dbh.updateTask(bundle.getInt("id"), text);
                }
                else {
                    ToDoClass item = new ToDoClass();
                    item.setTask(text);
                    item.setStatus(0);
                    dbh.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss( DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
