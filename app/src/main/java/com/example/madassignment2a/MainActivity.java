package com.example.madassignment2a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MainActivityData mainActivityDataViewModel;
    String verifyScreenState;

    ContactsFragment contacts = new ContactsFragment();
    ProfileFragment profile = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prep Sql lite DB
        ContactsDataAccessObject contactsDB = ContactsDataAccessObject.getInstance();
        contactsDB.load(getApplicationContext());

        // test DB
        Contact contactOne = new Contact(1,"jimmy", 03450045, "yuppee", "nope.jpg");
        contactsDB.addContact(contactOne);
        Contact newContact = contactsDB.getContact(contactOne);
        Toast.makeText(getApplicationContext(), newContact.getId()+"", Toast.LENGTH_SHORT);

        // Prep mutable data
        mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);

        String tmpScreenState = "Contacts";
        if(savedInstanceState != null)
        {
            mainActivityDataViewModel.setDisplayScreen(savedInstanceState.getString("screenValue"));
            tmpScreenState = savedInstanceState.getString("screenValue");
        }
        verifyScreenState = tmpScreenState;

        Log.d("main", "main");
        mainActivityDataViewModel.DisplayScreen.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (string.compareTo(verifyScreenState) != 0) {
                    Log.d("String", string);

                    if (string.equals("Contacts")) {
                        loadContactsFragment();
                    }
                    if (string.equals("Profile")) {
                        loadContactInfoFragment();
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

    public void loadContactInfoFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerView);
        if(frag==null){
            fm.beginTransaction().add(R.id.fragmentContainerView,profile).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.fragmentContainerView,profile).commit();
        }
    }
}