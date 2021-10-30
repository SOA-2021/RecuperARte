package com.grupo15.recuperarte.sensor;

import android.hardware.SensorEvent;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.api_catedra.api.IRegistrable;
import com.grupo15.recuperarte.api_catedra.model.EventType;

public class AccelerometerData implements IRegistrable {
    private static final int ACC_X_VAL = 0;
    private static final int ACC_Y_VAL = 1;
    private static final int ACC_Z_VAL = 2;

    public float xVal, yVal, zVal, maxVal;

    public AccelerometerData(SensorEvent evt, float maxVal) {
        this.xVal = evt.values[ACC_X_VAL];
        this.yVal = evt.values[ACC_Y_VAL];
        this.zVal = evt.values[ACC_Z_VAL];
        this.maxVal = maxVal;
    }

    public float metersWalked(long seconds) {
        return this.metersWalked(xVal, seconds) + this.metersWalked(yVal, seconds);
    }

    private float metersWalked(float acc, long seconds) {
        /* dado que la aceleracion puede ser negativa */
        acc = Math.abs(acc);

        /* velocidad = aceleracion * seg */
        final float vel = acc * seconds;

        /* espacio recorrido = velocidad * seg + aceleracion * seg^2 */
        return vel * seconds + (acc) * seconds;
    }

    public boolean isRunning() {
        float acc = Math.abs(this.xVal);
        if ( acc > this.maxVal/2 ) return true;

        acc = Math.abs(this.yVal);
        if ( acc > this.maxVal/2 ) return true;

        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("X: %fm/s^2\nY: %fm/s^2\nZ: %fm/s^2\n", xVal, yVal, zVal);
    }

    @Override public EventType type() { return EventType.ACCELEROMETER; }
    @Override public String description() { return this.toString(); }
}
