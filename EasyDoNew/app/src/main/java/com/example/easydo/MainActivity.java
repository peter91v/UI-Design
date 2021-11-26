package com.example.easydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.easydo.dao.Task;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /***/
    private final TaskManager taskManager = new TaskManager();

    /**AddFragment Objects*/
    private Button saveButton = findViewById(R.id.button_save);
    private Button cancleButton = findViewById(R.id.button_cancel);
    private TextView taskTitleText = findViewById(R.id.edit_task_title);
    private TextView taskDeadlineText = findViewById(R.id.edit_task_date);
    private TextView taskLocationText = findViewById(R.id.edit_task_location);
    private TextView taskDescriptionText = findViewById(R.id.edit_task_description);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //placeholder Tasks
        try {
            Task t1 = new Task.TaskBuilder("Johannes mal schustern").withDeadline("26.11.2021", "dd.MM.yyyy").createTask();
            Task t2 = new Task.TaskBuilder("Mit Peter vargan").withDeadline("31.12.2021", "dd.MM.yyyy").createTask();
            Task t3 = new Task.TaskBuilder("Flo richtig hinterbergern").withDeadline("19.01.2022", "dd.MM.yyyy").createTask();

            taskManager.addTask(t1);
            taskManager.addTask(t2);
            taskManager.addTask(t3);
            taskManager.addTask(t1);
            taskManager.addTask(t2);
            taskManager.addTask(t3);
            taskManager.addTask(t1);
            taskManager.addTask(t2);
            taskManager.addTask(t3);
            taskManager.addTask(t1);
            taskManager.addTask(t2);
            taskManager.addTask(t3);
            taskManager.addTask(t1);
            taskManager.addTask(t2);
            taskManager.addTask(t3);
        }
        catch (ParseException pe) {
            Log.d(TAG, "Date Parse Exception");
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "Initializing the RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(this, taskManager.getTodoList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Task createTask(){
        try {
            return new Task.TaskBuilder(taskTitleText.getText().toString())
                    .withDeadline(taskDeadlineText.getText().toString(), "dd/MM/yyyy")
                    .withLocation(taskLocationText.getText().toString())
                    .withDescription(taskDescriptionText.getText().toString())
                    .createTask();
        }
        catch(ParseException pe) {
            Log.d(TAG, "createTask: Parse Exception");
        }

       return null;
    }

}