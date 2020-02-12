package com.okcoin.commons.okex.open.api.bean.spot.param;

import java.util.List;

public class OrderParamDto {
    private String instrument_id;
    private List<String> order_ids;
    private List<String> client_oids;

    public void setOrder_ids(List<String> order_ids) {
        this.order_ids = order_ids;
    }



    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public List<String> getOrder_ids() {
        return order_ids;
    }
    public List<String> getClient_oids() {
        return client_oids;
    }

    public void setClient_oids(List<String> client_oids) {
        this.client_oids = client_oids;
    }



}
