package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiFundingTimeVO {

    private String instrument_id;
    private String funding_time;
    private String funding_rate;
    private String estimated_rate;
    private String settlement_time;

    public ApiFundingTimeVO() {
    }

    public ApiFundingTimeVO(String instrument_id, String funding_time, String funding_rate, String estimated_rate, String settlement_time) {
        this.instrument_id = instrument_id;
        this.funding_time = funding_time;
        this.funding_rate = funding_rate;
        this.estimated_rate = estimated_rate;
        this.settlement_time = settlement_time;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getFunding_time() {
        return funding_time;
    }

    public void setFunding_time(String funding_time) {
        this.funding_time = funding_time;
    }

    public String getFunding_rate() {
        return funding_rate;
    }

    public void setFunding_rate(String funding_rate) {
        this.funding_rate = funding_rate;
    }

    public String getEstimated_rate() {
        return estimated_rate;
    }

    public void setEstimated_rate(String estimated_rate) {
        this.estimated_rate = estimated_rate;
    }

    public String getSettlement_time() {
        return settlement_time;
    }

    public void setSettlement_time(String settlement_time) {
        this.settlement_time = settlement_time;
    }

    @Override
    public String toString() {
        return "ApiFundingTimeVO{" +
                "instrument_id='" + instrument_id + '\'' +
                ", funding_time='" + funding_time + '\'' +
                ", funding_rate='" + funding_rate + '\'' +
                ", estimated_rate='" + estimated_rate + '\'' +
                ", settlement_time='" + settlement_time + '\'' +
                '}';
    }
}
