package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiCancelOrderVO {

    private String order_id;
    private String result;
    private String client_oid;

    public ApiCancelOrderVO(String order_id, String result, String client_oid) {
        this.order_id = order_id;
        this.result = result;
        this.client_oid = client_oid;
    }

    public ApiCancelOrderVO() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    @Override
    public String toString() {
        return "ApiCancelOrderVO{" +
                "order_id='" + order_id + '\'' +
                ", result='" + result + '\'' +
                ", client_oid='" + client_oid + '\'' +
                '}';
    }
}
