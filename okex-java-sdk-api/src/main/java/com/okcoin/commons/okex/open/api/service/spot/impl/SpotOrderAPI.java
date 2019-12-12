package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.FindAlgOrders;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderAlgoParam;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface SpotOrderAPI {

    /**
     * 单个下单
     *
     * @param order
     * @return
     */
    @POST("api/spot/v3/orders")
    Call<OrderResult> addOrder(@Body PlaceOrderParam order);

    /**
     * 批量下单
     *
     * @param param
     * @return
     */
    @POST("api/spot/v3/batch_orders")
    Call<Map<String, List<OrderResult>>> addOrders(@Body List<PlaceOrderParam> param);

    /**
     * 指定订单撤单 delete协议
     *
     * @param orderId
     * @param order
     * @return
     */
    @HTTP(method = "DELETE", path = "api/spot/v3/orders/{order_id}", hasBody = true)
    Call<OrderResult> cancleOrderByOrderId(@Path("order_id") String orderId,
                                           @Body PlaceOrderParam order);

    /**
     * 指定订单撤单 post协议
     *通过order_id进行撤单
     * @param orderId
     * @param order
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_orders/{order_id}", hasBody = true)
    Call<OrderResult> cancleOrderByOrderId_1(@Path("order_id") String orderId,
                                             @Body PlaceOrderParam order);
    /**
     * 指定订单撤单 post协议
     *通过client_oid进行撤单
     * @param client_oid
     * @param order
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_orders/{client_oid}", hasBody = true)
    Call<OrderResult> cancleOrderByClientOid(@Path("client_oid") String client_oid,
                                             @Body PlaceOrderParam order);

    /**
     * 批量撤单 delete协议
     *
     * @param cancleOrders
     * @return
     */
    @HTTP(method = "DELETE", path = "api/spot/v3/batch_orders", hasBody = true)
    Call<Map<String, BatchOrdersResult>> batchCancleOrders(@Body List<OrderParamDto> cancleOrders);

    /**
     * 批量撤单 post协议
     *根据order_id进行撤单
     * @param cancleOrders
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_batch_orders", hasBody = true)
    Call<Map<String, Object>> batchCancleOrders_1(@Body List<OrderParamDto> cancleOrders);

    /**
     * 批量撤单 post协议
     *根据client_oid进行撤单
     * @param cancleOrders
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_batch_orders", hasBody = true)
    Call<Map<String, Object>> batchCancleOrders_2(@Body List<OrderParamDto> cancleOrders);

    /**
     * 查询指定订单数据
     *
     * @param orderId
     * @param instrument_id
     * @return
     */
    @GET("api/spot/v3/orders/{order_id}")
    Call<OrderInfo> getOrderByOrderId(@Path("order_id") String orderId,
                                      @Query("instrument_id") String instrument_id);

    @GET("api/spot/v3/orders/{client_oid}")
    Call<OrderInfo> getOrderByClientOid(@Path("client_oid") String client_oid,
                                        @Query("instrument_id") String instrument_id);

    /**
     * 分页查询订单
     *
     * @param product
     * @param status
     * @param after
     * @param before
     * @param limit
     * @return
     */
    @GET("api/spot/v3/orders")
    Call<List<OrderInfo>> getOrders(@Query("instrument_id") String product,
                                    @Query("state") String status,
                                    @Query("after") String after,
                                    @Query("before") String before,
                                    @Query("limit") String limit);

    /**
     * 分页查询挂单
     *
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @GET("api/spot/v3/orders_pending")
    Call<List<OrderInfo>> getPendingOrders(@Query("before") String before,
                                           @Query("after") String after,
                                           @Query("limit") String limit,
                                           @Query("instrument_id") String instrument_id);

    /**
     * 分页查询账单
     *
     * @param order_id
     * @param product
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @GET("api/spot/v3/fills")
    Call<List<Fills>> getFills(@Query("order_id") String order_id,
                               @Query("instrument_id") String product,
                               @Query("before") String before,
                               @Query("after") String after,
                               @Query("limit") String limit);

    /**
     * 策略委托下单
     * @param order
     * @return
     */
    @POST("api/spot/v3/order_algo")
    Call<OrderAlgoResult> addorder_algo(@Body OrderAlgoParam order);

    /**
     * 策略委托撤单
     * @param order
     * @return
     */
    @POST("api/spot/v3/cancel_batch_algos")
    Call<OrderAlgoResult> cancelOrder_algo(@Body OrderAlgoParam order);

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
    @GET("/api/spot/v3/algo")
    Call <String> getAlgoOrder(@Query("instrument_id") String instrument_id,
                               @Query("order_type") String order_type,
                               @Query("status") String status,
                               @Query("algo_id") String algo_id,
                               @Query("before") String before,
                               @Query("after") String after,
                               @Query("limit") String limit);


}
