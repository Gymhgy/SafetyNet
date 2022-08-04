package com.example.safetynet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safetynet.contactview.Contact;

import java.util.List;

public class NewTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        List<Contact> contacts = Contact.Retrieve(this);

    }
}