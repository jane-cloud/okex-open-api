package com.okcoin.commons.okex.open.api.bean.spot.result;

public class Fills {

    // 账单 id
    private Long ledger_id;
    // 币种 id
    private String instrument_id;
    // 价格
    private String price;
    // 数量
    private String size;
    // 订单 id
    private Long order_id;
    // 创建时间
    private String timestamp;
    private String created_at;
    // 流动方向
    private String liquidity;
    private String exec_type;
    // 手续费
    private String fee;
    // buy、sell
    private String side;
    private String currency;

    public Long getLedger_id() {
        return ledger_id;
    }

    public void setLedger_id(Long ledger_id) {
        this.ledger_id = ledger_id;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(String liquidity) {
        this.liquidity = liquidity;
    }

    public String getExec_type() {
        return exec_type;
    }

    public void setExec_type(String exec_type) {
        this.exec_type = exec_type;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
