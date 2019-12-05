package com.okcoin.commons.okex.open.api.bean.spot.result;

public class OrderAlgoResult {
    /**
     * 调用接口返回结果
     */
    private String result;
    /**
     * 合约ID，如BTC-USD-SWAP
     */
    private String instrument_id;
    /**
     * 	1：止盈止损 2：跟踪委托 3:冰山委托 4:时间加权
     */
    private String order_type;
    /**
     * 	订单ID，下单失败时，此字段值为-1
     */
    private String algo_id;
    /**
     * 错误码，下单成功时为0，下单失败时会显示相应错误码
     */
    private String error_code;
    /**
     * 错误信息，下单成功时为空，下单失败时会显示错误信息
     */
    private String error_message;
    /**
     * 	撤销指定的委托单ID
     */
    private String algo_ids[];

    public String[] getAlgo_ids() {
        return algo_ids;
    }

    public void setAlgo_ids(String[] algo_ids) {
        this.algo_ids = algo_ids;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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

    public String getAlgo_id() {
        return algo_id;
    }

    public void setAlgo_id(String algo_id) {
        this.algo_id = algo_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

}
