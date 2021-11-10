package com.grupo15.recuperarte.presenter;

import android.hardware.SensorManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.background.MainBackground;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.IMain;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataService;

public class MainPresenter implements IMain.Presenter {
    private MainBackground background;

    @NonNull
    private final IMain.View view;

    public MainPresenter(@NonNull IMain.View view) { this.view = view; }

    @Override
    public void runBackground(SensorManager sm, RunDataDAO dao) {
        final IApiClient api = Conf.getInstance().apiClient();
        this.background = new MainBackground(sm, api, dao);
        new Thread(this.background).start();
    }

    @Override
    public void terminateBackground() {
        this.background.terminate();
    }
}
