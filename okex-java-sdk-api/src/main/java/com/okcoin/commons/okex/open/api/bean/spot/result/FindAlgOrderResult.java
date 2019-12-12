package com.okcoin.commons.okex.open.api.bean.spot.result;

public class FindAlgOrderResult {
    /**
     * 	指定的币对
     */
    private String instrument_id;
    /**
     * 	1：止盈止损 2：跟踪委托 3:冰山委托 4:时间加权
     */
    private String order_type;
    /**
     * 委托时间
     */
    private String timestamp;
    /**
     * 委托失效时间
     */
    private String rejected_at;
    /**
     * 	委托单ID
     */
    private String algo_id;
    /**
     * 订单状态1，待生效 2，已生效 3，已撤销 （4 部分生效 5 暂停生效）
     */
    private String status;
    /**
     * 订单类型
     */
    private String type;
    /**
     * 委托数量,填写值1\<=X\<=1000000的整数
     */
    private String size;
    /**
     * 	触发价格，填写值0\<X
     */
    private String trigger_price;
    /**
     * 	委托价格，填写值0\<X\<=1000000
     */
    private String algo_price;
    /**
     * 	实际成交数量
     */
    private String real_amount;
    /**
     * 	回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
     */
    private String callback_rate;
    /**
     * 	委托深度，填写值0\<=X\<=0.01（1%）
     */
    private String algo_variance;
    /**
     * 	单笔均值，填写2-500的整数（永续2-500的整数）
     */
    private String avg_amount;
    /**
     * 价格限制 ，填写值0\<X\<=1000000
     */
    private String limit_price;
    /**
     * 	已成交量
     */
    private String deal_value;
    /**
     * 扫单范围，填写值0.005（0.5%）\<=X\<=0.01（1%）
     */
    private String sweep_range;
    /**
     * 	扫单比例，填写值 0.01\<=X\<=1
     */
    private String sweep_ratio;
    /**
     * 单笔上限，填写值10\<=X\<=2000（永续2-500的整数）
     */
    private String single_limit;
    /**
     * 	委托间隔，填写值5\<=X\<=120
     */
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

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
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
