package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OptionMarketAPI {
    //获取期权合约的深度数据
    @GET("/api/option/v3/instruments/{instrument_id}/book")
    Call<JSONObject> getDepthData(@Path("instrument_id") String instrument_id, @Query("size") String size);

    //获取期权合约成交记录
    @GET("/api/option/v3/instruments/{instrument_id}/trades")
    Call<JSONArray> getTradeList(@Path("instrument_id") String instrument_id,
                                 @Query("before") String before,
                                 @Query("after") String after,
                                 @Query("limit") String limit);

    //获取可用合约的列表
    @GET("/api/option/v3/instruments/{underlying}")
    Call<JSONArray> getInstruments(@Path("underlying") String underlying,
                                   @Query("delivery") String delivery,
                                   @Query("instrument_id") String instrument_id);


    //获取同一标的下所有期权合约详细定价
    @GET("/api/option/v3/instruments/{underlying}/summary")
    Call<JSONArray> getAllSummary(@Path("underlying") String underlying,
                                   @Query("delivery") String delivery);

    //获取某个期权合约的详细定价
    @GET("/api/option/v3/instruments/{underlying}/summary/{instrument_id}")
    Call<JSONObject> getDetailPrice(@Path("underlying") String underlying,
                              @Path("instrument_id") String instrument_id);

    //获取K线数据
    @GET("/api/option/v3/instruments/{instrument_id}/candles")
    Call<JSONArray> getCandles(@Path("instrument_id") String instrument_id);

    //获取某个期权合约的ticker信息
    @GET("/api/option/v3/instruments/{instrument_id}/ticker")
    Call<JSONObject> getTicker(@Path("instrument_id") String instrument_id);

    //获取期权合约已支持的标的的指数列表
    @GET("/api/option/v3/underlying")
    Call<JSONArray> getUnderlying();


}
