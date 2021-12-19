package com.example.easydo;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class Alarm extends BroadcastReceiver {
    private String title, message;
    private int id;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            id = intent.getIntExtra("id", 1);
            title = intent.getStringExtra("title");
            message = intent.getStringExtra("message");
            receiverAlarm(id, context, intent, title, message);

            MediaPlayer alarmMediaPlayer = MediaPlayer.create(context, R.raw.alarm);
            alarmMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            alarmMediaPlayer.setLooping(false);
            alarmMediaPlayer.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void receiverAlarm(int id, Context context, Intent intent, String title, String msg) throws InterruptedException {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, id, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(context, "EasyDoAlarm")
                .setSmallIcon(R.drawable.ic_baseline_access_alarm)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(id, notification);
    }
}
