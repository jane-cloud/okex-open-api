package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.option.OptionMarketAPIService;

public class OptionMarketAPIServiceImpl implements OptionMarketAPIService {
    private APIClient client;
    private OptionMarketAPI api;

    public OptionMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(OptionMarketAPI.class);
    }


    @Override
    public JSONObject getDepthData(String instrument_id, String size) {
        return this.client.executeSync(this.api.getDepthData(instrument_id,size));
    }
    @Override
    public JSONArray getInstruments(String underlying, String delivery, String instrument_id) {
        return this.client.executeSync(this.api.getInstruments(underlying,delivery,instrument_id));
    }

    @Override
    public JSONArray getTradeList(String instrument_id, String before, String after, String limit) {
        return this.client.executeSync(this.api.getTradeList(instrument_id,before,after,limit));
    }

    @Override
    public JSONArray getAllSummary(String underlying, String delivery) {
        return this.client.executeSync(this.api.getAllSummary(underlying,delivery));
    }

    @Override
    public JSONObject getDetailPrice(String underlying, String instrument_id) {
        return this.client.executeSync(this.api.getDetailPrice(underlying, instrument_id));
    }

    @Override
    public JSONArray getCandles(String instrument_id) {
        return this.client.executeSync(this.api.getCandles(instrument_id));
    }

    @Override
    public JSONObject getTicker(String instrument_id) {
        return this.client.executeSync(this.api.getTicker(instrument_id));
    }

    @Override
    public JSONArray getUnderlying() {
        return this.client.executeSync(this.api.getUnderlying());
    }


}
