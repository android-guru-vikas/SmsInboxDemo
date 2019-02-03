package com.dev.vokal.listener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.dev.vokal.R;
import com.dev.vokal.activity.SmsListActivity;
import com.dev.vokal.application.VokalApplication;
import com.dev.vokal.utils.Constants;

class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public NotificationHelper(Context base) {
        super(base);
    }

    public void createNotification(String title, String body) {
        Intent resultIntent = new Intent(getApplicationContext() , SmsListActivity.class);
        resultIntent.putExtra(Constants.KEY_SMS_BODY, body);
        resultIntent.putExtra(Constants.KEY_SMS_ADDRESS, title);
        resultIntent.putExtra(Constants.KEY_NEW_SMS, true);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(VokalApplication.getAppContext(),
                0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(VokalApplication.getAppContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) VokalApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(Constants.KEY_CHANNEL_ID, Constants.KEY_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(Constants.KEY_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }
}