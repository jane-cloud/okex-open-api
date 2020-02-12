package com.okcoin.commons.okex.open.api.service.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;

public interface SwapTradeAPIService {
    /**
     * 下单
     * @param ppOrder
     * @return
     */
    Object order(PpOrder ppOrder);

    /**
     * 批量下单
     * @param ppOrders
     * @return
     */
    String orders(PpOrders ppOrders);

    /**
     * 获取订单信息
     * @return
     */
    String getOrders();

    /**
     * 撤单
     * @param instrument_id
     * @param order_id
     * @return
     */
    String cancelOrderByOrderId(String instrument_id, String order_id);

    String cancelOrderByClientOid(String instrument_id, String client_oid);

    /**
     * 批量撤单
     * @param instrumentId
     * @param ppCancelOrderVO
     * @return
     */
    String cancelOrders(String instrumentId, PpCancelOrderVO ppCancelOrderVO);

    /**
     * 策略委托下单
     * @param swapOrderParam
     * @return
     */
    String swapOrderAlgo(SwapOrderParam swapOrderParam);

    /**
     * 策略委托撤单
     * @param cancelOrderAlgo
     * @return
     */
    String cancelOrderAlgo(CancelOrderAlgo cancelOrderAlgo);

    /**
     * 查看策略委托订单
     * @param instrument_id
     * @param order_type
     * @param status
     * @param algo_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    String getSwapOrders(String instrument_id,
                         String order_type,
                         String status,
                         String algo_id,
                         String before,
                         String after,
                         String limit);
}
