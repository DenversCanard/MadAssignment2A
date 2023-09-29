package com.example.madassignment2a;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        contactsAdapter = new ContactsAdapter(this);
        recycView.setAdapter(contactsAdapter);

        return view;
    }

    @Override
    public void processCommand(String command, int position)
    {
        if(command.equals("Profile"))
        {
            mainActivityData.setDisplayScreen("Profile");
        }
    }
}