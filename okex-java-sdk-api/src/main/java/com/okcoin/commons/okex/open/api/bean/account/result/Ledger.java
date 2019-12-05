package com.okcoin.commons.okex.open.api.bean.account.result;

import java.math.BigDecimal;

public class Ledger {

    private String ledger_id;

    private String currency;

    private String balance;

    private String amount;

    private String fee;

    private String typeName;

    private String timestamp;


    public String getLedger_id() {
        return ledger_id;
    }

    public void setLedger_id(String ledger_id) {
        this.ledger_id = ledger_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "Ledger{" +
                "ledger_id='" + ledger_id + '\'' +
                ", currency='" + currency + '\'' +
                ", balance='" + balance + '\'' +
                ", amount='" + amount + '\'' +
                ", fee='" + fee + '\'' +
                ", typeName='" + typeName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}