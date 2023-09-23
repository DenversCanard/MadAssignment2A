package com.example.madassignment2a;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    RecyclerView recycView;
    ContactsAdapter contactsAdapter;


    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recycView = (RecyclerView) view.findViewById(R.id.contactsRecyclerView);
//        recycView.setLayoutManager(new GridLayoutManager(   // TODO
//                getActivity(),
//                MapData.HEIGHT,
//                GridLayoutManager.HORIZONTAL,
//                false));

        contactsAdapter = new ContactsAdapter();
        recycView.setAdapter(contactsAdapter);

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }
}