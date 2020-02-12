package com.okcoin.commons.okex.open.api.bean.spot.result;

public class PendingOrdersInfo {
    private String order_id;
    private String client_oid;
    private String price;
    private String notional;
    private String size;
    private String order_type;
    private String instrument_id;
    private String type;
    private String side;
    private String timestamp;
    private String filled_size;
    private String filled_notional;
    private String state;

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNotional() {
        return notional;
    }

    public void setNotional(String notional) {
        this.notional = notional;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilled_size() {
        return filled_size;
    }

    public void setFilled_size(String filled_size) {
        this.filled_size = filled_size;
    }

    public String getFilled_notional() {
        return filled_notional;
    }

    public void setFilled_notional(String filled_notional) {
        this.filled_notional = filled_notional;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
