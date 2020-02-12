package com.okcoin.commons.okex.open.api.test.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.service.option.OptionMarketAPIService;
import com.okcoin.commons.okex.open.api.service.option.impl.OptionMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionMarketAPITests extends OptionAPIBaseTests{
    private static final Logger LOG = LoggerFactory.getLogger(OptionMarketAPITests.class);

    private OptionMarketAPIService marketAPIService;

    @Before
    public void before() {
        config = config();
        marketAPIService = new OptionMarketAPIServiceImpl(config);
    }
    /***
     * 公共-获取标的指数
     * 获取期权交易已支持的标的指数列表。
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/underlying
     * */
    //公共-获取标的指数
    @Test
    public void testGetUnderlying(){
        JSONArray result = marketAPIService.getUnderlying();
        toResultString(LOG,"result",result);
    }
    /***
     * 公共-获取期权合约
     * 获取可用合约的列表。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<underlying>
     * */
    //公共-获取期权合约
    @Test
    public void testGetInstrumrnt(){
        JSONArray result = marketAPIService.getInstruments("BTC-USD","","");
        toResultString(LOG,"result",result);
    }
    /***
     * 公共-获取期权合约详细定价
     * 获取同一标的下所有期权合约详细定价。
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<underlying>/summary
     * */
    @Test
    public void testGetAllSummary(){
        JSONArray result = marketAPIService.getAllSummary("BTC-USD","200327");
        toResultString(LOG,"",result);
    }
    /***
     * 公共-获取单个期权合约详细定价
     * 获取某个期权合约的详细定价。
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<underlying>/summary/<instrument_id>*/
       //公共-获取单个期权合约的详细定价
    @Test
    public void testGetDetailPrice(){
        JSONObject result = marketAPIService.getDetailPrice("BTC-USD","BTC-USD-200327-9500-C");
        toResultString(LOG,"result",result);
    }
    /**
     * 公共-获取深度数据
     * 获取期权合约的深度数据。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<instrument_id>/book*/
    @Test
    public void testGetDepthData(){
        JSONObject result = marketAPIService.getDepthData("BTC-USD-200327-7500-C","");
        toResultString(LOG,"result",result);
    }
    /***
     * 公共-获取成交数据
     * 获取期权合约的成交记录。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<instrument_id>/trades
     * */
    //获取期权合约的成交记录
    @Test
    public void testGetTradeList(){
        JSONArray result = marketAPIService.getTradeList("BTC-USD-200327-7500-C","","","");
        toResultString(LOG,"",result);
    }

    /***
     * 公共-获取某个Ticker信息
     * 获取某个期权合约的最新成交价、买一价、卖一价和对应的量。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<instrument_id>/ticker
     * */
    //获取单个期权合约的ticker信息
    @Test
    public void testGetTicker(){
        JSONObject result = marketAPIService.getTicker("BTC-USD-200327-7500-C");
        toResultString(LOG,"result",result);
    }
    /***
     *公共-获取K线数据
     * 获取期权合约的K线数据。
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/instruments/<instrument_id>/candles
     * */
    //获取K线数据
    @Test
    public void testGetCandles(){
        JSONArray result = marketAPIService.getCandles("BTC-USD-200327-7500-C");
        toResultString(LOG,"result",result);
    }

}
