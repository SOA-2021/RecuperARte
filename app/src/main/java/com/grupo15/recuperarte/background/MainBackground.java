package com.grupo15.recuperarte.background;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.persistence.RunData;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.sensor.AccelerometerData;
import com.grupo15.recuperarte.sensor.ProximityData;

/**
 * Es el hilo background encargado de realizar las acciones de fondo de la pantalla principal
 */
public class MainBackground implements Runnable, SensorEventListener {
    private static final long SLEEP_MS  = 1000;
    private static final long SLEEP_SEC = 1;

    private final IApiClient api;
    private final RunDataDAO dao;
    private final SensorManager sm;
    private boolean shouldContinue;
    private AccelerometerData accelerometer;
    private final float accMaxVal;
    private ProximityData proximity;
    private final boolean proximityIsBinary;

    public MainBackground(SensorManager sm, IApiClient api, RunDataDAO dao) {
        this.api = api;
        this.dao = dao;

        this.sm = sm;
        Sensor acelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if ( acelerometer == null )
            throw new IllegalArgumentException("el dispositivo no posee acelerometro");
        this.accMaxVal = acelerometer.getMaximumRange();

        Sensor proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if ( proximity == null )
            throw new IllegalArgumentException("el dispositivo no posee sensor de proximidad");
        float maxProximityValue = proximity.getMaximumRange();
        this.proximityIsBinary = maxProximityValue == 1;

        sm.registerListener(this, acelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);

        this.shouldContinue = true;
    }

    @Override
    public void run() {
        try {
            while ( this.shouldContinue ) {
                loop();
                Thread.sleep(SLEEP_MS);
            }
        } catch ( InterruptedException e ) {
            Log.e("MAIN", e.getMessage(), e);
        } finally {
            this.sm.unregisterListener(this);
        }
    }

    /**
     * Main loop del hilo.
     */
    private void loop() {
        try {
            /* Registro actividad del acelerometro */
            api.register(this.accelerometer);

            /* Registro actividad del sensor de proximidad */
            api.register(this.proximity);
        } catch ( ApiException e ) {
            Log.d("REG", e.getMessage(), e);
        }

        /* Persisto en la base de datos los metros recorridos */
        RunData runData = new RunData(
                this.accelerometer.metersWalked(SLEEP_SEC),
                this.accelerometer.isRunning()
        );
        dao.save(runData);
    }

    public synchronized void terminate() {
        this.shouldContinue = false;
    }

    public synchronized boolean shouldContinue() {
        return this.shouldContinue;
    }

    @Override
    public void onSensorChanged(SensorEvent evt) {
        switch ( evt.sensor.getType() ) {
            case Sensor.TYPE_ACCELEROMETER:
                handleAccelerometer(evt);
                break;
            case Sensor.TYPE_PROXIMITY:
                handleProximity(evt);
                break;
        }
    }

    public synchronized void handleAccelerometer(SensorEvent evt) {
        this.accelerometer = new AccelerometerData(evt, this.accMaxVal);
    }

    public synchronized void handleProximity(SensorEvent evt) {
        this.proximity = new ProximityData(evt, this.proximityIsBinary);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
