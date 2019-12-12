package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.result.*;
import com.okcoin.commons.okex.open.api.service.swap.SwapMarketAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SwapMarketTest extends SwapBaseTest {
    private SwapMarketAPIService swapMarketAPIService;

    @Before
    public void before() {
        config = config();
        swapMarketAPIService = new SwapMarketAPIServiceImpl(config);
    }

    /**
     * 公共-获取合约信息
     * 获取可用合约的列表，这组公开接口提供了行情数据的快照，无需认证即可调用。
     * 获取可用合约的列表，查询各合约的交易限制和价格步长等信息。
     * GET /api/swap/v3/instruments
     * 限速规则：20次/2s
     */
    @Test
    public void getContractsApi() {
        String contractsApi = swapMarketAPIService.getContractsApi();
        if (contractsApi.startsWith("{")) {
            System.out.println(contractsApi);
        } else {
            List<ApiContractVO> list = JSONArray.parseArray(contractsApi, ApiContractVO.class);
            System.out.println(contractsApi);
            list.forEach(contract -> System.out.println(contract.getInstrument_id()));
        }
    }

    /**
     * 公共-获取深度数据
     * 获取合约的深度列表。
     * GET /api/swap/v3/instruments/<instrument_id>/depth
     * 限速规则：20次/2s
     */
    @Test
    public void getDepthApi() {
        //size控制深度档位
        String depthApi = swapMarketAPIService.getDepthApi(instrument_id, "1","0.01");
        DepthVO depthVO = JSONObject.parseObject(depthApi, DepthVO.class);
        System.out.println(depthApi);
    }

    /**
     * 公共-获取全部ticker信息
     * 获取平台全部合约的最新成交价、买一价、卖一价和24交易量。
     * GET /api/swap/v3/instruments/ticker
     * 限速规则：20次/2s
     */
    @Test
    public void getTickersApi() {
        String tickersApi = swapMarketAPIService.getTickersApi();
        if (tickersApi.startsWith("{")) {
            System.out.println(tickersApi);
        } else {
            List<ApiTickerVO> list = JSONArray.parseArray(tickersApi, ApiTickerVO.class);
            //list.forEach(vo -> System.out.println(vo.getInstrument_id()));
            System.out.println(tickersApi);
        }
    }

    /**
     * 公共-获取某个ticker信息
     * 获取合约的最新成交价、买一价、卖一价和24交易量。
     * GET /api/swap/v3/instruments/<instrument_id>/ticker
     * 限速规则：20次/2s
     */
    @Test
    public void getTickerApi() {
        String tickerApi = swapMarketAPIService.getTickerApi(instrument_id);
        ApiTickerVO apiTickerVO = JSONObject.parseObject(tickerApi, ApiTickerVO.class);
        System.out.println(tickerApi);

    }

    /**
     * 公共-获取成交数据
     * 获取合约的成交记录，本接口能查询最近300条数据。
     * 限速规则：20次/2s
     */
    @Test
    public void getTradesApi() {
        String tradesApi = swapMarketAPIService.getTradesApi(instrument_id, null, null, null);
        if (tradesApi.startsWith("{")) {
            System.out.println(tradesApi);
        } else {
            List<ApiDealVO> apiDealVOS = JSONArray.parseArray(tradesApi, ApiDealVO.class);
            //apiDealVOS.forEach(vo -> System.out.println(vo.getTimestamp()));
            System.out.println(tradesApi);
        }
    }

    /**
     * 公共-获取K线数据
     * 获取合约的K线数据。k线数据最多可获取最近1440条。
     * GET /api/swap/v3/instruments/<instrument_id>/candles
     * 限速规则：20次/2s
     */
    @Test
    public void getCandlesApi() {
        String candlesApi = swapMarketAPIService.getCandlesApi(instrument_id, null, null, "60");
       String[] candleSize=candlesApi.split("],");

        System.out.println("------------------------"+candleSize.length);
        candlesApi = candlesApi.replaceAll("\\[", "\\{");

        System.out.println(candlesApi);
        /*if (candlesApi.lastIndexOf("\\]") != candlesApi.length()) {
            candlesApi = candlesApi.replace("\\]", "\\}");
        }
        if (candlesApi.startsWith("{")) {
            System.out.println(candlesApi);
        } else {
            List<ApiKlineVO> apiKlineVOS = JSONArray.parseArray(candlesApi, ApiKlineVO.class);
            apiKlineVOS.forEach(vo -> System.out.println(vo.getTimestamp()));
            System.out.println(candlesApi);
        }*/

    }

    /**
     * 公共-获取指数信息
     * GET /api/swap/v3/instruments/<instrument_id>/index
     * 限速规则：20次/2s
     */
    @Test
    public void getIndexApi() {
        String indexApi = swapMarketAPIService.getIndexApi(instrument_id);
        ApiIndexVO apiIndexVO = JSONObject.parseObject(indexApi, ApiIndexVO.class);
        System.out.println(indexApi);
    }

    /**
     * 公共-获取法币汇率
     * GET /api/swap/v3/rate
     * 限速规则：20次/2s
     */
    @Test
    public void getRateApi() {
        String rateApi = swapMarketAPIService.getRateApi();
        ApiRateVO apiRateVO = JSONObject.parseObject(rateApi, ApiRateVO.class);
        System.out.println(apiRateVO.getInstrument_id());
        System.out.println(rateApi);
    }

    /**
     * 公共-获取平台总持仓量
     * GET /api/swap/v3/instruments/<instrument_id>/open_interest
     * 限速规则：20次/2s
     */
    @Test
    public void getOpenInterestApi() {
        String openInterestApi = swapMarketAPIService.getOpenInterestApi(instrument_id);
        ApiOpenInterestVO apiOpenInterestVO = JSONObject.parseObject(openInterestApi, ApiOpenInterestVO.class);
        System.out.println(apiOpenInterestVO.getTimestamp());
        System.out.println(openInterestApi);
    }

    /**
     * 公共-获取当前限价
     * 获取合约当前交易的最高买价和最低卖价。
     * GET /api/swap/v3/instruments/<instrument_id>/price_limit
     * 限速规则：20次/2s
     */
    @Test
    public void getPriceLimitApi() {
        String priceLimitApi = swapMarketAPIService.getPriceLimitApi(instrument_id);
        ApiPriceLimitVO apiPriceLimitVO = JSONObject.parseObject(priceLimitApi, ApiPriceLimitVO.class);
        System.out.println(apiPriceLimitVO);
        System.out.println(priceLimitApi);
    }

    /**
     * 公共-获取强平单
     * GET /api/swap/v3/instruments/<instrument_id>/liquidation
     * 限速规则：20次/2s
     */
    @Test
    public void getLiquidationApi() {
        String liquidationApi = swapMarketAPIService.getLiquidationApi(instrument_id, "1", "1", "", "10");
        if (liquidationApi.startsWith("{")) {
            System.out.println(liquidationApi);
        } else {
            List<ApiLiquidationVO> apiLiquidationVOS = JSONArray.parseArray(liquidationApi, ApiLiquidationVO.class);
            apiLiquidationVOS.forEach(vo -> System.out.println(vo));
        }
    }

    /**
     * 公共-获取合约资金费率
     * GET /api/swap/v3/instruments/<instrument_id>/funding_time
     * 限速规则：20次/2s
     */
    @Test
    public void getFundingTimeApi() {
        String fundingTimeApi = swapMarketAPIService.getFundingTimeApi(instrument_id);
        ApiFundingTimeVO apiFundingTimeVO = JSONObject.parseObject(fundingTimeApi, ApiFundingTimeVO.class);
        System.out.println(apiFundingTimeVO.toString());
    }

    /**
     * 公共-获取合约历史资金费率
     * GET /api/swap/v3/instruments/<instrument_id>/historical_funding_rate
     * 限速规则：20次/2s
     */
    @Test
    public void getHistoricalFundingRateApi() {
        String historicalFundingRateApi = swapMarketAPIService.getHistoricalFundingRateApi(instrument_id, "1", "", "10");
        if (historicalFundingRateApi.startsWith("{")) {
            System.out.println(historicalFundingRateApi);
        } else {
            List<ApiFundingRateVO> apiFundingRateVOS = JSONArray.parseArray(historicalFundingRateApi, ApiFundingRateVO.class);
            apiFundingRateVOS.forEach(vo -> System.out.println(vo));
        }
    }

    /**
     * 公共-获取合约标记价格
     * GET /api/swap/v3/instruments/<instrument_id>/mark_price
     * 限速规则：20次/2s
     */
    @Test
    public void getMarkPriceApi() {
        String markPriceApi = swapMarketAPIService.getMarkPriceApi(instrument_id);
        ApiMarkPriceVO apiMarkPriceVO = JSONObject.parseObject(markPriceApi, ApiMarkPriceVO.class);
        System.out.println(apiMarkPriceVO);
    }

}
