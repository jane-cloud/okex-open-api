package com.okcoin.commons.okex.open.api.bean.spot.result;

public class Ticker {

    //private String product_id;
    private String last;
   // private String bid;
    //private String ask;
    private String open_24h;
    private String high_24h;
    private String low_24h;
    private String base_volume_24h;
    private String timestamp;
    private String quote_volume_24h;
    private String best_ask;
    private String best_bid;
    private String instrument_id;
    private String best_ask_size;
    private String best_bid_size;

    public String getBest_ask_size() {
        return best_ask_size;
    }

    public void setBest_ask_size(String best_ask_size) {
        this.best_ask_size = best_ask_size;
    }

    public String getBest_bid_size() {
        return best_bid_size;
    }

    public void setBest_bid_size(String best_bid_size) {
        this.best_bid_size = best_bid_size;
    }

    public String getLast_qty() {
        return last_qty;
    }

    public void setLast_qty(String last_qty) {
        this.last_qty = last_qty;
    }

    private String last_qty;


    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }


    public String getLast() {
        return this.last;
    }

    public void setLast(final String last) {
        this.last = last;
    }



    public String getOpen_24h() {
        return this.open_24h;
    }

    public void setOpen_24h(final String open_24h) {
        this.open_24h = open_24h;
    }

    public String getHigh_24h() {
        return this.high_24h;
    }

    public void setHigh_24h(final String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return this.low_24h;
    }

    public void setLow_24h(final String low_24h) {
        this.low_24h = low_24h;
    }

    public String getBase_volume_24h() {
        return this.base_volume_24h;
    }

    public void setBase_volume_24h(final String base_volume_24h) {
        this.base_volume_24h = base_volume_24h;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getQuote_volume_24h() {
        return this.quote_volume_24h;
    }

    public void setQuote_volume_24h(final String quote_volume_24h) {
        this.quote_volume_24h = quote_volume_24h;
    }

    public String getBest_ask() {
        return this.best_ask;
    }

    public void setBest_ask(final String best_ask) {
        this.best_ask = best_ask;
    }

    public String getBest_bid() {
        return this.best_bid;
    }

    public void setBest_bid(final String best_bid) {
        this.best_bid = best_bid;
    }
}
