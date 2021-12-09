package com.example.easydo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;


public class AddNewTaskFragment extends Fragment {
    private EditText editTextTitle,
            editTextDate,
            editTextLocation,
            editTextDescription,
            editTextTime;
    private DatePickerDialog datePickerDialog;
    private Button buttonSave, buttonCancel;
    private Task taskData = new Task.TaskBuilder("").createTask();
    private boolean editMode = false;
    FloatingActionButton addNewTask;
    public AddNewTaskFragment() {}
    public AddNewTaskFragment(Task newTask) {
        taskData = newTask;
        editMode = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.app_bar_main, container, false);
        addNewTask = mainView.findViewById(R.id.fab);

        View view = inflater.inflate(R.layout.fragment_addtask, container,false);
        editTextTitle = view.findViewById(R.id.edit_task_title);
        editTextDate = view.findViewById(R.id.edit_task_date);
        editTextTime = view.findViewById(R.id.edit_task_time);
        editTextLocation =view.findViewById(R.id.edit_task_location);
        editTextDescription = view.findViewById(R.id.edit_task_description);
        buttonSave = view.findViewById(R.id.button_save);
        buttonCancel = view.findViewById(R.id.button_cancel);


        editTextTitle.setText(taskData.getTitle());
        editTextDate.setText(taskData.getDeadline("dd.MM.yyyy"));
        editTextLocation.setText(taskData.getLocation());
        editTextDescription.setText(taskData.getDescription());

        int[] prio = getResources().getIntArray(R.array.priority);

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
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                Context context2 = getContext();

                int hours = calendar.get((Calendar.HOUR_OF_DAY));
                int minutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(context2, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextTime.setText(hourOfDay + ":" + minute);
                    }
                },hours,minutes,true);
                timePickerDialog.show();
            }

        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.getTaskManager().addTask(createTask(), true);
                    destroyFragment();
                    addNewTask.setEnabled(true);
                    addNewTask.setVisibility(View.VISIBLE);

                }catch (Exception e)
                {
                    CharSequence text = e.getMessage();
                    int duration = Toast.LENGTH_LONG;
                    Context context = getContext();

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextTitle.setText("");
                editTextDate.setText("");
                editTextLocation.setText("");
                editTextDescription.setText("");
                if(editMode)
                    MainActivity.getTaskManager().addTask(taskData, true);
                destroyFragment();
                addNewTask.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void destroyFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(MainActivity.getTaskManager().getTodoList())).commit();
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
