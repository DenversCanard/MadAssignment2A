package com.example.madassignment2a;

import android.database.Cursor;
import android.database.CursorWrapper;

public class ContactsDBCursor extends CursorWrapper {

    public ContactsDBCursor(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        Integer id = getInt(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.ID));
        String name = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.NAME));
        Integer number = getInt(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.NUMBER));
        String description = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.DESCRIPTION));
        String photoPath = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.PHOTO));

        return new Contact(id, name, number, description, photoPath);
    }
}
