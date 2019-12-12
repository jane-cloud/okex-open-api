package com.okcoin.commons.okex.open.api.bean.futures.param;

import java.util.List;

public class CancelFuturesOrder {
    private String instrument_id;
    private List<String> algo_ids;
    private String order_type;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public List<String> getAlgo_ids() {
        return algo_ids;
    }

    public void setAlgo_ids(List<String> algo_ids) {
        this.algo_ids = algo_ids;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
