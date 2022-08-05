package com.example.safetynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safetynet.contactview.Contact;
import com.example.safetynet.contactview.ContactAdapter;

import java.util.List;

public class NewTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        List<Contact> contacts = Contact.Retrieve(this, Contact.StorageMode.MASTER_LIST);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.newTripContacts);
        ContactAdapter adapter = new ContactAdapter(contacts, true);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        EditText destination = (EditText)findViewById(R.id.destination);
        EditText expectedTripTime = (EditText)findViewById(R.id.expectedTripTime);
        EditText tripMsg = (EditText)findViewById(R.id.tripMsg);

        tripMsg.setText(MessageManager.getMasterMessage(this));

        Button startTrip = (Button)findViewById(R.id.startTrip);
        startTrip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("TripInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("destination", destination.getText().toString());
                ed.putString("expectedTripTime", expectedTripTime.getText().toString());
                ed.apply();
                MessageManager.setTripMessage(NewTripActivity.this, tripMsg.getText().toString());
                Contact.Save(NewTripActivity.this, contacts, Contact.StorageMode.TRIP_SPECIFIC);

                AlertManager.setAlert(NewTripActivity.this, expectedTripTime.getText().toString());

                Intent intent = new Intent(NewTripActivity.this, CurrentTripActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}