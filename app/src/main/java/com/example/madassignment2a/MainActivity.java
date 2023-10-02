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
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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
        contactsDB.clearDatabase();
//        contactsDB.load(getApplicationContext());




        // test DB
//        Contact contact = new Contact(1,"jimmy1", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(2,"jimmy2", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(3,"jimmy3", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(4,"jimmy4", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(5,"jimmy5", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(6,"jimmy6", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(7,"jimmy7", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(8,"jimmy8", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(9,"jimmy9", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(10,"jimmy10", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(11,"jimmy11", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(12,"jimmy12", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(13,"jimmy13", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(14,"jimmy14", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//
//        contact = new Contact(15,"jimmy15", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(16,"jimmy16", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(17,"jimmy17", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(18,"jimmy18", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(19,"jimmy19", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//        contact = new Contact(20,"jimmy20", "03450045", "yuppee", null);
//        contactsDB.addContact(contact);
//

//        syncContacts();
//        contactsDB.deleteContact(contactOne);
//        newContact = contactsDB.getContact(contactOne);
//        if(newContact == null)
//        {
//            Log.d("delete", "success");
//        }

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

                // check if a contact exists
                int contactIndex = contactsDB.getContactPos(curContactNumber);

                // Add contact if it doesn't exist
                if(contactIndex == 0)
                {
                    Contact newContact = new Contact(curContactName, curContactNumber,curContactEmail,null);
                    contactsDB.addContact(newContact);
                }
//                Log.d("Name", curContactName);
//                Log.d("Number", curContactNumber+"");
//                Log.d("Email", curContactEmail);

//                getPhoto(curContactID);

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

    public void getPhoto(int id)
    {
        // Todo
    }
}