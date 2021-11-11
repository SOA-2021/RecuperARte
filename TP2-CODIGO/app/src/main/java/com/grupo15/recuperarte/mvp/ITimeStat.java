package com.grupo15.recuperarte.mvp;

import com.grupo15.recuperarte.persistence.RunDataService;

import java.util.List;

public interface ITimeStat {
    interface Presenter {
        void getTimeRunning(RunDataService service);
    }
    interface View {
        void drawTable(List<RunDataService.TimeRunning> timeRunning);
    }
}
