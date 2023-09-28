package com.example.madassignment2a;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsVH extends RecyclerView.ViewHolder
{
    TextView name;
    TextView number;
    Button menu;

    public ContactsVH(@NonNull View itemView, ViewGroup parent)
    {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        number = itemView.findViewById(R.id.number);
        menu = itemView.findViewById(R.id.menubutton);
    }
}
