package com.example.bmicalculator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

public class NextActivity extends AppCompatActivity {
private static final String CHANNEL_ID = "notification channel";
    private static final int NOTIFICATION_ID = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.reminder,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Notification n = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.bmiicon)
                .setContentText("Hey Check Your BMI !!")
                .setSubText("Hello")
                .setChannelId(CHANNEL_ID )
                .build();
        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel", NotificationManager.IMPORTANCE_HIGH));
        nm.notify(NOTIFICATION_ID,n);
    }
}