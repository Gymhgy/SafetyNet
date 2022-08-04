package com.example.safetynet;

import android.content.Context;
import android.content.SharedPreferences;

public class MessageManager {
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("Message", Context.MODE_PRIVATE);
    }
    public static String getMasterMessage(Context context) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString("message", "");
    }

    public static void setMasterMessage(Context context, String message) {
        SharedPreferences prefs = getPrefs(context);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("message", message);
        ed.apply();
    }
    public static String getTripMessage(Context context) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString("tripMessage", "");
    }

    public static void setTripMessage(Context context, String message) {
        SharedPreferences prefs = getPrefs(context);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("tripMessage", message);
        ed.apply();
    }
    public static void clearTripMessage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Message", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.remove("tripMessage");
        ed.apply();
    }
}
