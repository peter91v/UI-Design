package com.example.easydo;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static UiModeManager uiModeManager;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private static Context contextMain;
    private static boolean onTodoList = true;
    private static boolean isSettings = true;
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


            fragmentManager = getSupportFragmentManager();
            //fill the fragment with the TaskRecycler
            if(onTodoList)
            {
                fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getTodoList())).commit();
                toolbar.setText(getResources().getString(R.string.todo));
            }
            else{
                fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getDoneList())).commit();
                toolbar.setText(getResources().getString(R.string.done));
            }

            FloatingActionButton addNewTask = findViewById(R.id.fab);
            addNewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new AddNewTaskFragment()).addToBackStack("add new task").commit();
                }
            });



            ImageButton settingsButton = findViewById(R.id.settings_button);

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSettings){
                        settingsButton.setImageResource(R.drawable.ic_arrow_left_solid);
                        fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new Settings()).addToBackStack("Settings").addToBackStack("settings view").commit();
                        toolbar.setText(getResources().getString(R.string.settings));
                        addNewTask.setVisibility(View.GONE);
                        addNewTask.setEnabled(false);
                        isSettings = false;
                    }
                    else{
                        addNewTask.setVisibility(View.VISIBLE);
                        addNewTask.setEnabled(true);
                        settingsButton.setImageResource(R.drawable.ic_settings_black);
                        isSettings = true;
                        if(onTodoList){
                            toolbar.setText(getResources().getString(R.string.todo));
                            fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getTodoList())).addToBackStack("todo view").commit();
                        }else{
                            toolbar.setText(getResources().getString(R.string.done));
                            fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getDoneList())).addToBackStack("todo view").commit();
                        }
                    }
                }
            });

            //navigation
            BottomNavigationItemView todoView = findViewById(R.id.nav_todo);
            todoView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onTodoList = true;
                    isSettings = true;
                    settingsButton.setImageResource(R.drawable.ic_settings_black);
                    toolbar.setText(getResources().getString(R.string.todo));
                    addNewTask.setVisibility(View.VISIBLE);
                    addNewTask.setEnabled(true);
                    fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getTodoList())).addToBackStack("todo view").commit();
                }
            });
            BottomNavigationItemView doneView = findViewById(R.id.nav_done);
            doneView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onTodoList = false;
                    isSettings = true;
                    settingsButton.setImageResource(R.drawable.ic_settings_black);
                    toolbar.setText(getResources().getString(R.string.done));
                    addNewTask.setVisibility(View.GONE);
                    addNewTask.setEnabled(false);
                    fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getDoneList())).addToBackStack("done view").commit();
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