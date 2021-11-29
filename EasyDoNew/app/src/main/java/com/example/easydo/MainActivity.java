package com.example.easydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easydo.dao.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static UiModeManager uiModeManager;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private static Context contextMain;
    /***/
    private static final TaskManager taskManager = new TaskManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().hide();
            setContentView(R.layout.activity_main);
            uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
            TextView toolbar = findViewById(R.id.toolbartitle);
            contextMain = getApplicationContext();


            //placeholder Tasks
            Task t1 = new Task.TaskBuilder("Johannes mal schustern").withDeadline("26.11.2021", "dd.MM.yyyy").createTask();
            Task t2 = new Task.TaskBuilder("Mit Peter vargan").withDeadline("31.12.2021", "dd.MM.yyyy").createTask();
            Task t3 = new Task.TaskBuilder("Flo richtig hinterbergern").withDeadline("19.01.2022", "dd.MM.yyyy").createTask();

            taskManager.addTask(t1, true);
            taskManager.addTask(t2, true);
            taskManager.addTask(t3, true);


            fragmentManager = getSupportFragmentManager();
            //fill the fragment with the TaskRecycler
            fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, new TaskRecycler(taskManager.getTodoList())).commit();

            FloatingActionButton addNewTask = findViewById(R.id.fab);
            addNewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new AddNewTask()).addToBackStack("add new task").commit();
                }
            });

            //navigation
            BottomNavigationItemView todoView = findViewById(R.id.nav_todo);
            todoView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    toolbar.setText(getResources().getString(R.string.todo));
                    addNewTask.setVisibility(View.GONE);
                    addNewTask.setEnabled(true);
                    fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, new TaskRecycler(taskManager.getTodoList())).addToBackStack("todo view").commit();
                }
            });
            BottomNavigationItemView doneView = findViewById(R.id.nav_done);
            doneView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    toolbar.setText(getResources().getString(R.string.done));
                    addNewTask.setVisibility(View.INVISIBLE);
                    addNewTask.setEnabled(false);
                    fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, new TaskRecycler(taskManager.getDoneList())).addToBackStack("done view").commit();
                }
            });
            if (addNewTask.getVisibility() == View.INVISIBLE) {
                addNewTask.setVisibility(View.GONE);
            }
            ImageButton settingsButton = findViewById(R.id.settings_button);
            ImageButton backButton = findViewById(R.id.backButton);
            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new Settings()).addToBackStack("Settings").addToBackStack("settings view").commit();
                    toolbar.setText(getResources().getString(R.string.settings));
                    addNewTask.setVisibility(View.INVISIBLE);
                    backButton.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            CharSequence text = e.getMessage();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(contextMain, text, duration);
            toast.show();
        }
    }
    public void setLocale (Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
    public static TaskManager getTaskManager() {
        return taskManager;
    }
    public static UiModeManager getUiModeManager() {
        return uiModeManager;
    }
}