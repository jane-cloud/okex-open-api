package com.okcoin.commons.okex.open.api.bean.spot.result;

public class MarginBorrowOrderDto {
    private Long borrow_id;
    private String instrument_id;
    private String currency;
    private String timestamp;
    private String amount;
    private String interest;

    @Override
    public String toString() {
        return "MarginBorrowOrderDto{" +
                "borrow_id=" + borrow_id +
                ", instrument_id='" + instrument_id + '\'' +
                ", currency='" + currency + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", amount='" + amount + '\'' +
                ", interest='" + interest + '\'' +
                ", returned_amount='" + returned_amount + '\'' +
                ", paid_interest='" + paid_interest + '\'' +
                ", last_interest_time='" + last_interest_time + '\'' +
                ", force_repay_time='" + force_repay_time + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    private String returned_amount;
    private String paid_interest;
    private String last_interest_time;

    public String getForce_repay_time() {
        return force_repay_time;
    }

    public void setForce_repay_time(String force_repay_time) {
        this.force_repay_time = force_repay_time;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    private String force_repay_time;
    private String rate;


    public Long getBorrow_id() {
        return this.borrow_id;
    }

    public void setBorrow_id(final Long borrow_id) {
        this.borrow_id = borrow_id;
    }

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }


    public String getLast_interest_time() {
        return this.last_interest_time;
    }

    public void setLast_interest_time(final String last_interest_time) {
        this.last_interest_time = last_interest_time;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getInterest() {
        return this.interest;
    }

    public void setInterest(final String interest) {
        this.interest = interest;
    }


    public String getReturned_amount() {
        return this.returned_amount;
    }

    public void setReturned_amount(final String returned_amount) {
        this.returned_amount = returned_amount;
    }


    public String getPaid_interest() {
        return this.paid_interest;
    }

    public void setPaid_interest(final String paid_interest) {
        this.paid_interest = paid_interest;
    }


}
