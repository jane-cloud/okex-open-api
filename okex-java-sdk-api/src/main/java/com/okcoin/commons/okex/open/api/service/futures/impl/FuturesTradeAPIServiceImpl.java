package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelAll;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.CancelFuturesOrdeResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.FindFuturesOrderResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.FuturesOrderResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

import java.util.List;

/**
 * Futures trade api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:52
 */
public class FuturesTradeAPIServiceImpl implements FuturesTradeAPIService {

    private APIClient client;
    private FuturesTradeAPI api;

    public FuturesTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesTradeAPI.class);
    }

    @Override
    public JSONObject getPositions() {

        return this.client.executeSync(this.api.getPositions());
    }

    @Override
    public JSONObject getInstrumentPosition(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentPosition(instrumentId));
    }

    @Override
    public JSONObject getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    @Override
    public JSONObject getAccountsByCurrency(String underlying) {
        return this.client.executeSync(this.api.getAccountsByCurrency(underlying));
    }

    @Override
    public JSONArray getAccountsLedgerByCurrency(String underlying,String after,String before,String limit,String type) {
        return this.client.executeSync(this.api.getAccountsLedgerByCurrency(underlying,after,before,limit,type));
    }

    @Override
    public JSONObject getAccountsHoldsByInstrumentId(String instrumentId) {
        return this.client.executeSync(this.api.getAccountsHoldsByInstrumentId(instrumentId));
    }

    @Override
    public OrderResult order(Order order) {
        System.out.println(JsonUtils.convertObject(order, Order.class));
        return this.client.executeSync(this.api.order(JsonUtils.convertObject(order, Order.class)));
    }

    @Override
    public JSONObject orders(Orders orders) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", orders.getInstrument_id());
        params.put("orders_data", JsonUtils.convertList(orders.getOrders_data(), OrdersItem.class));
        System.out.println(params.toString());
        return this.client.executeSync(this.api.orders(params));
    }

    @Override
    public JSONObject cancelOrder(String instrumentId, String orderId) {
        return this.client.executeSync(this.api.cancelOrder(instrumentId, orderId));
    }

    @Override
    public JSONObject cancelOrderByClientOid(String instrumentId, String clientOid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(instrumentId, clientOid));
    }

    @Override
    public JSONObject cancelOrders(String instrumentId, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrders(instrumentId, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    @Override
    public JSONObject cancelOrdersByClientOid(String instrumentId, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrdersByClientOid(instrumentId, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    @Override
    public JSONObject getOrders(String instrument_id, String state, String after, String before, String limit) {
        return this.client.executeSync(this.api.getOrders(instrument_id, state, after, before, limit));
    }

    @Override
    public JSONObject getOrder(String instrumentId, String orderId) {
        return this.client.executeSync(this.api.getOrder(instrumentId, String.valueOf(orderId)));
    }

    @Override
    public JSONObject getOrderByClientOid(String instrumentId, String clientOid) {
        return this.client.executeSync(this.api.getOrder(instrumentId, String.valueOf(clientOid)));
    }

    @Override
    public JSONArray getFills(String instrumentId, String orderId, String from, String to, String limit) {
        return this.client.executeSync(this.api.getFills(instrumentId, orderId, from, to, limit));
    }

    @Override
    public JSONObject getInstrumentLeverRate(String underlying) {
        return this.client.executeSync(this.api.getLeverRate(underlying));
    }

    @Override
    public JSONObject changeLeverageOnFixed(String currency, String instrumentId, String direction, String leverage) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", instrumentId);
        params.put("direction", direction);
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.changeLeverageOnFixed(currency, params));
    }

    @Override
    public JSONObject changeLeverageOnCross(String underlying, String leverage) {
        JSONObject params = new JSONObject();
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.changeLeverageOnCross(underlying, params));
    }

    @Override
    public JSONObject closePositions(ClosePositions closePositions) {
        return this.client.executeSync(this.api.closePositions(JsonUtils.convertObject(closePositions,ClosePositions.class)));
    }

    @Override
    public JSONObject cancelAll(CancelAll cancelAll) {
        return this.client.executeSync(this.api.cancelAll(JsonUtils.convertObject(cancelAll,CancelAll.class)));
    }

    @Override
    public JSONObject changeMarginMode(ChangeMarginMode changeMarginMode ) {
        return this.client.executeSync(this.api.changeMarginMode(JsonUtils.convertObject(changeMarginMode,ChangeMarginMode.class )));
    }

    @Override
    public JSONObject changeLiquiMode(ChangeLiquiMode changeLiquiMode ) {
        return this.client.executeSync(this.api.changeLiquiMode(JsonUtils.convertObject(changeLiquiMode,ChangeLiquiMode.class )));
    }

    @Override
    public FuturesOrderResult futuresOrder(FuturesOrderParam futuresOrderParam) {
        System.out.println(JsonUtils.convertObject(futuresOrderParam, FuturesOrderParam.class));
        return this.client.executeSync(this.api.futuresOrder(futuresOrderParam));
    }

    @Override
    public CancelFuturesOrdeResult cancelFuturesOrder(CancelFuturesOrder cancelFuturesOrder) {
        System.out.println(JsonUtils.convertObject(cancelFuturesOrder, CancelFuturesOrder.class));
        return this.client.executeSync(this.api.cancelFuturesOrder(cancelFuturesOrder));
    }

    @Override
    public String findFuturesOrder(String instrument_id, String order_type, String status, String algo_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.findFuturesOrder(instrument_id,order_type,status,algo_id,after,before,limit));
    }
}
