package com.grupo15.recuperarte.mvp;

import com.grupo15.recuperarte.persistence.RunDataService;

import java.util.List;

public interface IMtPerDay {
    interface Presenter {
        void getMetersPerDay(RunDataService service);
    }
    interface View {
        void drawTable(List<RunDataService.MtPerDay> mtPerDay);
    }
}
