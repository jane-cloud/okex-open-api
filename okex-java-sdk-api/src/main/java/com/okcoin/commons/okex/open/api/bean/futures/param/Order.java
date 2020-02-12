package com.okcoin.commons.okex.open.api.bean.futures.param;

/**
 * New Order
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 15:38
 */
public class Order {
    /**
     * The id of the futures, eg: BTC-USD-180629
     */
    protected String instrument_id;

    /**
     * You setting order id.(optional)
     */
    private String client_oid;
    /**
     * The execution type {@link com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum}
     */
    private String type;

    /**
     * The order amount: Maximum 1 million
     */
    private String size;
    /**
     * Match best counter party price (BBO)? 0: No 1: Yes   If yes, the 'price' field is ignored
     */
    private String match_price;

    private String order_type;
    /**
     * The order price: Maximum 1 million
     */
    private String price;

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }



    public String getInstrument_id() {
        return instrument_id;
    }

    public void setinstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }


    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }


}
