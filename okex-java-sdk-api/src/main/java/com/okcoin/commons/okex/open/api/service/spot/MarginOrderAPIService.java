package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;

import java.util.List;
import java.util.Map;

/**
 * 杠杆订单相关接口
 */
public interface MarginOrderAPIService {
    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    OrderResult addOrder(PlaceOrderParam order);

    /**
     * 批量下单
     *
     * @param order
     * @return
     */
    Map<String, List<OrderResult>> addOrders(List<PlaceOrderParam> order);

    /**
     * 取消指定的订单 delete协议
     *
     * @param order_id
     */
    OrderResult cancleOrderByOrderId(final PlaceOrderParam order, String order_id);

    /**
     * 取消指定的订单 post协议
     *
     * @param order_id
     */
    OrderResult cancleOrdersByOrderId(final PlaceOrderParam order, String order_id);
    OrderResult cancleOrdersByClientOid(final PlaceOrderParam order, String client_oid);

    /**
     * 批量取消订单 delete协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, JSONObject> cancleOrders(final List<OrderParamDto> cancleOrders);

    /**
     * 批量取消订单 post协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, Object> cancleOrders_post(final List<OrderParamDto> cancleOrders);

    /**
     * 查询订单
     *
     * @param instrument_id
     * @param order_id
     * @return
     */
    OrderInfo getOrderByProductIdAndOrderId(String instrument_id, String order_id);
    OrderInfo getOrderByClientOid(String instrument_id, String client_oid);

    /**
     * 订单列表
     *
     * @param instrument_id
     * @param state
     * @param after
     * @param before
     * @param limit
     * @return
     */
    List<OrderInfo> getOrders(String instrument_id, String state, String after, String before, String limit);

    /**
     * /* 订单列表
     *
     * @param before
     * @param after
     * @param limit
     * @return
     */
    List<OrderInfo> getPendingOrders(String before, String after, String limit, String instrument_id);

    /**
     * 账单列表
     *
     * @param order_id
     * @param instrument_id
     * @param after
     * @param before
     * @param limit
     * @return
     */
    List<Fills> getFills(String order_id, String instrument_id, String after, String before, String limit);
}
