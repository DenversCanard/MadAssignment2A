package com.example.madassignment2a;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsVH>
{
    public interface AdapterListener {
        public void processCommand(String command,int position);
    }

    private AdapterListener mCallBack;
    public ContactsAdapter(AdapterListener callBack)
    {
        mCallBack = callBack;
    }

    @NonNull
    @Override
    public ContactsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_layout, parent, false);
        ContactsVH contactVH = new ContactsVH(view, parent);

        return contactVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsVH holder, int position)
    {
        // Holder logic goes here
        holder.name.setText("name" + position);
        holder.number.setText(position+"");
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.processCommand("Profile", position); // needs work
            }
        });
        Log.d("pos", "pos"+position);
    }

    @Override
    public int getItemCount() {
            return 40; // Implement properly
        }

}
