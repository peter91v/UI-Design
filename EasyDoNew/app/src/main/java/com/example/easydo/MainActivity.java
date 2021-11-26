package com.example.easydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easydo.dao.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    /***/
    private static final TaskManager taskManager = new TaskManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //placeholder Tasks
        Task t1 = new Task.TaskBuilder("Johannes mal schustern").withDeadline("26.11.2021", "dd.MM.yyyy").createTask();
        Task t2 = new Task.TaskBuilder("Mit Peter vargan").withDeadline("31.12.2021", "dd.MM.yyyy").createTask();
        Task t3 = new Task.TaskBuilder("Flo richtig hinterbergern").withDeadline("19.01.2022", "dd.MM.yyyy").createTask();

        taskManager.addTask(t1);
        taskManager.addTask(t2);
        taskManager.addTask(t3);

        initRecyclerView();
        FloatingActionButton addNewTask = findViewById(R.id.fab);
        fragmentManager = getSupportFragmentManager();
        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new AddNewTask()).commit();
            }
        });
    }

    private void initRecyclerView(){
        Log.d(TAG, "Initializing the RecyclerView");
        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(this, taskManager.getTodoList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}