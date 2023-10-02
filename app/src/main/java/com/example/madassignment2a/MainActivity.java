package com.example.madassignment2a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ContactsDataAccessObject contactsDB;
    private MainActivityData mainActivityDataViewModel;
    private String verifyScreenState;

    private ContactsFragment contacts = new ContactsFragment();
    private ProfileFragment profile = new ProfileFragment();

    private final int REQUEST_READ_CONTACT_PERMISSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prep Sql lite DB
        contactsDB = ContactsDataAccessObject.getInstance();
        contactsDB.load(getApplicationContext());
        contactsDB.clearDatabase(); // clears db on start for testing
//        contactsDB.load(getApplicationContext());


        // Prep mutable data
        mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);

        String tmpScreenState = "Contacts";
        if(savedInstanceState != null)
        {
            mainActivityDataViewModel.setDisplayScreen(savedInstanceState.getString("screenValue"));
            tmpScreenState = savedInstanceState.getString("screenValue");
        }
        verifyScreenState = tmpScreenState;

        mainActivityDataViewModel.DisplayScreen.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (string.compareTo(verifyScreenState) != 0) {
                    Log.d("String", string);

                    if (string.equals("Contacts")) {
                        loadContactsFragment();
                    }
                    if (string.equals("Profile")) {
                        loadProfileFragment();
                    }
                    verifyScreenState = string;
                }
            }
        });

        loadContactsFragment();

    }

    // fragment loading
    public void loadContactsFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerView);
        if(frag==null){
            fm.beginTransaction().add(R.id.fragmentContainerView,contacts).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.fragmentContainerView,contacts).commit();
        }
    }

    public void loadProfileFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerView);
        if(frag==null){
            fm.beginTransaction().add(R.id.fragmentContainerView,new ProfileFragment()).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.fragmentContainerView,new ProfileFragment()).commit();
        }
    }

    public void syncContacts()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACT_PERMISSION);

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        try{
            cursor.moveToFirst();
            do{
                int curContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String curContactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String curContactNumber = getPhoneNumber(curContactID);
                String curContactEmail = getEmail(curContactID);
                // Getphoto
                Log.d("Id", curContactID+"");
                byte[] photo  = getPhoto(curContactID);

                // check if a contact exists
                int contactIndex = contactsDB.getContactPos(curContactNumber);

                // Add contact if it doesn't exist
                if(contactIndex == 0)
                {
                    Contact newContact = new Contact(curContactName, curContactNumber,curContactEmail,photo);
                    Log.d("testn",photo+"");
                    contactsDB.addContact(newContact);
                }

            }
            while (cursor.moveToNext());

        }
        finally {
            cursor.close();
        }
    }

    public String getPhoneNumber(int id)
    {
        String result="";
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(id)
        };
        Cursor c = getContentResolver().query(
                phoneUri, queryFields, whereClause,whereValues, null);
        try{
            c.moveToFirst();
            if(c.getCount() == 1)// found phone number
            {
                String phoneNumber = c.getString(0);
                result = phoneNumber.replaceAll("[^0-9]", "");

            }

        }
        finally {
            c.close();
        }
        return result;
    }

    public String getEmail(int id)
    {
        String result="";
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(id)
        };
        Cursor c = getContentResolver().query(
                emailUri, queryFields, whereClause,whereValues, null);

        try{
            c.moveToFirst();

            if(c.getCount() == 1) // found email
            {
                String emailAddress = c.getString(0);
                result = result+emailAddress;
            }

        }
        finally {
            c.close();
        }
        return result;
    }

    public byte[] getPhoto(int id)
    {

        Uri photoUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.Contacts.Photo.PHOTO
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(id)
        };
        Cursor c = getContentResolver().query(
                photoUri, null, whereClause,whereValues, null);

        try{
            c.moveToFirst();

            if(c.getCount() > 0)
            {
                byte[] photo = c.getBlob(0);
                return photo;
            }

        }
        finally {
            c.close();
        }

        return null;
    }
}