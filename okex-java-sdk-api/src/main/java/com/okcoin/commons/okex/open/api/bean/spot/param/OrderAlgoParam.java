package com.okcoin.commons.okex.open.api.bean.spot.param;

public class OrderAlgoParam {
    /**
     * 币对名称
     */
    private String instrument_id;
    /**
     * 1：币币 2：杠杆
     */
    private String mode;
    /**
     * 	1：止盈止损 2：跟踪委托 3:冰山委托 4:时间加权
     */
    private String order_type;
    /**
     * 	委托总数，填写值1\<=X\<=1000000的整数
     */
    private String size;
    /**
     * 	buy or sell
     */
    private String side;

    //止盈止损参数
    /**
     * 	触发价格，填写值0\<X\<=1000000
     * 	跟踪委托参数:	激活价格 ，填写值0\<X\<=1000000
     */
    private String trigger_price;
    /**
     * 委托价格，填写值0\<X\<=1000000
     */
    private String algo_price;
    //跟踪委托参数
    /**
     * 	回调幅度，填写值0.001（0.1%）\<=X\<=0.05（5%）
     */
    private String callback_rate;
    //冰山委托参数
    /**
     * 委托深度，填写值0.0001(0.01%)\<=X\<=0.01（1%）
     */
    private String algo_variance;
    /**
     * 单笔均值，填写2-1000的整数（永续2-500的整数）
     */
    private String avg_amount;

    //时间加权参数
    /**
     *扫单范围，填写值0.005（0.5%）\<=X\<=0.01（1%）
     */
    private String sweep_range;
    /**
     *扫单比例，填写值 0.01\<=X\<=1
     */
    private String sweep_ratio;
    /**
     * 	单笔上限，填写值10\<=X\<=2000（永续2-500的整数）
     */
    private String single_limit;
    /**
     * 价格限制，填写值0\<X\<=1000000
     */
    private String limit_price;
    /**
     * 	委托间隔，填写值5\<=X\<=120
     */
    private String time_interval;

    /**
     * 撤销指定的委托单ID
     */
    private String algo_ids[];


    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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

    public String getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(String limit_price) {
        this.limit_price = limit_price;
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }

    public String[] getAlgo_ids() {
        return algo_ids;
    }

    public void setAlgo_ids(String[] algo_ids) {
        this.algo_ids = algo_ids;
    }
}
