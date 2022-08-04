package com.example.safetynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safetynet.contactview.Contact;
import com.example.safetynet.contactview.ContactAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CurrentTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trip);

        TextView tripInfo = (TextView) findViewById(R.id.tripInfo);
        TextView endTimeTextView = (TextView) findViewById(R.id.endTimeTextView);

        SharedPreferences prefs = getSharedPreferences("TripInfo", Context.MODE_PRIVATE);
        tripInfo.setText(prefs.getString("destination", "Your current trip"));
        String tripTime = prefs.getString("expectedTripTime", "00:05");
        if(TextUtils.isEmpty(tripTime)) tripTime = "00:05";
        int hours = Integer.parseInt(tripTime.split(":")[0]);
        int mins = Integer.parseInt(tripTime.split(":")[1]);

        LocalDateTime end = LocalDateTime.now().plusHours(hours).plusMinutes(mins);
        endTimeTextView.setText(end.format(DateTimeFormatter.ofPattern("hh:mm a")));

        Button addTime = (Button)findViewById(R.id.addButton);
        Button subtractTime = (Button)findViewById(R.id.subtractTime);

        Button finishTrip = (Button)findViewById(R.id.finishTripButton);

        finishTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("TripInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = prefs.edit();

                MessageManager.clearTripMessage(CurrentTripActivity.this);
                Contact.Clear(CurrentTripActivity.this, Contact.StorageMode.TRIP_SPECIFIC);

                ed.clear();
                ed.apply();

                Intent intent = new Intent(CurrentTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        List<Contact> tripContacts = Contact.Retrieve(this, Contact.StorageMode.TRIP_SPECIFIC);
        RecyclerView rvCurTripContacts = (RecyclerView) findViewById(R.id.curTripContacts);
        ContactAdapter adapter = new ContactAdapter(tripContacts);
        rvCurTripContacts.setAdapter(adapter);
        rvCurTripContacts.setLayoutManager(new LinearLayoutManager(this));

    }
}