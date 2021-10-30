package com.grupo15.recuperarte.persistence;

import android.provider.BaseColumns;

public class RunDataContract {
    private RunDataContract() {}

    public static class RunDataEntry implements BaseColumns {
        public static final String TABLE = "run_data";
        public static final String COL_METERS = "meters";
        public static final String COL_RUN = "is_running";
        public static final String COL_DT = "dtime";
    }
}
