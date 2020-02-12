package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Exchange Rate
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:31
 */
public class ExchangeRate {
    /**
     * legal tender pairs
     */
    private String instrument_id;
    /**
     * exchange rate
     */
    private Double rate;

    private String timestamp;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
