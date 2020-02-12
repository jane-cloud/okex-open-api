package com.okcoin.commons.okex.open.api.service.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface OptionMarketAPIService {

    JSONObject getDepthData(String instrument_id,String size);

    JSONArray getTradeList(String instrument_id, String before, String after, String limit);

    JSONArray getInstruments(String underlying,String delivery,String instrument_id);

    JSONArray getAllSummary(String underlying,String delivery);

    JSONObject getDetailPrice(String underlying,String instrument_id);

    JSONArray getCandles(String instrument_id);

    JSONObject getTicker(String instrument_id);

    JSONArray getUnderlying();
}
