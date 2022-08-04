package com.example.safetynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("ActivityPrefs", Context.MODE_PRIVATE);
        if(!prefs.getBoolean("onboarded", false)) {
            Intent intent = new Intent(this, OnboardActivity.class);
            startActivity(intent);
            finish();
        }
        if(prefs.getBoolean("onTrip", false)) {
            Intent intent = new Intent(this, CurrentTripActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);


    }
}
