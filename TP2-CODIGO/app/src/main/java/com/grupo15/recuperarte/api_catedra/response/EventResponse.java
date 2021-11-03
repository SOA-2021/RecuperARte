package com.grupo15.recuperarte.api_catedra.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventResponse {
    @Expose
    private boolean success;
    @Expose
    private String env;

    private class EventR {
        @Expose
        @SerializedName("type_events")
        private String type;
        @Expose
        private String description;
        @Expose
        private Integer id;
        @Expose
        private Integer dni;
    }

    public boolean success() { return this.success; }
}
