package com.okcoin.commons.okex.open.api.bean.futures.result;

public class CancelFuturesOrdeResult {
    private String instrument_id;
    private String order_type;
    private String algo_ids;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getAlgo_ids() {
        return algo_ids;
    }

    public void setAlgo_ids(String algo_ids) {
        this.algo_ids = algo_ids;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;



}
