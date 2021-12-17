package com.example.easydo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class AddNewTaskFragment extends Fragment {
    private EditText editTextTitle, editTextDate, editTextLocation, editTextDescription, editTextTime;
    private DatePickerDialog datePickerDialog;
    private Button buttonSave, buttonCancel;
    private Task taskData = new Task.TaskBuilder("").createTask();
    private Spinner editDropDownPriority;
    private FloatingActionButton addNewTask;

    private boolean editMode = false;
    private Calendar calendar;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int hours = 0;
    private int minutes = 0;



    public AddNewTaskFragment() {
    }

    public AddNewTaskFragment(Task newTask) {
        taskData = newTask;
        editMode = true;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.app_bar_main, container, false);
        View addtaskView = inflater.inflate(R.layout.fragment_addtask, container, false);

        addNewTask = mainView.findViewById(R.id.fab);
        editTextTitle = addtaskView.findViewById(R.id.edit_task_title);
        editTextDate = addtaskView.findViewById(R.id.edit_task_date);
        editTextTime = addtaskView.findViewById(R.id.edit_task_time);
        editTextLocation = addtaskView.findViewById(R.id.edit_task_location);
        editTextDescription = addtaskView.findViewById(R.id.edit_task_description);
        buttonSave = addtaskView.findViewById(R.id.button_save);
        buttonCancel = addtaskView.findViewById(R.id.button_cancel);
        editDropDownPriority = addtaskView.findViewById(R.id.edit_task_Priority);
        editTextTitle.setText(taskData.getTitle());
        editTextDate.setText(taskData.getDeadline("dd.MM.yyyy"));
        editTextTime.setText(taskData.getDeadline("HH:mm"));
        editTextLocation.setText(taskData.getLocation());
        editTextDescription.setText(taskData.getDescription());

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(), R.array.priorities, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDropDownPriority.setAdapter(priorityAdapter);

        editDropDownPriority.setSelection(taskData.getPriority(), false);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int pickerDay = calendar.get(calendar.DAY_OF_MONTH);
                int pickerMonth = calendar.get(Calendar.MONTH);
                int pickerYear = calendar.get(Calendar.YEAR);
                Context context = getContext();
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int pickerYear, int pickerMonth, int pickerDay) {
                        editTextDate.setText(pickerDay + "." + (pickerMonth + 1) + "." + pickerYear);
                        day = pickerDay;
                        month = pickerMonth;
                        year = pickerYear;
                    }
                }, pickerYear, pickerMonth, pickerDay);
                datePickerDialog.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                Context context = getContext();

                int pickerHours = calendar.get((Calendar.HOUR_OF_DAY));
                int pickerMinutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int pickerHours, int pickerMinutes) {
                        editTextTime.setText(pickerHours + ":" + pickerMinutes);
                        minutes = pickerMinutes;
                        hours = pickerHours;
                    }
                }, pickerHours, pickerMinutes, true);
                timePickerDialog.show();

            }

        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Task newTask = createTask();
                    MainActivity.getTaskManager().addTask(newTask, true);

                    if (!(year == 0 && month == 0 && day == 0) || !(hours == 0 && minutes == 0)) {
                        calendar.set(year, month, day, hours, minutes, 0);
                        long millis = calendar.getTimeInMillis();

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle)
                                .setTitle(getResources().getString(R.string.alarmdialog_title))
                                .setMessage(getResources().getString(R.string.alarmdialog_message))
                                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((MainActivity) getActivity()).createCalenderAppEntry(editTextTitle, editTextLocation, editTextDescription, millis);
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                   public void onClick(DialogInterface dialog, int which) {}
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        ((MainActivity) getActivity()).setAlarm(newTask.getId(), editTextTitle.getText().toString(), millis);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
                    }

                    destroyFragment();

                } catch (Exception e) {
                    CharSequence errorText = e.getMessage();
                    int duration = Toast.LENGTH_LONG;
                    Context context = getContext();
                    Toast toast = Toast.makeText(context, errorText, duration);

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
                if (editMode)
                    MainActivity.getTaskManager().addTask(taskData, true);
                destroyFragment();
            }
        });
        return addtaskView;
    }

    private void destroyFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();

        addNewTask.setVisibility(View.VISIBLE);
        addNewTask.setEnabled(true);
        fragmentManager.beginTransaction().remove(this).commit();
        fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(MainActivity.getTaskManager().getTodoList())).commit();


    }

    private Task createTask() throws RuntimeException {
        String title = editTextTitle.getText().toString();
        String deadline = editTextDate.getText().toString();
        String deadlineTime = editTextTime.getText().toString();
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();
        short priority;

        switch (editDropDownPriority.getSelectedItemPosition()) {
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

        if (!title.trim().isEmpty()) {
            newTask = new Task.TaskBuilder(title).createTask();
            newTask.setId(CounterHelper.getInstance().getId());
        } else
            throw new RuntimeException(getResources().getString(R.string.no_title));
        if (!deadline.isEmpty() || !deadlineTime.isEmpty()) {
            if (deadline.isEmpty())
                newTask.setDeadline(deadlineTime, "HH:mm");
            else if (deadlineTime.isEmpty())
                newTask.setDeadline(deadline, "dd.MM.yyyy");
            else
                newTask.setDeadline(deadline + deadlineTime, "dd.MM.yyyyHH:mm");
        }
        if (!location.isEmpty())
            newTask.setLocation(location);
        if (!description.isEmpty())
            newTask.setDescription(description);

        newTask.setPriority(priority);
        return newTask;
    }

}
