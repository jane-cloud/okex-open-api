package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Futures market api tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:54
 */
public class FuturesMarketAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(FuturesMarketAPITests.class);

    private FuturesMarketAPIService marketAPIService;

    @Before
    public void before() {
        config = config();
        marketAPIService = new FuturesMarketAPIServiceImpl(config);
    }

    /**
     * 公共——获取合约信息
     * 获取可用合约的列表，查询各合约的交易限制和价格步长等信息。
     * 限速规则：20次/2s （根据ip限速）
     * GET /api/futures/v3/instruments
     */
    @Test
    public void testGetInstruments() {
        List<Instruments> instruments = marketAPIService.getInstruments();
        toResultString(LOG, "Instruments", instruments);
    }


    /**
     * 获取深度数据
     * GET /api/futures/v3/instruments/<instrument_id>/book
     * 获取合约的深度列表。这个请求不支持分页，一个请求返回整个深度列表。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentBook() {
        Book book = marketAPIService.getInstrumentBook("BTC-USD-200327", "","");
        toResultString(LOG, "Instrument-Book", book);
    }


    /**
     * 获取某个ticker信息
     * GET /api/futures/v3/instruments/<instrument_id>/ticker
     * 获取合约的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentTicker() {
        Ticker ticker = marketAPIService.getInstrumentTicker("LTC-USD-200327");
        toResultString(LOG, "Instrument-Ticker", ticker);
    }

    /**
     * 获取全部ticker信息
     * GET /api/futures/v3/instruments/ticker
     * 获取平台全部合约的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * 限速规则：20次/2s （根据ip限速）
     */
    @Test
    public void testGetAllInstrumentTicker() {
        List<Ticker> tickers = marketAPIService.getAllInstrumentTicker();
        toResultString(LOG, "Instrument-Ticker", tickers);
    }

    /**
     * 获取成交数据
     * GET /api/futures/v3/instruments/<instrument_id>/trades
     * 获取合约最新的300条成交列表。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentTrades() {
        List<Trades> trades = marketAPIService.getInstrumentTrades("BTC-USD-200327", "", "", "");
        toResultString(LOG, "Instrument-Trades", trades);
    }

    /**
     * 获取K线数据
     * GET /api/futures/v3/instruments/<instrument-id>/candles
     * start和end同时传，既包含start的K线，也包含end的K线
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentCandles() {
//        String start = "2019-07-15T03:10:00.000Z";
//        String end = "2019-07-15T03:15:00.000Z";
        JSONArray array = marketAPIService.getInstrumentCandles("BTC-USD-200327",
                                                                "",
                                                                "",
                                                                "60");
        toResultString(LOG, "Instrument-Candles", array);
    }

    /**
     *  获取指数信息
     *  GET/api/futures/v3/instruments/<instrument_id>/index
     *  获取币种指数。此接口为公共接口，不需要身份验证。
     *  限速规则：20次/2s （根据ip限速）
     */
    @Test
    public void testGetInstrumentIndex() {
        Index index = marketAPIService.getInstrumentIndex("BTC-USD-200327");
        toResultString(LOG, "Instrument-Book", index);
    }

    /**
     * 获取法币汇率
     * 获取法币汇率。此接口为公共接口，不需要身份验证。
     * GET /api/futures/v3/rate
     * 限速规则：20次/2s （根据ip限速）
     */
    @Test
    public void testExchangeRate() {
        ExchangeRate exchangeRate = marketAPIService.getExchangeRate();
        toResultString(LOG, "ExchangeRate", exchangeRate);
    }

    /**
     * 获取预估交割价
     * GET /api/futures/v3/instruments/<instrument_id>/estimated_price
     * 获取合约预估交割价。交割预估价只有交割前一小时才有返回值。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentEstimatedPrice() {
        EstimatedPrice estimatedPrice = marketAPIService.getInstrumentEstimatedPrice("BTC-USD-200327");
        toResultString(LOG, "Instrument-Estimated-Price", estimatedPrice);
    }

    /**
     * 获取平台总持仓量
     * GET /api/futures/v3/instruments/<instrument_id>/open_interest
     * 获取合约整个平台的总持仓量
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentHolds() {
        Holds holds = marketAPIService.getInstrumentHolds("BTC-USD-200327");
        toResultString(LOG, "Instrument-Holds", holds);
    }

    /**
     *  获取当前限价
     *  GET /api/futures/v3/instruments/<instrument_id>/price_limit
     *  获取合约当前可开仓的最高买价和最低卖价
     *  限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetInstrumentPriceLimit() {
        PriceLimit priceLimit = marketAPIService.getInstrumentPriceLimit("BTC-USD-200327");
        toResultString(LOG, "Instrument-Price-Limit", priceLimit);
    }

    /**
     * 获取强平单
     * GET /api/futures/v3/instruments/<instrument_id>/liquidation
     * 获取合约强平单，此接口为公共接口，不需要身份验证
     * 限速规则：20次/2s
     */
    @Test
    public void testGetInstrumentLiquidation() {
        List<Liquidation> liquidations = marketAPIService.getInstrumentLiquidation("BTC-USD-200327", "1", "", "", "");
        toResultString(LOG, "Instrument-Liquidation", liquidations);
    }

    /**
     * 获取标记价格
     * GET/api/futures/v3/instruments/<instrument_id>/mark_price
     * 为了防止个别用户恶意操控市场导致合约价格波动剧烈，我们根据现货指数和合理基差设定标记价格。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetMarkPrice() {
        JSONObject jsonObject = marketAPIService.getMarkPrice("BTC-USD-200327");
        toResultString(LOG, "MarkPrice", jsonObject);
    }


}
