package com.example.madassignment2a;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDataAccessObject {
    private static ContactsDataAccessObject contactsDataAccessObject = null;
    private SQLiteDatabase db;

    public static ContactsDataAccessObject getInstance()
    {
        if(contactsDataAccessObject == null)
        {
            contactsDataAccessObject = new ContactsDataAccessObject();
        }
        return contactsDataAccessObject;
    }

    public void load(Context context){
        this.db = new ContactsDBHelper(context).getWritableDatabase();
    }

    // Create
    public void addContact(Contact contact){
        ContentValues cv = new ContentValues();
        cv.put(ContactsDBSchema.ContactsTable.Cols.ID, contact.getId());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NAME, contact.getName());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NUMBER, contact.getNumber());
        cv.put(ContactsDBSchema.ContactsTable.Cols.DESCRIPTION, contact.getDescription());
        cv.put(ContactsDBSchema.ContactsTable.Cols.PHOTO, contact.getPhotoPath());
        db.insert(ContactsDBSchema.ContactsTable.TNAME, null, cv);
    }

    // Read
    public void getContact(Contact contact) {

    }

    // Update
    public void updateContact(Contact contact)
    {

    }

    // Delete
    public void deleteContact(Contact contact)
    {

    }
    // Get all
    public void getAll(){} // Empty for now
}
