package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

public class SwapTradeAPIServiceImpl implements SwapTradeAPIService {
    private APIClient client;
    private SwapTradeAPI api;
    private SwapTradeAPI swapTradeAPI;

    public SwapTradeAPIServiceImpl() {
    }

    public SwapTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapTradeAPI.class);
    }

    /**
     * 下单
     *
     * @param ppOrder
     * @return
     */
    @Override
    public Object order(PpOrder ppOrder) {
        System.out.println("下单参数：：：：：：");
        System.out.println(JsonUtils.convertObject(ppOrder, PpOrder.class));
        //return this.client.executeSync(this.api.order(JsonUtils.convertObject(ppOrder, PpOrder.class)));
        return this.client.executeSync(this.api.order(ppOrder));
    }

    /**
     * 批量下单
     *
     * @param ppOrders
     * @return
     */
    @Override
    public String orders(PpOrders ppOrders) {
        return this.client.executeSync(this.api.orders(JsonUtils.convertObject(ppOrders, PpOrders.class)));
    }

    @Override
    public String getOrders() {
        return null;
    }

    /**
     * 撤单根据orderid
     *
     * @param instrumentId
     * @param orderId
     * @return
     */
    @Override
    public String cancelOrder(String instrumentId, String orderId) {
        return this.client.executeSync(this.api.cancelOrder(instrumentId,orderId));
    }

    /**
     * 根据client_oid撤单
     * @param instrumentId
     * @param clientOid
     * @return
     */
    @Override
    public String cancelOrderByClientOid(String instrumentId, String clientOid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(instrumentId,clientOid));
    }

    /**
     * 批量撤单根据orderid
     *
     * @param instrumentId
     * @param ppCancelOrderVO
     * @return
     */
    @Override
    public String cancelOrders(String instrumentId, PpCancelOrderVO ppCancelOrderVO) {
        return this.client.executeSync(this.api.cancelOrders(instrumentId,JsonUtils.convertObject(ppCancelOrderVO, PpCancelOrderVO.class)));
    }

    /**
     * 批量撤单根据ClientOid
     * @param instrumentId
     * @param ppCancelOrderVO
     * @return
     */
    @Override
    public String batchCancelOrderByClientOid(String instrumentId, PpCancelOrderVO ppCancelOrderVO) {
        return this.client.executeSync(this.api.batchCancelOrderByClientOid(instrumentId,JsonUtils.convertObject(ppCancelOrderVO, PpCancelOrderVO.class)));
    }

    @Override
    public String swapOrderAlgo(SwapOrderParam swapOrderParam) {
        System.out.println("begin swapOrder-----");
        return this.client.executeSync(this.api.swapOrderAlgo(swapOrderParam));
    }

    @Override
    public String cancelOrderAlgo(CancelOrderAlgo cancelOrderAlgo) {
        System.out.println("canceling the algo order");
        return this.client.executeSync(this.api.cancelOrderAlgo(cancelOrderAlgo));
    }

    @Override
    public String getSwapOrders(String instrument_id, String order_type, String status, String algo_id, String before, String after, String limit) {
        return this.client.executeSync(this.api.getSwapOrders(instrument_id,order_type,status,algo_id,before,after,limit));
    }
}
