package com.grupo15.recuperarte.sensor;

import android.hardware.SensorEvent;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.api_catedra.api.IRegistrable;
import com.grupo15.recuperarte.api_catedra.model.EventType;

public class AccelerometerData implements IRegistrable {
    private static final int ACC_X_VAL = 0;
    private static final int ACC_Y_VAL = 1;
    private static final int ACC_Z_VAL = 2;

    public float xAcc, yAcc, zAcc, maxVal;
    /* velocities */
    public float xVel, yVel, zVel;
    /* Position */
    public float xPos, yPos, zPos;

    public AccelerometerData(SensorEvent evt, float maxVal) {
        this.xAcc = evt.values[ACC_X_VAL];
        this.yAcc = evt.values[ACC_Y_VAL];
        this.zAcc = evt.values[ACC_Z_VAL];
        this.maxVal = maxVal;
    }

    public float metersWalked(AccelerometerData newPoint, float seconds) {
        float xAcc = newPoint.xAcc - this.xAcc;
        float yAcc = newPoint.yAcc - this.yAcc;
        float zAcc = newPoint.zAcc - this.zAcc;
        /* Integro la aceleracion para obtener velocidad */
        xVel += xAcc*seconds;
        yVel += yAcc*seconds;
        zVel += zAcc*seconds;

        float oldXPos = xPos, oldYPos = yPos, oldZPos = zPos;
        /* Integro velocidad para obtener posicion */
        xPos += xVel*seconds;
        yPos += yVel*seconds;
        zPos += zVel*seconds;

        /* La diferencia de posicion da la cantidad caminada */
        return Math.abs(xPos - oldXPos) + Math.abs(yPos - oldYPos) + Math.abs(zPos - oldZPos);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("X: %fm/s^2\nY: %fm/s^2\nZ: %fm/s^2\n", xAcc, yAcc, zAcc);
    }

    @Override public EventType type() { return EventType.ACCELEROMETER; }
    @Override public String description() { return this.toString(); }
}
