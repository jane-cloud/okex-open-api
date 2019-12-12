package com.okcoin.commons.okex.open.api.bean.account.param;

import java.math.BigDecimal;

public class Transfer {

    private String currency;

    public String getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    private String amount;

    private String from;

    private String to;

    private String sub_account;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    private String instrument_id;

    private String to_instrument_id;


    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo_instrument_id() {
        return to_instrument_id;
    }

    public void setTo_instrument_id(String to_instrument_id) {
        this.to_instrument_id = to_instrument_id;
    }



    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }



    public String getSub_account() {
        return sub_account;
    }

    public void setSub_account(String sub_account) {
        this.sub_account = sub_account;
    }


}
