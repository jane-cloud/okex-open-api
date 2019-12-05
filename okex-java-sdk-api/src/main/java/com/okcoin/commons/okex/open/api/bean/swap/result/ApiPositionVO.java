package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiPositionVO {

    private String margin_mode;
    private String liquidation_price;
    private String position;
    private String avail_position;
    private String margin;
    private String avg_cost;
    private String settlement_price;
    private String instrument_id;
    private String leverage;
    private String realized_pnl;
    private String side;
    private String timestamp;
    private String maint_margin_ratio;
    private String settled_pnl;
    private String last;
    private String unrealized_pnl;

    public ApiPositionVO(String margin_mode, String liquidation_price, String position, String avail_position, String margin, String avg_cost, String settlement_price, String instrument_id, String leverage, String realized_pnl, String side, String timestamp, String maint_margin_ratio, String settled_pnl, String last, String unrealized_pnl) {
        this.margin_mode = margin_mode;
        this.liquidation_price = liquidation_price;
        this.position = position;
        this.avail_position = avail_position;
        this.margin = margin;
        this.avg_cost = avg_cost;
        this.settlement_price = settlement_price;
        this.instrument_id = instrument_id;
        this.leverage = leverage;
        this.realized_pnl = realized_pnl;
        this.side = side;
        this.timestamp = timestamp;
        this.maint_margin_ratio = maint_margin_ratio;
        this.settled_pnl = settled_pnl;
        this.last = last;
        this.unrealized_pnl = unrealized_pnl;
    }

    public ApiPositionVO() {
    }

    public String getMargin_mode() {
        return margin_mode;
    }

    public void setMargin_mode(String margin_mode) {
        this.margin_mode = margin_mode;
    }

    public String getLiquidation_price() {
        return liquidation_price;
    }

    public void setLiquidation_price(String liquidation_price) {
        this.liquidation_price = liquidation_price;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvail_position() {
        return avail_position;
    }

    public void setAvail_position(String avail_position) {
        this.avail_position = avail_position;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(String avg_cost) {
        this.avg_cost = avg_cost;
    }

    public String getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(String settlement_price) {
        this.settlement_price = settlement_price;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public String getRealized_pnl() {
        return realized_pnl;
    }

    public void setRealized_pnl(String realized_pnl) {
        this.realized_pnl = realized_pnl;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMaint_margin_ratio() {
        return maint_margin_ratio;
    }

    public void setMaint_margin_ratio(String maint_margin_ratio) {
        this.maint_margin_ratio = maint_margin_ratio;
    }

    public String getSettled_pnl() {
        return settled_pnl;
    }

    public void setSettled_pnl(String settled_pnl) {
        this.settled_pnl = settled_pnl;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "ApiPositionVO{" +
                "margin_mode='" + margin_mode + '\'' +
                ", liquidation_price='" + liquidation_price + '\'' +
                ", position='" + position + '\'' +
                ", avail_position='" + avail_position + '\'' +
                ", margin='" + margin + '\'' +
                ", avg_cost='" + avg_cost + '\'' +
                ", settlement_price='" + settlement_price + '\'' +
                ", instrument_id='" + instrument_id + '\'' +
                ", leverage='" + leverage + '\'' +
                ", realized_pnl='" + realized_pnl + '\'' +
                ", side='" + side + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", maint_margin_ratio='" + maint_margin_ratio + '\'' +
                ", settled_pnl='" + settled_pnl + '\'' +
                ", last='" + last + '\'' +
                ", unrealized_pnl='" + unrealized_pnl + '\'' +
                '}';
    }

    public String getUnrealized_pnl() {
        return unrealized_pnl;
    }

    public void setUnrealized_pnl(String unrealized_pnl) {
        this.unrealized_pnl = unrealized_pnl;
    }
}
