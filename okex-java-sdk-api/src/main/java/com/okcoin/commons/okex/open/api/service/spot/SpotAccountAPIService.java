package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;

import java.util.List;
import java.util.Map;

/**
 * 币币资产相关接口
 */
public interface SpotAccountAPIService {

    /**
     * 系统时间
     *
     * @return
     */
    ServerTimeDto time();

    /**
     * 挖矿相关数据
     *
     * @return
     */
    Map<String, Object> getMiningData();


    /**
     * 账户资产列表
     *
     * @return
     */
    List<Account> getAccounts();

    /**
     * 币币账单流水
     * @param currency
     * @param before
     * @param after
     * @param limit
     * @param type
     * @param
     * @return
     */
    JSONArray getLedgersByCurrency(String currency, String before, String after, String limit, String type);

    /**
     * 单币资产
     *
     * @param currency
     * @return
     */
    Account getAccountByCurrency(final String currency);
}
