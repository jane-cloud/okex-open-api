package com.okcoin.commons.okex.open.api.bean.spot.result;

public class OrderResult {

    private boolean result;
    private Long order_id;
    private String client_oid;
    private String error_code;
    private String error_message;


    public String getClient_oid() {
        return this.client_oid;
    }

    public void setClient_oid(final String client_oid) {
        this.client_oid = client_oid;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public Long getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(final Long order_id) {
        this.order_id = order_id;
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
