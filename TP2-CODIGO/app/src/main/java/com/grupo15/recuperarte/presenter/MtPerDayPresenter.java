package com.grupo15.recuperarte.presenter;

import androidx.annotation.NonNull;

import com.grupo15.recuperarte.mvp.IMtPerDay;
import com.grupo15.recuperarte.network.NetworkChecker;
import com.grupo15.recuperarte.persistence.RunDataService;

import java.util.List;

public class MtPerDayPresenter implements IMtPerDay.Presenter {
    @NonNull
    private final IMtPerDay.View view;

    public MtPerDayPresenter(@NonNull IMtPerDay.View view) {
        this.view = view;
    }

    @Override
    public void getMetersPerDay(RunDataService service) {
        List<RunDataService.MtPerDay> mtPerDay = service.listMtPerDay();
        this.view.drawTable(mtPerDay);
    }
}
