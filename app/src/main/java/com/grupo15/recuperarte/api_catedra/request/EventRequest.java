package com.grupo15.recuperarte.api_catedra.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.grupo15.recuperarte.api_catedra.model.EventType;
import com.grupo15.recuperarte.api_catedra.api.IRegistrable;
import com.grupo15.recuperarte.global.Conf;

public class EventRequest implements IApiRequest {
    @Expose
    public static final String env = Conf.ENV;
    @Expose
    @SerializedName("type_events")
    public EventType type;
    @Expose
    public String description;

    /**
     * Crea una request de evento a partir de un objeto registrable.
     */
    public EventRequest(IRegistrable r) {
        this.type = r.type();
        this.description = r.description();
    }

    @Override
    public String toJson() { return new Gson().toJson(this); }
}
