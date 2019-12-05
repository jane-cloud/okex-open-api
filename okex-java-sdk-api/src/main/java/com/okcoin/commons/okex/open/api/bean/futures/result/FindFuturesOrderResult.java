package com.okcoin.commons.okex.open.api.bean.futures.result;

public class FindFuturesOrderResult {
    private String instrument_id;
    private String order_type;
    private String timestamp;
    private String rejected_at;
    private String algo_id;
    private String status;
    private String type;
    private String leverage;
    private String size;
    private String trigger_price;
    private String algo_price;
    private String real_amount;
    private String callback_rate;
    private String algo_variance;
    private String avg_amount;
    private String price_limit;
    private String deal_value;
    private String sweep_range;
    private String sweep_ratio;
    private String single_limit;
    private String time_interval;


    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRejected_at() {
        return rejected_at;
    }

    public void setRejected_at(String rejected_at) {
        this.rejected_at = rejected_at;
    }

    public String getAlgo_id() {
        return algo_id;
    }

    public void setAlgo_id(String algo_id) {
        this.algo_id = algo_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTrigger_price() {
        return trigger_price;
    }

    public void setTrigger_price(String trigger_price) {
        this.trigger_price = trigger_price;
    }

    public String getAlgo_price() {
        return algo_price;
    }

    public void setAlgo_price(String algo_price) {
        this.algo_price = algo_price;
    }

    public String getReal_amount() {
        return real_amount;
    }

    public void setReal_amount(String real_amount) {
        this.real_amount = real_amount;
    }

    public String getCallback_rate() {
        return callback_rate;
    }

    public void setCallback_rate(String callback_rate) {
        this.callback_rate = callback_rate;
    }

    public String getAlgo_variance() {
        return algo_variance;
    }

    public void setAlgo_variance(String algo_variance) {
        this.algo_variance = algo_variance;
    }

    public String getAvg_amount() {
        return avg_amount;
    }

    public void setAvg_amount(String avg_amount) {
        this.avg_amount = avg_amount;
    }

    public String getPrice_limit() {
        return price_limit;
    }

    public void setPrice_limit(String price_limit) {
        this.price_limit = price_limit;
    }

    public String getDeal_value() {
        return deal_value;
    }

    public void setDeal_value(String deal_value) {
        this.deal_value = deal_value;
    }

    public String getSweep_range() {
        return sweep_range;
    }

    public void setSweep_range(String sweep_range) {
        this.sweep_range = sweep_range;
    }

    public String getSweep_ratio() {
        return sweep_ratio;
    }

    public void setSweep_ratio(String sweep_ratio) {
        this.sweep_ratio = sweep_ratio;
    }

    public String getSingle_limit() {
        return single_limit;
    }

    public void setSingle_limit(String single_limit) {
        this.single_limit = single_limit;
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }
}
