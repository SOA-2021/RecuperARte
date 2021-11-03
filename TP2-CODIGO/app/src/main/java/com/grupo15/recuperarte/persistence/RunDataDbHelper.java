package com.grupo15.recuperarte.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.grupo15.recuperarte.persistence.RunDataContract.RunDataEntry;

public class RunDataDbHelper extends SQLiteOpenHelper {
    private static final String DB = "RunData.db";
    private static final int DB_VER = 1;

    public RunDataDbHelper(Context c) {
        super(c, DB, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strb = "CREATE TABLE " +
                RunDataEntry.TABLE +
                " (" +
                RunDataEntry._ID +
                " INTEGER PRIMARY KEY," +
                RunDataEntry.COL_METERS +
                " REAL," +
                RunDataEntry.COL_RUN +
                " INTEGER," +
                RunDataEntry.COL_DT +
                " TEXT)";
        db.execSQL(strb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers) { /* no op */ }
}
