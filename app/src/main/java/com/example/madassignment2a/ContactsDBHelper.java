package com.example.madassignment2a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    public ContactsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + ContactsDBSchema.ContactsTable.TNAME
                + "("
                + ContactsDBSchema.ContactsTable.Cols.NAME+" TEXT,"
                + ContactsDBSchema.ContactsTable.Cols.NUMBER+" TEXT,"
                + ContactsDBSchema.ContactsTable.Cols.EMAIL+" TEXT,"
                + ContactsDBSchema.ContactsTable.Cols.PHOTO+" BLOB"
                + ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
