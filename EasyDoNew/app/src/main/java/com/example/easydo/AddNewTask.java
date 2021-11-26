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
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.Task;

import java.util.Calendar;


public class AddNewTask extends Fragment {
    private EditText editTextTitle,
            editTextDate,
            editTextLocation,
            editTextDescription;
    private DatePickerDialog datePickerDialog;
    private Button buttonSave, buttonCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                Context context1 = getContext();
                datePickerDialog = new DatePickerDialog(context1, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        editTextDate.setText(day + "." + (month + 1) + "." + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getTaskManager().addTask(createTask());
                destroyFragment();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTitle.setText("");
                editTextDate.setText("");
                editTextLocation.setText("");
                editTextDescription.setText("");
                destroyFragment();
            }
        });
        return view;
    }

    private void destroyFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, new TaskRecycler()).commit();
        fragmentManager.beginTransaction().remove(this).commit();
    }

    private Task createTask() throws RuntimeException{
        String title = editTextTitle.getText().toString();
        String deadline = editTextDate.getText().toString();
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();
        Task newTask;

        if (!title.isEmpty())
            newTask = new Task.TaskBuilder(title).createTask();
        else
            throw new RuntimeException(getResources().getString(R.string.no_title));
        if(!deadline.isEmpty())
            newTask.setDeadline(deadline, "dd.MM.yyyy");
        if(!location.isEmpty())
            newTask.setLocation(location);
        if(!description.isEmpty())
            newTask.setDescription(description);

        return newTask;
    }
}
