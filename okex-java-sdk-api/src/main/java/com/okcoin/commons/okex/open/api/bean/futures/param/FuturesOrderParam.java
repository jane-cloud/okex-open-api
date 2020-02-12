package com.okcoin.commons.okex.open.api.bean.futures.param;

public class FuturesOrderParam {
    //公共参数
    private String instrument_id;
    private String type;
    private String order_type;
    private String size;
    //止盈止损
    private String trigger_price;
    private String algo_price;
    //跟踪委托
    private String callback_rate;
    //冰山委托
    private String algo_variance;
    private String avg_amount;
    private String price_limit;
    //时间加权委托
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
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
