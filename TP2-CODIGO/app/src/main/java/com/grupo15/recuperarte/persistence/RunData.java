package com.grupo15.recuperarte.persistence;

import android.content.ContentValues;

import java.time.LocalDateTime;

import static com.grupo15.recuperarte.persistence.RunDataContract.RunDataEntry;

import com.grupo15.recuperarte.sensor.AccelerometerData;

/**
 * Modelo que representa la estructura interna dentro de
 * la base de datos.
 */
public class RunData implements ISQLObj {
    private final float meters;
    private final boolean isRunning;
    private final LocalDateTime dt;

    public RunData(float mt, boolean isRunning) {
        this.meters = mt;
        this.isRunning = isRunning;
        this.dt = LocalDateTime.now();
    }

    public RunData(float mt, boolean isRunning, LocalDateTime dt) {
        this.meters = mt;
        this.isRunning = isRunning;
        this.dt = dt;
    }

    @Override
    public ContentValues toSQLValues() {
        ContentValues v = new ContentValues();
        v.put(RunDataEntry.COL_METERS, meters);
        v.put(RunDataEntry.COL_RUN, isRunning ? 1 : 0);
        v.put(RunDataEntry.COL_DT, dt.toString());
        return v;
    }

    public float getMeters() { return meters; }
    public boolean isRunning() { return isRunning; }
    public LocalDateTime getDate() { return dt; }

    @Override
    public String toString() {
        return "RunData{" +
                "meters=" + meters +
                ", isRunning=" + isRunning +
                ", dt=" + dt +
                '}';
    }
}
