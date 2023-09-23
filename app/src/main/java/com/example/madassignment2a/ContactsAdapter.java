package com.example.madassignment2a;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsVH>
{
    public ContactsAdapter()
    {

    }

    @NonNull
    @Override
    public ContactsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_layout, parent, false);

        ContactsVH contactVH = new ContactsVH(view, parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("in", "in");
            }
        });

        return contactVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsVH holder, int position)
    {
        // Holder logic goes here

    }

    @Override
    public int getItemCount() {
            return 100; // Implement properly
        }

}
