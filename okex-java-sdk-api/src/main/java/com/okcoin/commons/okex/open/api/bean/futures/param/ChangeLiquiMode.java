package com.okcoin.commons.okex.open.api.bean.futures.param;

public class ChangeLiquiMode {

    private String currency;

    private  String liqui_mode;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLiqui_mode() {
        return liqui_mode;
    }

    public void setLiqui_mode(String liqui_mode) {
        this.liqui_mode = liqui_mode;
    }
}
