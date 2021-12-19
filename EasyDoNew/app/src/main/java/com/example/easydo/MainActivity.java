package com.example.easydo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.easydo.dao.TaskDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private FloatingActionButton addNewTask;
    private static Context contextMain;
    private static boolean onTodoList = true;
    private static boolean isSettings = true;
    private static TaskDBHelper easDoDBHelper;
    private static TaskManager taskManager;
    private static Locale localeSetting;
    private static Configuration conf;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            conf.setLocale(localeSetting);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            easDoDBHelper = new TaskDBHelper(this);
            taskManager = new TaskManager(easDoDBHelper.getReadableDatabase(), easDoDBHelper.getWritableDatabase());

            SharedPreferences languagePreferences = getApplicationContext().getSharedPreferences("EasyDoLanguageSetting", 0); // 0 - for private mode

            String languageSetting = languagePreferences.getString("lang", "");
            if(languageSetting.isEmpty())
                languageSetting = "en";

            localeSetting = new Locale(languageSetting);
            setLocale(localeSetting.getLanguage());

            getSupportActionBar().hide();
            setContentView(R.layout.activity_main);
            TextView toolbar = findViewById(R.id.toolbartitle);
            contextMain = getApplicationContext();

            fragmentManager = getSupportFragmentManager();
            //fill the fragment with the TaskRecycler
            if (onTodoList) {
                fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getTodoList())).commit();
                toolbar.setText(getResources().getString(R.string.todo));
            } else {
                fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getDoneList())).commit();
                toolbar.setText(getResources().getString(R.string.done));
            }

            addNewTask = findViewById(R.id.fab);

            addNewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new AddNewTaskFragment()).addToBackStack("add new task").commit();
                }
            });
            createNotificationChannel();


            ImageButton settingsButton = findViewById(R.id.settings_button);

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSettings) {
                        settingsButton.setImageResource(R.drawable.ic_arrow_left_solid);
                        fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new Settings()).addToBackStack("settings view").commit();
                        toolbar.setText(getResources().getString(R.string.settings));
                        addNewTask.setVisibility(View.GONE);
                        addNewTask.setEnabled(false);
                        isSettings = false;
                    } else {
                        addNewTask.setVisibility(View.VISIBLE);
                        addNewTask.setEnabled(true);
                        settingsButton.setImageResource(R.drawable.ic_settings_black);
                        isSettings = true;
                        if (onTodoList) {
                            toolbar.setText(getResources().getString(R.string.todo));
                            fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getTodoList())).addToBackStack("todo view").commit();
                        } else {
                            toolbar.setText(getResources().getString(R.string.done));
                            fragmentManager.beginTransaction().replace(R.id.host_fragment_content_main, TaskRecyclerFragment.newInstance(taskManager.getDoneList())).addToBackStack("done view").commit();
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

        } catch (Exception e) {
            CharSequence text = e.getMessage();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(contextMain, text, duration);
            toast.show();
            Log.d(TAG, "onCreate: " + e.getStackTrace());
        }
    }

    public void setLocale(String lang) {

        Locale appLocale = new Locale(lang);
        Locale.setDefault(appLocale);

        localeSetting = appLocale;

        Resources appRessources = getResources();
        DisplayMetrics displayMetrics = appRessources.getDisplayMetrics();
        Configuration configuration = appRessources.getConfiguration();
        configuration.locale = appLocale;
        appRessources.updateConfiguration(configuration, displayMetrics);

    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public FloatingActionButton getNewTaskFAB() {
        return addNewTask;
    }


    public static Context getMainContext() {
        return contextMain;
    }

    private void createNotificationChannel() {
        Log.d(TAG, "createNotificationChannel: called");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence title = "Todo app channel";
            String description = "Todo app channel";
            int priority = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel("EasyDoAlarm", title, priority);
            notificationChannel.setDescription(description);
            notificationChannel.setSound(null, null);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.deleteNotificationChannel(notificationChannel.getId());
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void setAlarm(int id, String title, String message, long millis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_LONG).show();
    }

    public static void deleteAlarm(int id) {
        NotificationManager notificationManager = (NotificationManager) getMainContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public void createCalenderAppEntry(EditText title, EditText location, EditText description, long millis) {
        if (Build.VERSION.SDK_INT >= 14) {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, millis)
                    .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                    .putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString())
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString())
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}