package com.example.easydo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.Task;
import com.example.easydo.dao.TaskContract;
import com.example.easydo.dao.TaskDBHelper;
import com.example.easydo.databinding.AppBarMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static UiModeManager uiModeManager;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private static Context contextMain;
    private static boolean onTodoList = true;
    private static boolean isSettings = true;
    private static TaskDBHelper easDoDBHelper;
    private static TaskManager taskManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            easDoDBHelper = new TaskDBHelper(this);
            taskManager = new TaskManager(easDoDBHelper.getReadableDatabase(), easDoDBHelper.getWritableDatabase());

            getSupportActionBar().hide();
            setContentView(R.layout.activity_main);
            uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
            TextView toolbar = findViewById(R.id.toolbartitle);
            contextMain = getApplicationContext();
            //TODO LOCAL ÄNDERT SICH BEIM DREHEN
            setLocale(getResources().getConfiguration().locale.toString());
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
                    addNewTask.setVisibility(View.GONE);
                    addNewTask.setEnabled(false);
                }
            });
            createNotificationChannel();


            ImageButton settingsButton = findViewById(R.id.settings_button);

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSettings){
                        settingsButton.setImageResource(R.drawable.ic_arrow_left_solid);
                        fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new Settings()).addToBackStack("settings view").commit();
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
            Log.d(TAG, "onCreate: " + e.getStackTrace());
        }
    }
    public void setLocale (String lang) {
        Configuration configuration = contextMain.getResources().getConfiguration();
        Locale locale = new Locale(lang);

        if(!configuration.locale.equals(locale)){
            Locale.setDefault(locale);
            configuration.locale = locale;
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }
    public static TaskManager getTaskManager() {
        return taskManager;
    }
    public static UiModeManager getUiModeManager() {
        return uiModeManager;
    }
    public static Context getMainContext() {return contextMain;}

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence title = "Todo app channel";
            String description = "Todo app channel";
            int priority = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("Alarm", title,priority);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void setAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent,0);
        Calendar calendar = Calendar.getInstance();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_LONG).show();
    }
    public void creatEvent(EditText title, EditText location, EditText description, Calendar calendar){
        if (Build.VERSION.SDK_INT >= 14) {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                   .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getInstance().getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                    .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString())
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}