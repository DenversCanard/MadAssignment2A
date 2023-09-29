package com.example.madassignment2a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

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
    public Contact getContact(Contact contact) {
        String[] cols = {"id"};
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(contact.getId())};
        Cursor cursor = db.query(ContactsDBSchema.ContactsTable.TNAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        ContactsDBCursor contactsDBCursor = new ContactsDBCursor(cursor);

        try
        {
            // An issue to fix TODO
            contactsDBCursor.moveToFirst();
            while(!contactsDBCursor.isAfterLast())
            {
                return contactsDBCursor.getContact();
//                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        return null;
    }

    // Update
    public void updateContact(Contact contact)
    {
        ContentValues cv = new ContentValues();
        cv.put(ContactsDBSchema.ContactsTable.Cols.ID, contact.getId());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NAME, contact.getName());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NUMBER, contact.getNumber());
        cv.put(ContactsDBSchema.ContactsTable.Cols.DESCRIPTION, contact.getDescription());
        cv.put(ContactsDBSchema.ContactsTable.Cols.PHOTO, contact.getPhotoPath());

        String[] whereValue = {String.valueOf(contact.getId())};
        db.update(ContactsDBSchema.ContactsTable.TNAME, cv,
                ContactsDBSchema.ContactsTable.Cols.ID + " = ?",
                whereValue);
    }

    // Delete
    public void deleteContact(Contact contact)
    {
        String[] whereValue = {String.valueOf(contact.getId())};
        db.delete(ContactsDBSchema.ContactsTable.TNAME,
                ContactsDBSchema.ContactsTable.Cols.ID + " = ?",
                whereValue);
    }
    // Get all
    public ArrayList<Contact> getAll()
    {
        ArrayList<Contact> allContacts = new ArrayList<>();
        Cursor cursor = db.query(ContactsDBSchema.ContactsTable.TNAME,
                null,
                null,
                null,
                null,
                null,
                null);

        ContactsDBCursor contactsDBCursor = new ContactsDBCursor(cursor);

        try
        {
            // An issue to fix TODO
            contactsDBCursor.moveToFirst();
            while(!contactsDBCursor.isAfterLast())
            {
                allContacts.add(contactsDBCursor.getContact());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        return allContacts;
    } // Empty for now
}
