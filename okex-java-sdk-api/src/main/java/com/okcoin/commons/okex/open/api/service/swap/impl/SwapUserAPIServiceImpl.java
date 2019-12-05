package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

public class SwapUserAPIServiceImpl implements SwapUserAPIServive {
    private APIClient client;
    private SwapUserAPI api;

    public SwapUserAPIServiceImpl() {
    }

    public SwapUserAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapUserAPI.class);
    }

    /**
     * 获取所有合约持仓信息
     * @return
     */
    @Override
    public String getAllPosition() {
        return client.executeSync(api.getAllPosition());
    }

    /**
     * 获取单个合约持仓信息
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getPosition(String instrument_id) {
        return client.executeSync(api.getPosition(instrument_id));
    }

    /**
     * 获取所有币种合约的账户信息
     *
     * @return
     */
    @Override
    public String getAccounts() {
        return client.executeSync(api.getAccounts());
    }

    /**
     * 获取某个币种合约的账户信息
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String selectAccount(String instrumentId) {
        return client.executeSync(api.selectAccount(instrumentId));
    }

    /**
     * 获取某个合约的用户配置
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String selectContractSettings(String instrumentId) {
        return client.executeSync(api.selectContractSettings(instrumentId));
    }

    /**
     * 设定某个合约的杠杆
     *
     * @param instrumentId
     * @param levelRateParam
     * @return
     */
    @Override
    public String updateLevelRate(String instrumentId, LevelRateParam levelRateParam) {
        System.out.println("instrumentId"+instrumentId);
        return client.executeSync(api.updateLevelRate(instrumentId, JsonUtils.convertObject(levelRateParam, LevelRateParam.class)));
        //return client.executeSync(api.updateLevelRate(instrumentId, levelRateParam));
    }

    /**
     * 获取所有订单列表
     *
     * @param instrumentId
     * @param state
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String selectOrders(String instrumentId, String state, String before, String after, String limit) {
        return client.executeSync(api.selectOrders(instrumentId, state, before, after, limit));
    }

    /**
     * 通过订单orderId获取单个订单信息
     *
     * @param instrumentId
     * @param orderId
     * @return
     */
    @Override
    public String selectOrder(String instrumentId, String orderId) {
        return client.executeSync(api.selectOrder(instrumentId, orderId));
    }

    /**
     * 通过订单clientOid获取单个订单信息
     * @param instrumentId
     * @param clientOid
     * @return
     */
    @Override
    public String selectOrderByClientOid(String instrumentId, String clientOid) {
        return client.executeSync(api.selectOrderByClientOid(instrumentId, clientOid));
    }

    /**
     * 获取最近的成交明细列表
     *
     * @param instrumentId
     * @param orderId
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String selectDealDetail(String instrumentId, String orderId, String before, String after, String limit) {
        return client.executeSync(api.selectDealDetail(instrumentId, orderId, before, after, limit));
    }

    /**
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。
     * 流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。
     *
     * @param instrumentId
     * @param before
     * @param after
     * @param limit
     * @param type
     * @return
     */
    @Override
    public String getLedger(String instrumentId, String before, String after, String limit,String type) {
        return client.executeSync(api.getLedger(instrumentId, before, after, limit,type));
    }

    /**
     * 获取合约挂单冻结数量
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getHolds(String instrumentId) {
        return client.executeSync(api.getHolds(instrumentId));
    }

    /**
     * 获取账户手续费费率
     * @return
     */
    @Override
    public String getTradeFee() {
        return client.executeSync(api.getTradeFee());
    }
}
