package com.example.safetynet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safetynet.contactview.Contact;
import com.example.safetynet.contactview.ContactAdapter;

import java.util.List;

public class OnboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        List<Contact> contacts = Contact.Retrieve(this, Contact.StorageMode.MASTER_LIST);

        Button addButton = (Button)findViewById(R.id.addButton);
        EditText contactNumber = (EditText)findViewById(R.id.contactNumber);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contacts.add(new Contact(null, contactNumber.getText().toString()));
                contactNumber.getText().clear();
            }
        });

        Button addFromContacts = (Button)findViewById(R.id.addFromContacts);
        addFromContacts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Not implemented yet";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View mDecorView = getWindow().getDecorView();
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }*/
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.contactsList);
        ContactAdapter adapter = new ContactAdapter(contacts);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        EditText emergencyMsg = (EditText)findViewById(R.id.emergencyMsg);
        Button finishButton= (Button)findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("ActivityPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putBoolean("onboarded", true);
                ed.apply();
                MessageManager.setMasterMessage(OnboardActivity.this, emergencyMsg.getText().toString());
                Contact.Save(OnboardActivity.this, contacts, Contact.StorageMode.MASTER_LIST);
                Intent intent = new Intent(OnboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}