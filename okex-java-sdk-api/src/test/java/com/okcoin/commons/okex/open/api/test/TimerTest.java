package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.test.account.AccountAPITests;
import com.okcoin.commons.okex.open.api.utils.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();
        return config;
    }

    public static void main(String[] args) {
        APIConfiguration configuration=new APIConfiguration();
        configuration.setEndpoint("https://www.okex.com/");
        configuration.setApiKey("");
        configuration.setSecretKey("");
        configuration.setPassphrase("");
        configuration.setPrint(true);
        configuration.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
        FuturesMarketAPIService marketAPIService =new FuturesMarketAPIServiceImpl(configuration);
        FuturesTradeAPIService  tradeAPIService = new FuturesTradeAPIServiceImpl(configuration) ;
        AccountAPIService accountAPIService = new AccountAPIServiceImpl(configuration);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date time=new Date();
                // task to run goes here
                /*System.out.println(DateUtils.timeToString(time,4));
                JSONArray array = marketAPIService.getInstrumentCandles("BTC-USD-191227", null,null, 60L);
                System.out.println( "Instrument-Candles"+array);*/

           /*     //资金账户的账单流水测速
                JSONArray result = accountAPIService.getLedger("", "BTC", "", "", "");
                JSONArray result1 = accountAPIService.getLedger("", "OKB", "", "", "");
                System.out.println(result);
                System.out.println(result1);*/
                JSONObject object = tradeAPIService.changeLeverageOnFixed("ETH-USD","ETH-USD-191227","long","20");
                JSONObject object1 = tradeAPIService.changeLeverageOnFixed("ETH-USDT","ETH-USDT-191227","long","25");

                System.out.println(object);
                System.out.println(object1);


            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 450;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    } // end of main
}
