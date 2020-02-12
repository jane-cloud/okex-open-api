package com.okcoin.commons.okex.open.api.service.swap;


public interface SwapMarketAPIService {
    /**
     * 获取可用合约的列表。
     *
     * @return
     */
    String getContractsApi();

    /**
     * 获取合约的深度列表。
     *
     * @param instrument_id
     * @param size
     * @return
     */
    String getDepthApi(String instrument_id, String size,String depth);

    /**
     * 获取平台全部合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @return
     */
    String getTickersApi();

    /**
     * 获取合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @param instrument_id
     * @return
     */
    String getTickerApi(String instrument_id);

    /**
     * 获取合约的成交记录。
     * @param instrument_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    String getTradesApi(String instrument_id, String before, String after, String limit);

    /**
     * 获取合约的K线数据。
     * @param instrument_id
     * @param start
     * @param end
     * @param granularity
     * @return
     */
    String getCandlesApi(String instrument_id, String start, String end, String granularity);

    /**
     * 获取币种指数。
     * @param instrument_id
     * @return
     */
    String getIndexApi(String instrument_id);

    /**
     * 获取法币汇率。
     * @return
     */
    String getRateApi();

    /**
     * 获取合约整个平台的总持仓量。
     * @param instrument_id
     * @return
     */
    String getOpenInterestApi(String instrument_id);

    /**
     * 获取合约当前开仓的最高买价和最低卖价。
     * @param instrument_id
     * @return
     */
    String getPriceLimitApi(String instrument_id);

    /**
     * 获取合约爆仓单。
     * @param instrument_id
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    String getLiquidationApi(String instrument_id, String status, String from, String to, String limit);

    /**
     * 获取合约下一次的结算时间。
     * @param instrument_id
     * @return
     */
    String getFundingTimeApi(String instrument_id);

    /**
     * 获取合约历史资金费率
     * @param instrument_id
     * @param limit
     * @return
     */
    String getHistoricalFundingRateApi(String instrument_id,String limit);

    /**
     * 获取合约标记价格
     * @return
     */
    String getMarkPriceApi(String instrument_id);
}
