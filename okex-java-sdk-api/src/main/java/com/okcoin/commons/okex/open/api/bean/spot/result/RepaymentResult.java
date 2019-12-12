package com.okcoin.commons.okex.open.api.bean.spot.result;


public class RepaymentResult {

    private boolean result;
    private String repayment_id;
    private String client_oid;

    public boolean isResult() {
        return this.result;
    }

    public void setResult(final boolean result) {
        this.result = result;
    }

    public String getRepayment_id() {
        return repayment_id;
    }

    public void setRepayment_id(String repayment_id) {
        this.repayment_id = repayment_id;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }
}
