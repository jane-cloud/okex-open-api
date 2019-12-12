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
    //获取深度数据
    @Test
    public void testGetDepthData(){
        JSONObject result = marketAPIService.getDepthData("TBTC-USD-191227-7500-C","");
        toResultString(LOG,"result",result);
    }
    //获取期权合约的成交记录
    @Test
    public void testGetTradeList(){
        JSONArray result = marketAPIService.getTradeList("TBTC-USD-191227-7500-C","","","");
        toResultString(LOG,"",result);
    }

    //获取可用合约订单列表
    @Test
    public void testGetInstrumrnt(){
        JSONArray result = marketAPIService.getInstruments("TBTC-USD","","");
        toResultString(LOG,"result",result);
    }

    //获取同一标的下所有期权合约详细定价
    @Test
    public void testGetAllSummary(){
        JSONArray result = marketAPIService.getAllSummary("TBTC-USD","");
        toResultString(LOG,"",result);
    }
    //获取某个期权合约的详细定价
    @Test
    public void testGetDetailPrice(){
        JSONObject result = marketAPIService.getDetailPrice("TBTC-USD","TBTC-USD-191227-7500-C");
        toResultString(LOG,"result",result);
    }

    //获取K线数据
    @Test
    public void testGetCandles(){
        JSONArray result = marketAPIService.getCandles("TBTC-USD-191227-7500-C");
        toResultString(LOG,"result",result);
    }

    //获取单个期权合约的ticker信息
    @Test
    public void testGetTicker(){
        JSONObject result = marketAPIService.getTicker("TBTC-USD-191227-7500-C");
        toResultString(LOG,"result",result);
    }

    //获取期权交易已支持的标的指数列表
    @Test
    public void testGetUnderlying(){
        JSONArray result = marketAPIService.getUnderlying();
        toResultString(LOG,"result",result);
    }

}
