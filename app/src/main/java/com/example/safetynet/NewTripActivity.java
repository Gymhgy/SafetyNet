package com.example.safetynet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
                Intent intent = new Intent(NewTripActivity.this, CurrentTripActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);
            }
        }
        else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("4159965423", null, "hi", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("4159965423", null, "hi", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}