package com.android.prowarenesstestapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseProvider extends ContentProvider
{
	public DBHelper mDBHelper = null;
	public static Cursor cursor = null;
	SQLiteDatabase db = null;
	private static final UriMatcher sUriMatcher = buildUriMatcher();

	public class DBHelper extends SQLiteOpenHelper
	{
		public DBHelper(Context context)
		{
			super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db)
		{
			long avilablesize = db.getMaximumSize();
			db.setMaximumSize(avilablesize);
			createDatabaseTables(db);
		}

		private void createDatabaseTables(SQLiteDatabase db)
		{

			db.execSQL("CREATE TABLE " + DatabaseConstants.TABLE_CONTACT + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ DatabaseConstants.COLUMN_CONTACT_ID + " VARCHAR, "
					+ DatabaseConstants.COLUMN_CONTACT_NAME + " VARCHAR, "
					+ DatabaseConstants.COLUMN_IS_REMOVE +" INTEGER DEFAULT 0);");

		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		db = mDBHelper.getReadableDatabase();
		long avilablesize = db.getMaximumSize();
		db.setMaximumSize(avilablesize);
		int deleted = 0;

		if (sUriMatcher.match(uri) == DatabaseConstants.CONTACT_VALUE)
		{
			deleted = db.delete(DatabaseConstants.TABLE_CONTACT, selection, selectionArgs);
		}

		return deleted;
	}

	@Override
	public String getType(Uri uri)
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		db = mDBHelper.getWritableDatabase();
		long avilablesize = db.getMaximumSize();
		db.setMaximumSize(avilablesize);

		if (sUriMatcher.match(uri) == DatabaseConstants.CONTACT_VALUE)
		{
			db.insert(DatabaseConstants.TABLE_CONTACT, null, values);

		}

		return uri;
	}

	@Override
	public boolean onCreate()
	{
		mDBHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		db = mDBHelper.getReadableDatabase();
		long avilablesize = db.getMaximumSize();
		db.setMaximumSize(avilablesize);
		cursor = null;

		if (sUriMatcher.match(uri) == DatabaseConstants.CONTACT_VALUE)
		{
			cursor = db.query(DatabaseConstants.TABLE_CONTACT, projection, selection, selectionArgs, null, null, sortOrder);

		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		int upCount = 0;
		SQLiteDatabase db = mDBHelper.getWritableDatabase();
		long avilablesize = db.getMaximumSize();
		db.setMaximumSize(avilablesize);

		if (sUriMatcher.match(uri) == DatabaseConstants.CONTACT_VALUE)
		{
			upCount = db.update(DatabaseConstants.TABLE_CONTACT, values, selection, selectionArgs);
		}


		return upCount;
	}

	private static UriMatcher buildUriMatcher()
	{
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = DatabaseConstants.CONTENT_AUTHORITY;
		matcher.addURI(authority, DatabaseConstants.TABLE_CONTACT, DatabaseConstants.CONTACT_VALUE);
		return matcher;
	}
}