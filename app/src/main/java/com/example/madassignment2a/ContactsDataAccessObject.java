package com.example.madassignment2a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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

    public void clearDatabase()
    {
//        db.execSQL("drop table if exists " + ContactsDBSchema.ContactsTable.TNAME);

        db.execSQL("delete from " + ContactsDBSchema.ContactsTable.TNAME);
    }

    // Create
    public void addContact(Contact contact){
        ContentValues cv = new ContentValues();
        cv.put(ContactsDBSchema.ContactsTable.Cols.NAME, contact.getName());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NUMBER, contact.getNumber());
        cv.put(ContactsDBSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
        cv.put(ContactsDBSchema.ContactsTable.Cols.PHOTO, contact.getPhoto());
        db.insert(ContactsDBSchema.ContactsTable.TNAME, null, cv);
    }

    // Read
    public Contact getContact(Contact contact) {
        // can be used to check if a person is in the Database, so we check name and number as well
        String whereClause = "name = ? AND number = ?";
        String[] whereArgs = { contact.getName(),contact.getNumber()};
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

    public Contact getContactIndex(int index) {
        Cursor cursor = db.query(ContactsDBSchema.ContactsTable.TNAME,
                null,
                null,
                null,
                null,
                null,
                null);

        ContactsDBCursor contactsDBCursor = new ContactsDBCursor(cursor);
        int posCounter = 0;

        try
        {
            // An issue to fix TODO
            contactsDBCursor.moveToFirst();
            while(!contactsDBCursor.isAfterLast())
            {
                posCounter++;

                if(posCounter == index)
                {
                    return contactsDBCursor.getContact();
                }
                contactsDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        return null;
    }

    public int getContactPos(String number)
    {
        // can be used to check if a person is in the Database, so we check name and number as well
        Cursor cursor = db.query(ContactsDBSchema.ContactsTable.TNAME,
                null,
                null,
                null,
                null,
                null,
                null);

        ContactsDBCursor contactsDBCursor = new ContactsDBCursor(cursor);
        int posCounter = 0;

        try
        {
            // An issue to fix TODO
            contactsDBCursor.moveToFirst();
            while(!contactsDBCursor.isAfterLast())
            {
                posCounter++;

                if(contactsDBCursor.getContact().getNumber().equals(number))
                {
                    return posCounter;
                }
                cursor.moveToNext();

            }
        }
        finally
        {
            cursor.close();
        }
        return 0;
    }

    // Update
    public void updateContact(Contact contact)
    {
        ContentValues cv = new ContentValues();
        cv.put(ContactsDBSchema.ContactsTable.Cols.NAME, contact.getName());
        cv.put(ContactsDBSchema.ContactsTable.Cols.NUMBER, contact.getNumber());
        cv.put(ContactsDBSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
        cv.put(ContactsDBSchema.ContactsTable.Cols.PHOTO, contact.getPhoto());

        String[] whereValue = {String.valueOf(contact.getNumber())};
        db.update(ContactsDBSchema.ContactsTable.TNAME, cv,
                ContactsDBSchema.ContactsTable.Cols.NUMBER + " = ?",
                whereValue);
    }


    // Delete
    public void deleteContact(Contact contact)
    {
        String[] whereValue = {String.valueOf(contact.getNumber())};
        db.delete(ContactsDBSchema.ContactsTable.TNAME,
                ContactsDBSchema.ContactsTable.Cols.NUMBER + " = ?",
                whereValue);
    }

    // Get all contacts
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
