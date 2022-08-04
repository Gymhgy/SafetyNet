package com.example.safetynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Button newTrip = (Button)findViewById(R.id.newTripButton);
        newTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTripActivity.class);
                startActivity(intent);
            }
        });
        Button settings = (Button)findViewById(R.id.settingsButton);

    }
}
