package com.okcoin.commons.okex.open.api.bean.option.param;

public class AmentDate {
    private String order_id;
    private String new_size;
    private String client_oid;
    private String request_id;

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    private String new_price;

    public String getNew_size() {
        return new_size;
    }

    public void setNew_size(String new_size) {
        this.new_size = new_size;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }



    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
