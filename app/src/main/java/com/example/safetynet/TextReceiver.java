package com.example.safetynet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.safetynet.contactview.Contact;

public class TextReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = MessageManager.getTripMessage(context);
        for(Contact contact : Contact.Retrieve(context, Contact.StorageMode.TRIP_SPECIFIC)) {
            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
        }
    }
}