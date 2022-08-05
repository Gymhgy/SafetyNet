package com.example.safetynet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlertManager {
    private static PendingIntent current = null;
    public static void setAlert(Context context, String time) {
        AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
        int hours = Integer.parseInt(time.split(":")[0]);
        int mins = Integer.parseInt(time.split(":")[1]);
        Intent intent = new Intent(context, AlertReceiver.class);
        current = PendingIntent.getBroadcast(context, 0, intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (hours * 60L + mins) * 60 * 1000, current);
        /*SmsManager smsManager = SmsManager.getDefault();
        String message = MessageManager.getTripMessage(context);
        for(Contact contact : Contact.Retrieve(context, Contact.StorageMode.TRIP_SPECIFIC)) {
            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
        }*/
    }
    public static void setSMSTime(Context context, String time) {
        AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
        int hours = Integer.parseInt(time.split(":")[0]);
        int mins = Integer.parseInt(time.split(":")[1]);

        Intent intent = new Intent(context, TextReceiver.class);
        current = PendingIntent.getBroadcast(context, 0, intent, 0);

        manager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (hours * 60L + mins) * 60 * 1000, current);
    }
    public static void cancel(Context context) {
        AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
        manager.cancel(current);
    }
}
