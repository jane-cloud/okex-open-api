package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapMarketAPIService;

public class SwapMarketAPIServiceImpl implements SwapMarketAPIService {
    private APIClient client;
    private SwapMarketAPI api;

    public SwapMarketAPIServiceImpl() {
    }

    public SwapMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapMarketAPI.class);
    }

    /**
     * 获取可用合约的列表。
     *
     * @return
     */
    @Override
    public String getContractsApi() {
        return client.executeSync(api.getContractsApi());
    }

    /**
     * 获取合约的深度列表。
     *
     * @param instrument_id
     * @param size
     * @return
     */
    @Override
    public String getDepthApi(String instrument_id, String size,String depth) {
        return client.executeSync(api.getDepthApi(instrument_id, size,depth));
    }

    /**
     * 获取平台全部合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @return
     */
    @Override
    public String getTickersApi() {
        return client.executeSync(api.getTickersApi());
    }

    /**
     * 获取合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getTickerApi(String instrument_id) {
        return client.executeSync(api.getTickerApi(instrument_id));
    }

    /**
     * 获取合约的成交记录。
     *
     * @param instrument_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String getTradesApi(String instrument_id, String before, String after, String limit) {
        return client.executeSync(api.getTradesApi(instrument_id, before, after, limit));
    }

    /**
     * 获取合约的K线数据。
     *
     * @param instrument_id
     * @param start
     * @param end
     * @param granularity
     * @return
     */
    @Override
    public String getCandlesApi(String instrument_id, String start, String end, String granularity) {
        return client.executeSync(api.getCandlesApi(instrument_id, start, end, granularity));
    }

    /**
     * 获取币种指数。
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getIndexApi(String instrument_id) {
        return client.executeSync(api.getIndexApi(instrument_id));
    }

    /**
     * 获取法币汇率。
     *
     * @return
     */
    @Override
    public String getRateApi() {
        return client.executeSync(api.getRateApi());
    }

    /**
     * 获取合约整个平台的总持仓量。
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getOpenInterestApi(String instrument_id) {
        return client.executeSync(api.getOpenInterestApi(instrument_id));
    }

    /**
     * 获取合约当前开仓的最高买价和最低卖价。
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getPriceLimitApi(String instrument_id) {
        return client.executeSync(api.getPriceLimitApi(instrument_id));
    }

    /**
     * 获取合约爆仓单。
     *
     * @param instrument_id
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @Override
    public String getLiquidationApi(String instrument_id, String status, String from, String to, String limit) {
        return client.executeSync(api.getLiquidationApi(instrument_id, status, from, to, limit));
    }

    /**
     * 获取合约下一次的结算时间。
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getFundingTimeApi(String instrument_id) {
        return client.executeSync(api.getFundingTimeApi(instrument_id));
    }

    /**
     * 获取合约历史资金费率
     *
     * @param instrument_id
     * @param limit
     * @return
     */
    @Override
    public String getHistoricalFundingRateApi(String instrument_id,String limit) {
        return client.executeSync(api.getHistoricalFundingRateApi(instrument_id,limit));
    }

    /**
     * 获取合约标记价格
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getMarkPriceApi(String instrument_id) {
        return client.executeSync(api.getMarkPriceApi(instrument_id));
    }
}
