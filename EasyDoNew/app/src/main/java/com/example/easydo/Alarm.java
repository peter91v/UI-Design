package com.example.easydo;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Alarm extends BroadcastReceiver {
    private String title;
    private int id;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            id = intent.getIntExtra("id", 1);
            title = intent.getStringExtra("title");
            reciverAlarm(id, context, intent, title, "message");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reciverAlarm(int id, Context context, Intent intent, String title, String msg) throws InterruptedException {
        //Intent intent1 = new Intent(context, MainActivity.class);
        //Context context1 = new MainActivity().getBaseContext();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, id, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "Alarm")
                .setSmallIcon(R.drawable.ic_baseline_access_alarm)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id, notificationBuilder.build());
    }
}
