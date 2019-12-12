package com.okcoin.commons.okex.open.api.bean.option.param;

public class OrderParam {
    private String client_oid;
    private String instrument_id;
    private String side;
    private String order_type;
    private String price;
    private String size;

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMatch_price() {
        return match_price;
    }

    public void setMatch_price(String match_price) {
        this.match_price = match_price;
    }

    private String match_price;

    @Override
    public String toString() {
        return "OrderParam{" +
                "client_oid='" + client_oid + '\'' +
                ", instrument_id='" + instrument_id + '\'' +
                ", side='" + side + '\'' +
                ", order_type='" + order_type + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", match_price='" + match_price + '\'' +
                '}';
    }
}
