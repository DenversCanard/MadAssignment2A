package com.example.madassignment2a;

import android.database.Cursor;
import android.database.CursorWrapper;

public class ContactsDBCursor extends CursorWrapper {

    public ContactsDBCursor(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        String name = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.NAME));
        String number = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.NUMBER));
        String description = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.EMAIL));
        byte[] photo = getBlob(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.PHOTO));

        return new Contact(name, number, description, photo);
    }
}
