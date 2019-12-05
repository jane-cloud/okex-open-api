package com.okcoin.commons.okex.open.api.bean.spot.param;

public class FindAlgOrders {
    /**
     * 	指定的币对
     */
    private String instrument_id;
    /**
     * 	1：止盈止损 2：跟踪委托 3:冰山委托 4:时间加权
     */
    private String order_type;
    /**
     * 【status和algo_id必填且只能填其一】订单状态(1，待生效 2，已生效 3，已撤销 4 部分生效
     * 5 暂停生效6委托失败）【只有冰山和加权有4、5状态】
     */
    private String status;
    /**
     * 【status和algo_id必填且只能填其一】查询指定的委托单ID
     */
    private String algo_id;
    /**
     * 	请求此id之后(更新的数据)的分页内容
     */
    private String before;
    /**
     * 	请求此id之前(更旧的数据)的分页内容
     */
    private String after;
    /**
     * 分页返回的结果集数量，默认为100，最大为100
     */
    private String limit;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlgo_id() {
        return algo_id;
    }

    public void setAlgo_id(String algo_id) {
        this.algo_id = algo_id;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
