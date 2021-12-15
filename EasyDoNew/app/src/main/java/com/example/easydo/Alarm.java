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

public class Alarm extends BroadcastReceiver {
    UUID id;
    @Override
    public void onReceive(Context context, Intent intent) {
        reciverAlarm(intent.getIntExtra("id", 1), context,intent,intent.getExtras().getString("title"),"im msg 1");


//receiving Second Alarm from MainActivity.Class
        // recAlarmo(2,context,intent,"title 2","im msg 2");
    }


    // receiving Alarms Method and Creating Notifications

    public void reciverAlarm(int id , Context context,Intent intent,String title,String msg){
        Intent intent1 = new Intent(context, MainActivity.class);
        Context context1 = new MainActivity().getBaseContext();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{intent1}, PendingIntent.FLAG_UPDATE_CURRENT);

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
