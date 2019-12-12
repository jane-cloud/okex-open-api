package com.okcoin.commons.okex.open.api.bean.option.param;

import java.util.List;

public class CancelOrders {
    private List<String> order_ids;
    private List<String> client_oids;

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    private String underlying;


    public List<String> getOrder_ids() {
        return order_ids;
    }

    public void setOrder_ids(List<String> order_ids) {
        this.order_ids = order_ids;
    }

    public List<String> getClient_oids() {
        return client_oids;
    }

    public void setClient_oids(List<String> client_oids) {
        this.client_oids = client_oids;
    }
}
