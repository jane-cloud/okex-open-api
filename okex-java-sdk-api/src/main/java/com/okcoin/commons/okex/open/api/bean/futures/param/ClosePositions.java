package com.okcoin.commons.okex.open.api.bean.futures.param;

public class ClosePositions {

    private String  instrument_id;

    private String  direction;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
