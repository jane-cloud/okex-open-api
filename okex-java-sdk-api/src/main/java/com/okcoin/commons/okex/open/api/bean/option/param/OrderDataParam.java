package com.okcoin.commons.okex.open.api.bean.option.param;

import java.util.List;

public class OrderDataParam {
    public List<OrderParam> getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(List<OrderParam> orderdata) {
        this.orderdata = orderdata;
    }

    private List<OrderParam> orderdata;

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    private String underlying;

    @Override
    public String toString() {
        return "OrderDataParam{" +
                "orderdata=" + orderdata +
                '}';
    }
}
