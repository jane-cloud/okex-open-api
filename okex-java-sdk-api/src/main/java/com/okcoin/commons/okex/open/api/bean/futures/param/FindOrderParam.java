package com.okcoin.commons.okex.open.api.bean.futures.param;

public class FindOrderParam {
    private String instrument_id;
    private String order_type;
    private String status;
    private String algo_ids;
    private String before;
    private String after;
    private String limit;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlgo_ids() {
        return algo_ids;
    }

    public void setAlgo_ids(String algo_ids) {
        this.algo_ids = algo_ids;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
