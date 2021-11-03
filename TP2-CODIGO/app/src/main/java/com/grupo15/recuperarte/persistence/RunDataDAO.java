package com.grupo15.recuperarte.persistence;

import static com.grupo15.recuperarte.persistence.RunDataContract.RunDataEntry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RunDataDAO {
    @NonNull
    private final Context context;

    public RunDataDAO(@NonNull Context c) {
        this.context = c;
    }

    public long save(RunData d) {
        final RunDataDbHelper helper = new RunDataDbHelper(context);
        final SQLiteDatabase db = helper.getWritableDatabase();

        return db.insert(RunDataEntry.TABLE, null, d.toSQLValues());
    }

    public List<RunData> listAll() {
        /* SELECT MT, IS_RUN, DATETIME */
        final String[] projection = {
                RunDataEntry.COL_METERS,
                RunDataEntry.COL_RUN,
                RunDataEntry.COL_DT
        };

        final String orderBy = String.format("%s ASC", RunDataEntry.COL_DT);

        final RunDataDbHelper helper = new RunDataDbHelper(context);
        final SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor c = db.query(
                RunDataEntry.TABLE,
                projection,
                null,
                null,
                null,
                null,
                orderBy
        )) {
            List<RunData> l = new ArrayList<>();
            while (c.moveToNext()) {
                float mt = c.getFloat(c.getColumnIndexOrThrow(RunDataEntry.COL_METERS));
                boolean run = c.getInt(c.getColumnIndexOrThrow(RunDataEntry.COL_RUN)) == 1;
                LocalDateTime dt = LocalDateTime.parse(
                        c.getString(c.getColumnIndexOrThrow(RunDataEntry.COL_DT))
                );

                l.add(new RunData(mt, run, dt));
            }

            return l;
        }
    }

    public List<LocalDateTime> listRunningHours() {
        /* SELECT DATETIME */
        final String[] projection = {
                RunDataEntry.COL_DT
        };
        final String orderBy = String.format("%s ASC", RunDataEntry.COL_DT);

        /* WHERE is_running = 1 */
        final String where = String.format("%s = ?", RunDataEntry.COL_RUN);
        final String[] whereParams = {"1"};

        final RunDataDbHelper helper = new RunDataDbHelper(context);
        final SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor c = db.query(
                RunDataEntry.TABLE,
                projection,
                where,
                whereParams,
                null,
                null,
                orderBy
        );) {
            List<LocalDateTime> l = new ArrayList<>();
            while (c.moveToNext()) {
                LocalDateTime dt = LocalDateTime.parse(
                        c.getString(c.getColumnIndexOrThrow(RunDataEntry.COL_DT))
                );
                l.add(dt);
            }

            return l;
        }
    }
}
