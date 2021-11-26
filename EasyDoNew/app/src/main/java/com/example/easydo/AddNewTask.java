package com.example.easydo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easydo.dao.Task;

import java.util.Calendar;


public class AddNewTask extends Fragment {
    private EditText editTextTitle,
            editTextDate,
            editTextLocation,
            editTextDescription;
    private DatePickerDialog datePickerDialog;
    private Button buttonSave, buttonCancel;
    private TaskManager taskManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        taskManager = new TaskManager();

        View view = inflater.inflate(R.layout.fragment_addtask, container,false);
        editTextTitle = view.findViewById(R.id.edit_task_title);
        editTextDate = view.findViewById(R.id.edit_task_date);
        editTextLocation =view.findViewById(R.id.edit_task_location);
        editTextDescription = view.findViewById(R.id.edit_task_description);
        buttonSave = view.findViewById(R.id.button_save);
        buttonCancel = view.findViewById(R.id.button_cancel);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mounth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                Context context1 = getContext();
                datePickerDialog = new DatePickerDialog(context1, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mounth, int day) {
                        editTextDate.setText(day + "/" + (mounth + 1) + "/" + year);
                    }
                }, year, mounth, day);
                datePickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* Task x = new Task.TaskBuilder(editTextTitle.getText().toString()).withDeadline(editTextDate.getText())
                    .withLocation(editTextLocation.getText()).withDescription(editTextDescription.getText()).createTask();*/
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
