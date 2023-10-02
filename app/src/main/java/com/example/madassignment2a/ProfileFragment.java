package com.example.madassignment2a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class ProfileFragment extends Fragment {

    private boolean photoChanged = false;

    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        ContactsDataAccessObject contactsDataAccessObject = ContactsDataAccessObject.getInstance();


        Button backButton = mainView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setDisplayScreen("Contacts");
                mainActivityData.setProfileID(0);
            }
        });


        // Apply appropriate information
        Log.d("io", mainActivityData.getProfileID()+"");
        if(mainActivityData.getProfileID() != 0)
        {
//            Log.d("Index", mainActivityData.getProfileID()+"");
            Contact curContact = contactsDataAccessObject.getContactIndex(mainActivityData.getProfileID());
//            Log.d("Name", curContact.getName());


            ((TextView) mainView.findViewById(R.id.nameProfile)).setText(curContact.getName());
            ((TextView) mainView.findViewById(R.id.numberProfile)).setText(curContact.getNumber());
            ((TextView) mainView.findViewById(R.id.emailProfile)).setText(curContact.getEmail());

            String name = ((TextView) mainView.findViewById(R.id.nameProfile)).getText().toString();
            Log.d("FinalName", name);
            // photo loading
            byte[] imageBytes = curContact.getPhoto();
            if(imageBytes != null)
            {
                Bitmap decoded = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                ((ImageButton) mainView.findViewById(R.id.contactPhoto)).setImageBitmap(decoded);
            }
        }
        else
        {
            ((TextView) mainView.findViewById(R.id.nameProfile)).setText("Name");
            ((TextView) mainView.findViewById(R.id.numberProfile)).setText("Number");
            ((TextView) mainView.findViewById(R.id.emailProfile)).setText("Email");

        }

        // Add Image
        ImageButton profilePhoto = mainView.findViewById(R.id.contactPhoto);

        ActivityResultLauncher<Intent> thumbNailLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap image = (Bitmap) data.getExtras().get("data");

                                if (image != null) {
                                    profilePhoto.setImageBitmap(image);
                                }

                            }
                        });

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                thumbNailLauncher.launch(intent);
                photoChanged = true;
            }
        });
//
//
//
        FloatingActionButton addButton = mainView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = ((TextView) mainView.findViewById(R.id.nameProfile)).getText().toString();
                 String number = ((TextView) mainView.findViewById(R.id.numberProfile)).getText().toString();
                 // Need to check that number can be converted to an int

                 String email = ((TextView) mainView.findViewById(R.id.emailProfile)).getText().toString();

                 // get photo
                 byte[] photo = null;
                 if(photoChanged)
                 {
                     //get Imagebutton bitmap
                     Bitmap imageBitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
                     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                     imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                     photo = outputStream.toByteArray();
                 }

                 if (mainActivityData.getProfileID() != 0) {
                     Contact curContact = new Contact(name, number, email, photo);

                     contactsDataAccessObject.updateContact(curContact); // Todo
                 } else {
                     int freeSpot = contactsDataAccessObject.getAll().size() + 1;

                     Contact curContact = new Contact(name, number, email, photo);
                     contactsDataAccessObject.addContact(curContact);

                 }
                 mainActivityData.setDisplayScreen("Contacts");
             }
        });

        FloatingActionButton deleteButton = mainView.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((TextView) mainView.findViewById(R.id.nameProfile)).getText().toString();
                String number = ((TextView) mainView.findViewById(R.id.numberProfile)).getText().toString();
                // Need to check that number can be converted to an int

                String email = ((TextView) mainView.findViewById(R.id.emailProfile)).getText().toString();

                // get photo
                byte[] photo = null;
                //get Imagebutton bitmap
                Bitmap imageBitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                photo = outputStream.toByteArray();

                Contact curContact = new Contact(name, number, email, photo);
                contactsDataAccessObject.deleteContact(curContact);

                mainActivityData.setDisplayScreen("Contacts");
            }
        });

        return mainView;
    }
}