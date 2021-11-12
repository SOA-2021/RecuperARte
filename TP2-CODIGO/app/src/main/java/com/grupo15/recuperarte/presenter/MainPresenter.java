package com.grupo15.recuperarte.presenter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.background.MainBackground;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.IMain;
import com.grupo15.recuperarte.network.NetworkChecker;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataService;
import com.grupo15.recuperarte.sensor.ProximityData;

public class MainPresenter implements IMain.Presenter {
    private static final long MS_SLEEP = 2000; /* dos segundos */
    private MainBackground background;
    private final NetworkChecker network;
    private final RunDataService service;
    private final Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    private boolean shouldContinue;

    @NonNull
    private final IMain.View view;

    public MainPresenter(@NonNull IMain.View view, NetworkChecker network, RunDataService service) {
        this.view = view;
        this.network = network;
        this.service = service;
        this.shouldContinue = true;
    }

    @Override
    public void runBackground(SensorManager sm, RunDataDAO dao) {
        if ( !network.isConnected() ) {
            handler.post(() -> this.view.onError("No esta conectado a Internet"));
            return;
        }

        final IApiClient api = Conf.getInstance().apiClient();
        this.background = new MainBackground(sm, api, dao, network, handler, view);
        new Thread(this.background).start();

        new Thread(() -> {
            while ( shouldContinue ) {
                RunDataService.MtPerDay meters = service.getMtToday();
                handler.post(()->this.view.updateMeters(meters));
                try {
                    Thread.sleep(MS_SLEEP);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    @Override
    public synchronized void terminateBackground() {
        this.background.terminate();
        this.shouldContinue = false;
    }
}
