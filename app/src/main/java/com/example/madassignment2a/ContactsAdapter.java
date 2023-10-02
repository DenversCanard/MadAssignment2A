package com.example.madassignment2a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private ContactsDataAccessObject db;
    public ContactsAdapter(AdapterListener callBack, ContactsDataAccessObject db)
    {
        mCallBack = callBack;
        this.db = db;
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
        Contact curContact = db.getContactIndex(position+1);

        if(curContact != null)
        {
            // Holder logic goes here
            holder.name.setText(curContact.getName());
            holder.number.setText(curContact.getNumber()+"");

            byte[] image = curContact.getPhoto();
            if(image != null)
            {
                Bitmap decoded = BitmapFactory.decodeByteArray(image, 0, image.length);
                holder.photo.setImageBitmap(decoded);
            }

            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallBack.processCommand("Profile", position+1); // needs work
                }
            });
        }
    }

    @Override
    public int getItemCount() {
            return db.getAll().size(); // Implement properly
        }

}
