package com.example.EDUAppGPSrinagar;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    public static final String EDUAPP_CHANNEL_ID="eduapp_channel";
    public static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager != null && notificationManager.getNotificationChannel(EDUAPP_CHANNEL_ID)==null){
                NotificationChannel channel=new NotificationChannel(EDUAPP_CHANNEL_ID,"EduApp Channel",NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is an Education Channel");
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);

                notificationManager.createNotificationChannel(channel);
            }

        }
    }

    @SuppressLint("MissingPermission")
    public static void sendNotification(Context context, String title, String message, Intent intent){
        PendingIntent intent1=PendingIntent.getActivities(context,0,new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,EDUAPP_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.splash)
                .setContentIntent(intent1)
                .setSilent(true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify((int)System.currentTimeMillis(),builder.build());
    }
}
