package com.example.my_todo_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private TextView newTaskText;
    private Button saveBtn;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.from(container.getContext()).inflate(R.layout.new_task, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.addTaskText);
        saveBtn = getView().findViewById(R.id.SaveBtn);

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if(bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            if(task.length() < 0) {
                saveBtn
            }
        }
    }
}
