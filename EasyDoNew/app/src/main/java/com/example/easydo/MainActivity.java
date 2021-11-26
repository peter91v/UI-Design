package com.example.easydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.easydo.dao.Task;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final TaskManager taskManager = new TaskManager();

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
}