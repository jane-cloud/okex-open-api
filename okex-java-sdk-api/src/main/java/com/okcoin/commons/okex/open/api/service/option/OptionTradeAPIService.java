package com.okcoin.commons.okex.open.api.service.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;

import java.util.List;

public interface OptionTradeAPIService {

    JSONObject getAccount(String underlying);

    JSONObject amendOrder(String underlying, AmentDate amentDate);

    JSONObject amendBatchOrders(String underlying, AmendDateParam amendDateParam);

    JSONObject cancelOrders(String underlying,String order_id);

    JSONObject cancelOrderByClientOid(String underlying,String client_oid);

    JSONObject cancelBatchOrders(String underlying, CancelOrders cancelOrders);

    //JSONObject (String underlying, CancelOrders cancelOrders);

    JSONArray getFills(String underlying, String order_id, String instrument_id, String before, String after, String limit);

    JSONArray getLedger(String underlying);

    JSONObject getOrder(OrderParam orderParam);

    JSONObject getOrders1(OrderDataParam orderDataParam);

    JSONObject getOrderInfo(String underlying,String order_id);
    JSONObject getOrderInfoByClientOid(String underlying,String client_oid);

    JSONObject getOrderList(String underlying,String state,String instrument_id,String before,String after,String limit);

    JSONObject getPosition(String underlying,String instrument_id);

    JSONObject getTradeFee();

}
