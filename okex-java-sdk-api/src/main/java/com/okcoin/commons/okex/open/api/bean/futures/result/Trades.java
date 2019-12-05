package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Get the latest transaction log information.  <br/>
 * Created by Tony Tian on 2018/2/26 13:30. <br/>
 */
public class Trades {
    /**
     * The id of the futures contract
     */
    private String trade_id;
    /**
     * Transaction type
     */
    private String side;
    /**
     * Transaction price
     */
    private String price;
    /**
     * Transaction amount
     */
    private String qty;
    /**
     * Transaction date
     */
    private String timestamp;



    public String getTrade_id() { return trade_id; }

    public void setTrade_id(String trade_id) { this.trade_id = trade_id; }

    public String getSide() { return side; }

    public void setSide(String side) { this.side = side; }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "Trades{" +
                "trade_id='" + trade_id + '\'' +
                ", side='" + side + '\'' +
                ", price='" + price + '\'' +
                ", qty='" + qty + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
