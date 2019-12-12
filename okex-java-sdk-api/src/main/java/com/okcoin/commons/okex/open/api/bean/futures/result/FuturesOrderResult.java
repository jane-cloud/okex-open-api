package com.okcoin.commons.okex.open.api.bean.futures.result;

public class FuturesOrderResult {
    private String result;
    private String instrument_id;
    private String order_type;
    private String algo_id;
    private String error_code;
    private String error_message;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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

    public String getAlgo_id() {
        return algo_id;
    }

    public void setAlgo_id(String algo_id) {
        this.algo_id = algo_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
