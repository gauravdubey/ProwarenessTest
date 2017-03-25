package com.android.prowarenesstestapp.database;

import android.net.Uri;

public interface DatabaseConstants {
    String DATABASE_NAME = "prowareness.db";
    int DATABASE_VERSION = 1;
    String CONTENT_AUTHORITY = "com.android.prowarenesstestapp";

    String TABLE_CONTACT = "contacttable";

    String COLUMN_CONTACT_ID = "contactId";
    String COLUMN_CONTACT_NAME = "contactName";
    String COLUMN_IS_REMOVE = "isRemove";


    int CONTACT_VALUE = 101;

    Uri URI_TABLENAME_CONTACT = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TABLE_CONTACT);
}