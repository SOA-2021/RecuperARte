package com.grupo15.recuperarte.mvp;

import android.hardware.SensorManager;

import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataService;

public interface IMain {
    interface Presenter {
        void runBackground(SensorManager sm, RunDataDAO dao);
        void terminateBackground();
    }
    interface View {
        void updateMeters(RunDataService.MtPerDay mt);
        void alertProximity();
        void onError(String error);
    }
}
