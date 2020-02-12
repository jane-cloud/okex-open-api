package com.okcoin.commons.okex.open.api.bean.swap.param;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

public class LevelRateParam extends JSONObject {

    /**
     * 1.LONG
     * 2.SHORT
     * 3.全仓杠杆
     */
    private String side;
    private String leverage;


    public LevelRateParam(String side, String levelRate) {
        this.side = side;
        this.leverage = levelRate;
    }

    public LevelRateParam() {
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }
}


