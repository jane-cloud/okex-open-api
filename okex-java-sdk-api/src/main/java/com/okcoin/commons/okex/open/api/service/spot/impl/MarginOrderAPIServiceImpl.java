package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.MarginOrderAPIService;

import java.util.List;
import java.util.Map;

public class MarginOrderAPIServiceImpl implements MarginOrderAPIService {
    private final APIClient client;
    private final MarginOrderAPI marginOrderAPI;

    public MarginOrderAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.marginOrderAPI = this.client.createService(MarginOrderAPI.class);
    }

    @Override
    public OrderResult addOrder(final PlaceOrderParam order) {
        return this.client.executeSync(this.marginOrderAPI.addOrder(order));
    }

    @Override
    public Map<String, List<OrderResult>> addOrders(final List<PlaceOrderParam> order) {
        return this.client.executeSync(this.marginOrderAPI.addOrders(order));
    }

    @Override
    public OrderResult cancleOrderByOrderId(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.marginOrderAPI.cancleOrdersByProductIdAndOrderId(order_id, order));
    }

    @Override
    public OrderResult cancleOrdersByOrderId(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.marginOrderAPI.cancleOrdersByOrderId(order_id, order));
    }

    @Override
     public OrderResult cancleOrdersByClientOid(final PlaceOrderParam order, final String client_oid) {
            return this.client.executeSync(this.marginOrderAPI.cancleOrdersByClientOid(client_oid, order));
     }


    @Override
    public Map<String, JSONObject> cancleOrders(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.marginOrderAPI.batchCancleOrders(cancleOrders));
    }

    @Override
    public Map<String, Object> cancleOrders_post(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.marginOrderAPI.batchCancleOrders_1(cancleOrders));
    }

    @Override
    public OrderInfo getOrderByProductIdAndOrderId(final String instrument_id, final String order_id) {
        return this.client.executeSync(this.marginOrderAPI.getOrderByProductIdAndOrderId(order_id, instrument_id));
    }

    @Override
    public OrderInfo getOrderByClientOid(String instrument_id, String client_oid) {
        return client.executeSync(this.marginOrderAPI.getOrderByClientOid(instrument_id,client_oid));
    }

    @Override
    public List<OrderInfo> getOrders(final String instrument_id, final String state, final String after, final String before, final String limit) {
        return this.client.executeSync(this.marginOrderAPI.getOrders(instrument_id, state, after, before, limit));
    }

    @Override
    public List<OrderInfo> getPendingOrders(final String before, final String after, final String limit, final String instrument_id) {
        return this.client.executeSync(this.marginOrderAPI.getPendingOrders(before, after, limit, instrument_id));
    }

    @Override
    public List<Fills> getFills(final String order_id, final String instrument_id, final String after, final String before, final String limit) {
        return this.client.executeSync(this.marginOrderAPI.getFills(order_id, instrument_id, after, before, limit));
    }
}
