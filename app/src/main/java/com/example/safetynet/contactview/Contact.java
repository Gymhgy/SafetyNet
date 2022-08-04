package com.example.safetynet.contactview;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Contact {
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String toString() {
        return (TextUtils.isEmpty(name) ? "" : name + " ") + number;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }

    private String name;
    private String number;

    public static void Save(Context context, List<Contact> contacts, StorageMode mode) {
        Set<String> encodedContacts = Encode(contacts);
        SharedPreferences prefs = context.getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putStringSet(ModeToKey(mode), encodedContacts);
        ed.apply();
    }
    public static List<Contact> Retrieve(Context context, StorageMode mode) {
        SharedPreferences prefs = context.getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        Set<String> contactsSet = prefs.getStringSet(ModeToKey(mode), null);
        return Decode(contactsSet);
    }
    public static void Clear(Context context, StorageMode mode) {
        SharedPreferences prefs = context.getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        prefs.edit().remove(ModeToKey(mode));
    }
    private static String ModeToKey(StorageMode mode) {
        switch(mode) {
            case MASTER_LIST:
                return "Contacts";
            case TRIP_SPECIFIC:
                return "TripContacts";
            default:
                return "";
        }
    }

    private static List<Contact> Decode(Set<String> encodedContacts) {
        if(encodedContacts == null) {
            return new ArrayList<Contact>();
        }
        List <Contact> contacts = new ArrayList<>();
        for(String encoded : encodedContacts) {
            String[] temp = encoded.split("/", 2);
            int length1 = Integer.parseInt(temp[0]);
            String name = temp[1].substring(0, length1);
            String number = temp[1].substring(length1);
            contacts.add(new Contact(name, number));
        }
        return contacts;
    }

    private static Set<String> Encode(List<Contact> contacts) {
        Set<String> encodedContacts = new HashSet<>();
        for(Contact contact : contacts) {
            String name = (TextUtils.isEmpty(contact.getName()) ? "" : contact.getName() + " ");
            String number = contact.getNumber();
            String encoded = ""+name.length()+"/"+name+number;
            encodedContacts.add(encoded);
        }
        return encodedContacts;
    }
    public enum StorageMode {
        MASTER_LIST,
        TRIP_SPECIFIC
    }
}
