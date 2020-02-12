package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * The latest data on the futures contract product  <br/>
 * Created by Tony Tian on 2018/2/26 13;16. <br/>
 */
public class Ticker {
    /**
     * The id of the futures contract
     */
    private String instrument_id;
    /**
     * Buy first price
     */
    private String best_bid;
    /**
     * Sell first price
     */
    private String best_ask;
    /**
     * Highest price
     */
    private String high_24h;
    /**
     * Lowest price
     */
    private String low_24h;
    /**
     * Latest price
     */
    private String last;
    /**
     * Volume (recent 24 hours)
     */
    private String volume_24h;
    /**
     * timestamp
     */
    private String timestamp;

    public String getInstrument_id() { return instrument_id; }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public String getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(String best_bid) {
        this.best_bid = best_bid;
    }

    public String getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(String best_ask) {
        this.best_ask = best_ask;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
