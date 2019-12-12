package com.okcoin.commons.okex.open.api.bean.futures.param;

/**
 * batch new order sub element <br/>
 * Created by Tony Tian on 2018/2/28 17:57. <br/>
 */
public class OrdersItem {
    /**
     * You setting order id. (optional)
     */
    private String client_oid;
    /**
     * The execution type {@link com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum}
     */
    private String type;
    /**
     * The order price: Maximum 1 million
     */
    private String price;
    /**
     * The order amount: Maximum 1 million
     */
    private String size;
    /**
     * Match best counter party price (BBO)? 0: No 1: Yes   If yes, the 'price' field is ignored
     */
    private String match_price;

    private String order_type;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
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

    @Override
    public String toString() {
        return "OrdersItem{" +
                "client_oid='" + client_oid + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", match_price='" + match_price + '\'' +
                ", order_type='" + order_type + '\'' +
                '}';
    }
}
