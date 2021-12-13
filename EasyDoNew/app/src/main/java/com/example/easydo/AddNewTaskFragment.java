package com.example.easydo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.CounterHelper;
import com.example.easydo.dao.Task;
import com.example.easydo.databinding.AppBarMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


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
    private Spinner editDropDownPriority;
    private Calendar calendar;
    FloatingActionButton addNewTask;
    public AddNewTaskFragment() {}
    public AddNewTaskFragment(Task newTask) {
        taskData = newTask;
        editMode = true;
    }
    AppBarMainBinding mainActivityBidning;
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
        editDropDownPriority = view.findViewById(R.id.edit_task_Priority);
        //map

        editTextTitle.setText(taskData.getTitle());
        editTextDate.setText(taskData.getDeadline("dd.MM.yyyy"));
        editTextTime.setText(taskData.getDeadline("HH:mm"));
        editTextLocation.setText(taskData.getLocation());
        editTextDescription.setText(taskData.getDescription());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.programming_languages,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDropDownPriority.setAdapter(adapter);

        editDropDownPriority.setSelection(taskData.getPriority(), false);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
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
                calendar = Calendar.getInstance();
                Context context2 = getContext();

                int hours = calendar.get((Calendar.HOUR_OF_DAY));
                int minutes = calendar.get(Calendar.MINUTE);
                calendar.set(Calendar.HOUR_OF_DAY, hours);
                calendar.set(Calendar.MINUTE, minutes);
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
            //do not
            public void onClick(View v) {
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle)
                            .setTitle("Do you want to save this task to your Calendar?")

                            .setMessage(getResources().getString(R.string.message))
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.getTaskManager().addTask(createTask(), true);
                                    ((MainActivity)getActivity()).creatEvent(editTextTitle,editTextLocation,editTextDescription, calendar);
                                    if (editTextDate.equals("") || editTextDate == null)
                                        ((MainActivity)getActivity()).setAlarm();
                                    addNewTask.setEnabled(true);
                                    mainView.findViewById(R.id.fab).setVisibility(View.VISIBLE);
                                    destroyFragment();
                                    addNewTask.setEnabled(true);
                                    addNewTask.setVisibility(View.VISIBLE);

                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.no),  new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.getTaskManager().addTask(createTask(), true);
                                    if (!editTextDate.equals("") || editTextDate != null)
                                         ((MainActivity)getActivity()).setAlarm();

                                         destroyFragment();
                                    addNewTask.setEnabled(true);
                                    addNewTask.setVisibility(View.VISIBLE);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));

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
                editTextTime.setText("");
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
        String deadlineTime = editTextTime.getText().toString();
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();
        short priority;
        switch (editDropDownPriority.getSelectedItemPosition()){

            case 1:
                priority = 1;
                break;
            case 2:
                priority = 2;
                break;
            case 3:
                priority = 3;
                break;
            default:
                priority = 0;
        }

        Task newTask;

        if (!title.trim().isEmpty()){
            newTask = new Task.TaskBuilder(title).createTask();
            newTask.setId(CounterHelper.getInstance().getId());
        }
        else
            throw new RuntimeException(getResources().getString(R.string.no_title));
        if(!deadline.isEmpty() || !deadlineTime.isEmpty()){
            if(deadline.isEmpty())
                newTask.setDeadline(deadlineTime, "HH:mm");
            else if(deadlineTime.isEmpty())
                newTask.setDeadline(deadline, "dd.MM.yyyy");
            else
                newTask.setDeadline(deadline + deadlineTime, "dd.MM.yyyyHH:mm");
        }
        if(!location.isEmpty())
            newTask.setLocation(location);
        if(!description.isEmpty())
            newTask.setDescription(description);

        newTask.setPriority(priority);
        return newTask;
    }
}
