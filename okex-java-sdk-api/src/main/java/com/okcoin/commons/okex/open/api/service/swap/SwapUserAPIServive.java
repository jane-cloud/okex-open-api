package com.okcoin.commons.okex.open.api.service.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;

public interface SwapUserAPIServive {
    /**
     * 获取所有合约持仓信息
     *
     * @param
     * @return
     */
    //所有合约持仓信息
    //JSONObject getPositions();
    String getPositions();


    /**
     * 获取单个合约持仓信息
     *
     * @param instrument_id
     * @return
     */
    String getPosition(String instrument_id);

    /**
     * 获取所有币种合约的账户信息
     *
     * @return
     */
    String getAccounts();

    /**
     * 获取某个币种合约的账户信息
     *
     * @param instrument_id
     * @return
     */
    String selectAccount(String instrument_id);

    /**
     * 获取某个合约的用户配置
     *
     * @param instrument_id
     * @return
     */
    String selectContractSettings(String instrument_id);

    /**
     * 设定某个合约的杠杆
     *
     * @return*/
    String updateLevelRate(String instrument_id, LevelRateParam levelRateParam);


    /**
     * 获取所有订单列表
     *
     * @param instrument_id
     * @param state
     * @param before
     * @param after
     * @param limit
     * @return
     */
    String selectOrders(String instrument_id, String state, String before, String after, String limit);

    /**
     * 通过订单id获取单个订单信息
     *
     * @param instrument_id
     * @param order_id
     * @return
     */
    String selectOrderByOrderId(String instrument_id, String order_id);
    String selectOrderByClientOid(String instrument_id, String client_oid);

    /**
     * 获取最近的成交明细列表
     * @param instrument_id
     * @param order_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    String selectDealDetail(String instrument_id, String order_id, String before, String after, String limit);

    /**
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。
     * 流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。
     *
     * @param instrument_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    String getLedger(String instrument_id, String before, String after, String limit,String type);

    /**
     * 获取合约挂单冻结数量
     *
     * @param instrument_id
     * @return
     */
    String getHolds(String instrument_id);

    //当前账户交易手续等级的费率
    String getTradeFee();
}
