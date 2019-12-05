package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.SpotProductAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotProductAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpotProductAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotProductAPITest.class);

    private SpotProductAPIService spotProductAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.spotProductAPIService = new SpotProductAPIServiceImpl(this.config);
    }

    /**
     * 公共-获取币对信息
     * 获取交易币对的列表，查询各币对的交易限制和价格步长等信息。
     * GET /api/spot/v3/instruments
     * 限速规则：20次/2s
     */
    @Test
    public void getProducts() {
        final List<Product> products = this.spotProductAPIService.getProducts();
        this.toResultString(SpotProductAPITest.LOG, "products", products);
    }

    /**
     * 公共-获取深度数据
     * 获取币对的深度列表。这个请求不支持分页，一个请求返回整个深度列表。
     * GET /api/spot/v3/instruments/<instrument_id>/book
     * 限速规则：20次/2s
     */
    @Test
    public void bookProductsByProductId() {
        for (int i = 0; i < 1; i++) {
            final Book book = this.spotProductAPIService.bookProductsByProductId("BTC-USDT", "10", "");
            this.toResultString(SpotProductAPITest.LOG, "book", book);
            System.out.println("==========i=" + i);
            try {
                Thread.sleep(400);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 公共-获取全部ticker信息
     * 获取平台全部币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * GET /api/spot/v3/instruments/ticker
     * 限速规则：50次/2s
     */
    @Test
    public void getTickers() {
        final List<Ticker> tickers = this.spotProductAPIService.getTickers();
        this.toResultString(SpotProductAPITest.LOG, "tickers", tickers);

    }

    /**
     * 公共-获取某个ticker信息
     * 获取币对的最新成交价、买一价、卖一价和24小时交易量的快照信息。
     * GET /api/spot/v3/instruments/<instrument-id>/ticker
     * 限速规则：20次/2s
     */
    @Test
    public void getTickerByProductId() {
        final Ticker ticker = this.spotProductAPIService.getTickerByProductId("BTC-USDT");
        this.toResultString(SpotProductAPITest.LOG, "ticker", ticker);
    }

    /**
     * 公共-获取成交数据
     * 本接口能查询最近60条数据。这个请求支持分页，并且按时间倒序排序和存储，
     * 最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * GET /api/spot/v3/instruments/<instrument_id>/trades
     * 限速规则：20次/2s
     */
    @Test
    public void getTrades() {
        final List<Trade> trades = this.spotProductAPIService.getTrades("BTC-USDT", "", "", "20");
        this.toResultString(SpotProductAPITest.LOG, "trades", trades);
    }

    /**
     * 公共-获取K线数据
     * 获取币对的K线数据。K线数据按请求的粒度分组返回，k线数据最多可获取最近2000条。
     * start ,end都要包含，不然就返回最近200条
     * GET /api/spot/v3/instruments/<instrument_id>/candles
     * 限速规则：20次/2s
     */
    @Test
    public void getCandles() {
        final JSONArray klines = this.spotProductAPIService.getCandles("BTC-USDT", "60", "", "");
        this.toResultString(SpotProductAPITest.LOG, "klines", klines);
    }


    /**
     *公共-获取指数成分
     * 获取指数成分。此接口为公共接口，不需要身份验证。
     * 限速规则：20次/2s
     * GET /api/index/v3/<instrument_id>/constituents
     *
     * **/
    @Test
    public void testGetIndex() {
        final String index = this.spotProductAPIService.getIndex("BTC-USD");
        this.toResultString(SpotProductAPITest.LOG, "klines", index);
    }




}
