package com.okcoin.commons.okex.open.api.service.account.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Account api
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/07/04 20:51
 */
public interface AccountAPI {


    @POST("/api/account/v3/transfer")
    Call<JSONObject> transfer(@Body JSONObject jsonObject);


    @POST("/api/account/v3/withdrawal")
    Call<JSONObject> withdraw(@Body JSONObject jsonObject);

    @GET("/api/account/v3/currencies")
    Call<List<Currency>> getCurrencies();

    @GET("/api/account/v3/ledger")
    Call<JSONArray> getLedger(@Query("txn_type") String type, @Query("currency") String currency,
                                 @Query("before") String before, @Query("after") String after, @Query("limit") String limit);

    @GET("/api/account/v3/wallet")
    Call<List<Wallet>> getWallet();

    @GET("/api/account/v3/wallet/{currency}")
    Call<List<Wallet>> getWallet(@Path("currency") String currency);

    @GET("/api/account/v3/deposit/address")
    Call<JSONArray> getDepositAddress(@Query("currency") String currency);

    @GET("/api/account/v3/withdrawal/fee")
    Call<List<WithdrawFee>> getWithdrawFee(@Query("currency") String currency);

    @GET("/api/account/v3/onhold")
    Call<JSONArray> getOnHold(@Query("currency") String currency);

    @POST("/api/account/v3/lock")
    Call<JSONObject> lock(@Body JSONObject jsonObject);

    @POST("/api/account/v3/unlock")
    Call<JSONObject> unlock(@Body JSONObject jsonObject);

    @GET("/api/account/v3/deposit/history")
    Call<JSONArray> getDepositHistory();

    @GET("/api/account/v3/deposit/history/{currency}")
    Call<JSONArray> getDepositHistory(@Path("currency") String currency);
    //查询所有币种提币记录
    @GET("/api/account/v3/withdrawal/history")
    Call<JSONArray> getWithdrawalHistory();
    //查看单个提币记录
    @GET("/api/account/v3/withdrawal/history/{currency}")
    Call<JSONArray> getWithdrawalHistory(@Path("currency") String currency);
}
