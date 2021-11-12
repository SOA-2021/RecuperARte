package com.grupo15.recuperarte.presenter;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.mvp.ITimeStat;
import com.grupo15.recuperarte.network.NetworkChecker;
import com.grupo15.recuperarte.persistence.RunDataService;

import java.util.List;

public class TimeRunningPresenter implements ITimeStat.Presenter {
    @NonNull
    private final ITimeStat.View view;

    public TimeRunningPresenter(@NonNull ITimeStat.View view) {
        this.view = view;
    }

    @Override
    public void getTimeRunning(RunDataService service) {
        List<RunDataService.TimeRunning> timeRunning = service.listTimeRunning();
        this.view.drawTable(timeRunning);
    }
}
