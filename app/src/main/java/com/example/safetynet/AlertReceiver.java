package com.example.safetynet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("DEFAULT_ID",
                    "DEFAULT_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        Intent dismissIntent = new Intent(context, DismissReceiver.class);
        PendingIntent dismissPi = PendingIntent.getBroadcast(context, 0, dismissIntent, 0);
        Intent curTripIntent = new Intent(context, CurrentTripActivity.class);
        PendingIntent curTripPi = PendingIntent.getActivity(context, 0, curTripIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "DEFAULT_ID")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Are you OK?")
                .setContentText("Emergency contacts will be notified in a minute unless you dismiss this notification.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Emergency contacts will be notified in a minute unless you dismiss this notification."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .setContentIntent(curTripPi)
                .setDeleteIntent(dismissPi)

                .addAction(R.drawable.x_checkbox_selector, "Dismiss",
                        dismissPi);
        notificationManager.notify(0, builder.build());


        AlertManager.setSMSTime(context, "00:01");

        /*SmsManager smsManager = SmsManager.getDefault();
        String message = MessageManager.getTripMessage(context);
        for(Contact contact : Contact.Retrieve(context, Contact.StorageMode.TRIP_SPECIFIC)) {
            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
        }
        CharSequence text = "Sent";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
    }
}