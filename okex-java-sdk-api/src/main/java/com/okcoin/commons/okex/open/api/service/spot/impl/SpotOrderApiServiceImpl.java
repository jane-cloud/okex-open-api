package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.*;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;

import java.util.List;
import java.util.Map;

/**
 * 币币订单相关接口
 **/
public class SpotOrderApiServiceImpl implements SpotOrderAPIServive {
    private final APIClient client;
    private final SpotOrderAPI spotOrderAPI;

    public SpotOrderApiServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.spotOrderAPI = this.client.createService(SpotOrderAPI.class);
    }

    @Override
    public OrderResult addOrder(final PlaceOrderParam order) {
        return this.client.executeSync(this.spotOrderAPI.addOrder(order));
    }

    @Override
    public Map<String, List<OrderResult>> addOrders(final List<PlaceOrderParam> order) {
        return this.client.executeSync(this.spotOrderAPI.addOrders(order));
    }

    @Override
    public OrderResult cancleOrderByOrderId(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.spotOrderAPI.cancleOrderByOrderId(order_id, order));
    }

    @Override
    public OrderResult cancleOrderByOrderId_post(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.spotOrderAPI.cancleOrderByOrderId_1(order_id, order));
    }

    @Override
    public Map<String, Object> batchCancleOrders_2(List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.spotOrderAPI.batchCancleOrders_2(cancleOrders));
    }

    @Override
    public Map<String, Object> batch_orderCle(List<OrderParamDto> orderParamDto) {
        return this.client.executeSync(this.spotOrderAPI.batch_orderCle(orderParamDto));
    }

    @Override
    public OrderResult cancleOrderByClientOid(PlaceOrderParam order, String client_oid) {
        return this.client.executeSync(this.spotOrderAPI.cancleOrderByOrderId(client_oid, order));
    }

    @Override
    public Map<String, BatchOrdersResult> cancleOrders(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.spotOrderAPI.batchCancleOrders(cancleOrders));
    }

    @Override
    public Map<String, Object> cancleOrders_post(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.spotOrderAPI.batchCancleOrders_1(cancleOrders));
    }



    @Override
    public OrderInfo getOrderByOrderId(final String instrument_id, final String order_id) {
        return this.client.executeSync(this.spotOrderAPI.getOrderByOrderId(order_id, instrument_id));
    }

    @Override
    public OrderInfo getOrderByClientOid(String instrument_id, String client_oid) {
        return this.client.executeSync(this.spotOrderAPI.getOrderByClientOid(client_oid,instrument_id));
    }

    @Override
    public List<OrderInfo> getOrders(final String instrument_id, final String state, final String after, final String before, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getOrders(instrument_id, state, after, before, limit));
    }

    @Override
    public List<PendingOrdersInfo> getPendingOrders(final String before, final String after, final String limit, final String instrument_id) {
        return this.client.executeSync(this.spotOrderAPI.getPendingOrders(before, after, limit, instrument_id));
    }

    @Override
    public List<Fills> getFills(final String order_id, final String instrument_id, final String before, final String after, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getFills(order_id, instrument_id, before, after, limit));
    }

    @Override
    public OrderAlgoResult addorder_algo(OrderAlgoParam order) {
        return this.client.executeSync(this.spotOrderAPI.addorder_algo(order));
    }

    @Override
    public OrderAlgoResult cancelOrder_algo(OrderAlgoParam order) {
        return this.client.executeSync(this.spotOrderAPI.cancelOrder_algo(order));
    }

    @Override
    public String getAlgoOrder(final String instrument_id, final String order_type, final String status, final String algo_id, final String before, final String after, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getAlgoOrder(instrument_id,order_type,status,algo_id,before,after,limit));
    }


}
