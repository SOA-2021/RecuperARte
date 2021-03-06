package com.grupo15.recuperarte.background;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.grupo15.recuperarte.api_catedra.api.ApiException;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.mvp.IMain;
import com.grupo15.recuperarte.network.NetworkChecker;
import com.grupo15.recuperarte.persistence.RunData;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.sensor.AccelerometerData;
import com.grupo15.recuperarte.sensor.ProximityData;


/**
 * Es el hilo background encargado de realizar las acciones de fondo de la pantalla principal
 */
public class MainBackground implements Runnable, SensorEventListener {
    /* 3 metros por segundo siginfica que esta corriendo */
    private static final float MT_THRESHOLD = 3;
    private static final long SLEEP_MS  = 1000;
    private static final long MS_TO_SEC  = 1000;

    private final IMain.View view;
    private final Handler handler;
    private final NetworkChecker network;
    private final IApiClient api;
    private final RunDataDAO dao;
    private final SensorManager sm;
    private boolean shouldContinue;
    private AccelerometerData accelerometer;
    private final float accMaxVal;
    private ProximityData proximity;
    private final boolean proximityIsBinary;
    private final long lastTime = System.currentTimeMillis();
    private float micrometers;

    public MainBackground(
            SensorManager sm,
            IApiClient api,
            RunDataDAO dao,
            NetworkChecker nc,
            Handler h,
            IMain.View view
    ) {
        this.api = api;
        this.dao = dao;
        this.network = nc;
        this.view = view;
        this.handler = h;

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
            registerOnAPI();
        } catch ( ApiException e ) {
            Log.e("[API]", e.getMessage(), e);
        }

        if ( proximity != null && proximity.val != 0 )
            handler.post(this.view::alertProximity);

        /* Persisto en la base de datos los metros recorridos */
        float metersWalked = this.micrometers /1000000;
        RunData runData = new RunData(
                metersWalked,
                metersWalked > MT_THRESHOLD
        );
        this.micrometers = 0;

        if ( metersWalked != 0 ) {
            dao.save(runData);
            Log.d("[MAIN]", String.format("Registro runData: %s", runData.toString()));
        }
    }

    private void registerOnAPI() throws ApiException {
        if ( !network.isConnected() ) {
            Log.d("[MAIN]", "No esta conectado a Internet");
            return;
        }

        if ( this.accelerometer != null ) {
            /* Registro actividad del acelerometro */
            api.register(this.accelerometer);
            Log.d(
                    "[MAIN]",
                    String.format("Registro acelerometro: %s", this.accelerometer.toString())
            );
        }

        if ( this.proximity != null ) {
            /* Registro actividad del sensor de proximidad */
            api.register(this.proximity);
            Log.d(
                "[MAIN]",
                String.format("Registro proximidad: %s", this.proximity.toString())
            );
        }
    }

    public synchronized void terminate() {
        this.shouldContinue = false;
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
        long now = System.currentTimeMillis();
        AccelerometerData previous = this.accelerometer;
        this.accelerometer = new AccelerometerData(evt, this.accMaxVal);
        if ( previous != null ) {
            this.micrometers += previous.metersWalked(this.accelerometer, (now - lastTime)/MS_TO_SEC);
        }
    }

    public synchronized void handleProximity(SensorEvent evt) {
        this.proximity = new ProximityData(evt, this.proximityIsBinary);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
