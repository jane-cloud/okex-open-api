package com.okcoin.commons.okex.open.api.bean.futures.param;

/**
 * Created by wc.j on 2018/11/1.
 */
public class ChangeMarginMode {

    private String underlying;

    private String margin_mode;

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public String getMargin_mode() {
        return margin_mode;
    }

    public void setMargin_mode(String margin_mode) {
        this.margin_mode = margin_mode;
    }
}
