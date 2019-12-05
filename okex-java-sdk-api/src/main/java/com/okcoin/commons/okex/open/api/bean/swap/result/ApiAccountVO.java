package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiAccountVO {

    private String equity;
    private String fixed_balance;
    private String total_avail_balance;
    private String margin;
    private String realized_pnl;
    private String unrealized_pnl;
    private String margin_ratio;
    private String instrument_id;
    private String margin_frozen;
    private String timestamp;
    private String margin_mode;
    private String maint_margin_ratio;

    public ApiAccountVO(String equity, String fixed_balance, String total_avail_balance, String margin, String realized_pnl, String unrealized_pnl, String margin_ratio, String instrument_id, String margin_frozen, String timestamp, String margin_mode, String maint_margin_ratio) {
        this.equity = equity;
        this.fixed_balance = fixed_balance;
        this.total_avail_balance = total_avail_balance;
        this.margin = margin;
        this.realized_pnl = realized_pnl;
        this.unrealized_pnl = unrealized_pnl;
        this.margin_ratio = margin_ratio;
        this.instrument_id = instrument_id;
        this.margin_frozen = margin_frozen;
        this.timestamp = timestamp;
        this.margin_mode = margin_mode;
        this.maint_margin_ratio = maint_margin_ratio;
    }

    public ApiAccountVO() {
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public String getFixed_balance() {
        return fixed_balance;
    }

    public void setFixed_balance(String fixed_balance) {
        this.fixed_balance = fixed_balance;
    }

    public String getTotal_avail_balance() {
        return total_avail_balance;
    }

    public void setTotal_avail_balance(String total_avail_balance) {
        this.total_avail_balance = total_avail_balance;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getRealized_pnl() {
        return realized_pnl;
    }

    public void setRealized_pnl(String realized_pnl) {
        this.realized_pnl = realized_pnl;
    }

    public String getUnrealized_pnl() {
        return unrealized_pnl;
    }

    public void setUnrealized_pnl(String unrealized_pnl) {
        this.unrealized_pnl = unrealized_pnl;
    }

    public String getMargin_ratio() {
        return margin_ratio;
    }

    public void setMargin_ratio(String margin_ratio) {
        this.margin_ratio = margin_ratio;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getMargin_frozen() {
        return margin_frozen;
    }

    public void setMargin_frozen(String margin_frozen) {
        this.margin_frozen = margin_frozen;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMargin_mode() {
        return margin_mode;
    }

    public void setMargin_mode(String margin_mode) {
        this.margin_mode = margin_mode;
    }

    public String getMaint_margin_ratio() {
        return maint_margin_ratio;
    }

    public void setMaint_margin_ratio(String maint_margin_ratio) {
        this.maint_margin_ratio = maint_margin_ratio;
    }

    @Override
    public String toString() {
        return "ApiAccountVO{" +
                "equity='" + equity + '\'' +
                ", fixed_balance='" + fixed_balance + '\'' +
                ", total_avail_balance='" + total_avail_balance + '\'' +
                ", margin='" + margin + '\'' +
                ", realized_pnl='" + realized_pnl + '\'' +
                ", unrealized_pnl='" + unrealized_pnl + '\'' +
                ", margin_ratio='" + margin_ratio + '\'' +
                ", instrument_id='" + instrument_id + '\'' +
                ", margin_frozen='" + margin_frozen + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", margin_mode='" + margin_mode + '\'' +
                ", maint_margin_ratio='" + maint_margin_ratio + '\'' +
                '}';
    }
}
