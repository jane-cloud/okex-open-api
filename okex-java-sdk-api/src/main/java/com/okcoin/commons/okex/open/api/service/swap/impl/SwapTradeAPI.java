package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.CancelOrderAlgo;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpOrder;
import com.okcoin.commons.okex.open.api.bean.swap.param.SwapOrderParam;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import retrofit2.Call;
import retrofit2.http.*;

public interface SwapTradeAPI {
    //下单
    @POST("/api/swap/v3/order")
    Call<Object> order(@Body PpOrder ppOrder);
    //批量下单
    @POST("/api/swap/v3/orders")
    Call<String> orders(@Body JSONObject ppOrders);
    //撤单
    @POST("/api/swap/v3/cancel_order/{instrument_id}/{order_id}")
    Call<String> cancelOrderByOrderId(@Path("instrument_id") String instrumentId, @Path("order_id") String order_id);
    @POST("/api/swap/v3/cancel_order/{instrument_id}/{client_oid}")
    Call<String> cancelOrderByClientOid(@Path("instrument_id") String instrumentId, @Path("client_oid") String client_oid);

    //批量撤单
    @POST("/api/swap/v3/cancel_batch_orders/{instrument_id}")
    Call<String> cancelOrders(@Path("instrument_id") String instrumentId, @Body JSONObject ppOrders);

    /**
     * 策略下单
     * @param swapOrderParam
     * @return
     */
    @POST("/api/swap/v3/order_algo")
    Call<String> swapOrderAlgo(@Body SwapOrderParam swapOrderParam);

    /**
     * 策略撤单
     * @param cancelOrderAlgo
     * @return
     */
    @POST("/api/swap/v3/cancel_algos")
    Call<String> cancelOrderAlgo(@Body CancelOrderAlgo cancelOrderAlgo);

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
    @GET("/api/swap/v3/order_algo/{instrument_id}")
    Call<String> getSwapOrders(@Path("instrument_id") String instrument_id,
                               @Query("order_type") String order_type,
                               @Query("status") String status,
                               @Query("algo_id") String algo_id,
                               @Query("before") String before,
                               @Query("after") String after,
                               @Query("limit") String limit);
}
