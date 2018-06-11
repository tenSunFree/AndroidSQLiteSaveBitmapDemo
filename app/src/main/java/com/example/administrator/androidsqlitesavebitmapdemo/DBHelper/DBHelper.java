package com.example.administrator.androidsqlitesavebitmapdemo.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.administrator.androidsqlitesavebitmapdemo.Sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    private static final String DB_Name = "SaveBitmap.db";
    private static final int DB_Version = 1;

    private static final String TBL_Name = "Gallery";

    private static final String COL_Name = "Name";
    private static final String COL_Data = "Data";

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    /**
     * Add Bitmap byte array to DB
     */
    public void addBitmap(String name, byte[] image) throws SQLException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_Name, name);
        contentValues.put(COL_Data, image);
        sqLiteDatabase.insert(TBL_Name, null, contentValues);
    }

    /**
     * Get Byte array bitmap
     */
    public byte[] getBitmapByName(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String[] select = {COL_Data};
        sqLiteQueryBuilder.setTables(TBL_Name);

        Cursor cursor = sqLiteQueryBuilder.query(
                sqLiteDatabase,
                select,
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );

        byte[] result = null;
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getBlob(cursor.getColumnIndex(COL_Data));
            } while (cursor.moveToNext());
        }

        return result;
    }
}
