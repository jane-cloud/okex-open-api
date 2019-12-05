package com.okcoin.commons.okex.open.api.bean.spot.param;

public class PlaceOrderParam {
    /**
     * 客户端下单 标示id 非必填
     */
    private String client_oid;
    /**
     * 币对如 etc_eth
     */
    private String instrument_id;
    /**
     * 买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 限价单 limit 市价单 market
     */
    private String type;
    /**
     * 交易数量
     */
    private String size;
    /**
     * 限价单使用 价格
     */
    private String price;
    /**
     * 市价单使用 价格
     */
    private String notional;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    private String order_type;


    /**
     * 1币币交易 2杠杆交易
     */
    private String margin_trading;

    public String getClient_oid() {
        return this.client_oid;
    }

    public void setClient_oid(final String client_oid) {
        this.client_oid = client_oid;
    }


    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getSide() {
        return this.side;
    }

    public void setSide(final String side) {
        this.side = side;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }


    public String getNotional() {
        return this.notional;
    }

    public void setNotional(final String notional) {
        this.notional = notional;
    }


    public String getMargin_trading() {
        return margin_trading;
    }

    public void setMargin_trading(String margin_trading) {
        this.margin_trading = margin_trading;
    }
}
