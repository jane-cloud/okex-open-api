package com.okcoin.commons.okex.open.api.bean.spot.param;

public class SpotMarginLeverage {
    private  String leverage;

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    @Override
    public String toString() {
        return "SpotMarginLeverage{" +
                "leverage='" + leverage + '\'' +
                '}';
    }
}
