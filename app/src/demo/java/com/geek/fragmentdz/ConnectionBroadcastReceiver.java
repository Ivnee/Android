package com.geek.fragmentdz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {
    private int msgID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.drawable.icon1).setContentTitle(context.getString(R.string.warning)).setContentText(context.getString(R.string.connection_problems));
        PendingIntent contentIntent = PendingIntent.
                getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).notify(msgID++, builder.build());

    }
}
