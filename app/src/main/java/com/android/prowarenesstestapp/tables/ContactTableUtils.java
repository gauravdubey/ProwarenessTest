package com.android.prowarenesstestapp.tables;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.prowarenesstestapp.bean.ContactResultModel;
import com.android.prowarenesstestapp.database.DatabaseConstants;
import com.android.prowarenesstestapp.database.DatabaseProvider;
import com.android.prowarenesstestapp.utils.AppConstant;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactTableUtils {


    /**
     * Save Contact List in Db
     * @param context
     * @param aContactList
     */
    public static void saveContactList(Context context, ArrayList<ContactResultModel> aContactList) {

        try {

            ArrayList<ContentValues> aContentValues = new ArrayList<ContentValues>();
            aContentValues.clear();

            for (ContactResultModel model : aContactList) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseConstants.COLUMN_CONTACT_ID, model.getUid());
                contentValues.put(DatabaseConstants.COLUMN_CONTACT_NAME, model.getName());
                aContentValues.add(contentValues);
            }

            deleteContact(context);
            bulkInsertions(context, aContentValues);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void updateRecords(Context context, ArrayList<ContactResultModel> aContactList) {

        try {

            ArrayList<ContentValues> aContentValues = new ArrayList<ContentValues>();
            aContentValues.clear();

            for (ContactResultModel model : aContactList) {

                if (!isAvailableRecords(context,model.getUid())){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseConstants.COLUMN_CONTACT_ID, model.getUid());
                    contentValues.put(DatabaseConstants.COLUMN_CONTACT_NAME, model.getName());
                    aContentValues.add(contentValues);
                }
            }
            bulkInsertions(context, aContentValues);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void deleteContact(Context context) {

        ContentResolver resolver = context.getContentResolver();
        int count = resolver.delete(DatabaseConstants.URI_TABLENAME_CONTACT,null,null);
        System.out.println("\n Delete Contact list " + count);
    }


    /**
     * Bulk insertions for all
     * @param context
     * @param aContentValues
     */
    public static void bulkInsertions(Context context, ArrayList<ContentValues> aContentValues) {

        DatabaseProvider mDBProvider = new DatabaseProvider();
        DatabaseProvider.DBHelper dbHelper = mDBProvider.new DBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        try {

            for (ContentValues contentValues : aContentValues) {

                if (contentValues != null) {

                    db.insert(DatabaseConstants.TABLE_CONTACT, null, contentValues);

                }
            }
            db.setTransactionSuccessful();
            System.out.println("Bulk Insertion completed");
        } catch (Exception e) {

        } finally {

            db.endTransaction();
            context.getContentResolver().notifyChange(DatabaseConstants.URI_TABLENAME_CONTACT, null);
            db.close();
        }

    }


    /**
     * Check Record in DB
     * @param context
     * @param id
     * @return
     */
    private static boolean isAvailableRecords(Context context,String id){

        boolean isAvailable = false;
        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(DatabaseConstants.URI_TABLENAME_CONTACT, null, DatabaseConstants.COLUMN_CONTACT_ID + "=?",
                    new String[]{id}, null);

            if (cursor!=null && cursor.getCount()>0){
                isAvailable=true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isAvailable;
    }

    /**
     * Get Contact List
     * @return
     */
    public static ArrayList<ContactResultModel> getContactList(Context context) {

        ArrayList<ContactResultModel> aContactList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(DatabaseConstants.URI_TABLENAME_CONTACT, null,
                    DatabaseConstants.COLUMN_IS_REMOVE+"==0", null, DatabaseConstants.COLUMN_CONTACT_NAME+" COLLATE NOCASE ASC");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ContactResultModel contactResultModel=new ContactResultModel();
                    // we store email and number as json so we need to convert them in model
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_CONTACT_NAME));
                    String uid = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.COLUMN_CONTACT_ID));
                    contactResultModel.setName(name);
                    contactResultModel.setUid(uid);
                    aContactList.add(contactResultModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return aContactList;
    }


    /**
     * Contact Row
     * @param context
     * @param contactId
     */
    public static void updateContact(Context context,String contactId) {

        ContentResolver resolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstants.COLUMN_IS_REMOVE, AppConstant.SOFT_DELETE);
        int count = resolver.update(DatabaseConstants.URI_TABLENAME_CONTACT,contentValues, DatabaseConstants.COLUMN_CONTACT_ID + "= ?",new String[] { contactId });

        System.out.println("\n Contact Update " + count);

    }
}
