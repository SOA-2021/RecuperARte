package com.grupo15.recuperarte.sensor;

import android.hardware.SensorEvent;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.api_catedra.api.IRegistrable;
import com.grupo15.recuperarte.api_catedra.model.EventType;

public class ProximityData implements IRegistrable {
    private static final int PROX_VAL = 0;
    public final float val;
    public final boolean isBinary;

    public ProximityData(SensorEvent evt, boolean isBinary) {
        this.val = evt.values[PROX_VAL];
        this.isBinary = isBinary;
    }

    @Override public EventType type() { return EventType.PROXIMITY; }
    @Override public String description() { return this.toString(); }

    @Override
    @NonNull
    public String toString() {
        if ( this.isBinary )
            return val == 0 ? "Smartphone cerca" : "Smartphone lejos";
        else
            return String.format("%f cm\n", val);
    }
}
