package com.example.madassignment2a;

import android.content.ContentResolver;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsFragment extends Fragment implements ContactsAdapter.AdapterListener
{

    RecyclerView recycView;
    ContactsAdapter contactsAdapter;
    MainActivityData mainActivityData;


    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        recycView = (RecyclerView) view.findViewById(R.id.contactsRecyclerView);
        recycView.setLayoutManager(new LinearLayoutManager(   // TODO
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));

        contactsAdapter = new ContactsAdapter(this, ContactsDataAccessObject.getInstance());
        recycView.setAdapter(contactsAdapter);

        FloatingActionButton addContact = view.findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setProfileID(0);
                mainActivityData.setDisplayScreen("Profile");
            }
        });


        FloatingActionButton syncContact = view.findViewById(R.id.syncContacts);
        syncContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).syncContacts();
                contactsAdapter.notifyDataSetChanged();
            }
        });
        Log.d("contacts", "True");
        contactsAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void processCommand(String command, int position)
    {
        if(command.equals("Profile"))
        {
            Log.d("posCommand", position+"");
            mainActivityData.setProfileID(position);
            mainActivityData.setDisplayScreen("Profile");
        }
    }


}