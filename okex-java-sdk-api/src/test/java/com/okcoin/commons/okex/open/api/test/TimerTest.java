package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.Ticker;
import com.okcoin.commons.okex.open.api.bean.futures.result.Trades;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.option.impl.OptionMarketAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapMarketAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapUserAPIServiceImpl;
import com.okcoin.commons.okex.open.api.test.account.AccountAPITests;
import com.okcoin.commons.okex.open.api.test.spot.SpotAccountAPITest;
import com.okcoin.commons.okex.open.api.utils.DateUtils;

import java.util.Date;
import java.util.List;
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

        //configuration.setPrint(true);
        configuration.setPrint(false);
        configuration.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
        //币币
        SpotAccountAPIService spotAccountAPIService = new SpotAccountAPIServiceImpl(configuration);
        //交割公共接口
        FuturesMarketAPIService marketAPIService =new FuturesMarketAPIServiceImpl(configuration);
        //交割私有接口
        FuturesTradeAPIServiceImpl  tradeAPIService = new FuturesTradeAPIServiceImpl(configuration);
        //资金账户
        AccountAPIService accountAPIService = new AccountAPIServiceImpl(configuration);
        //永续公共接口
        SwapMarketAPIServiceImpl swapMarketAPIService = new SwapMarketAPIServiceImpl(configuration);
        //永续用户
        SwapUserAPIServiceImpl swapUserAPIService = new SwapUserAPIServiceImpl(configuration);
        //期权公共接口
        OptionMarketAPIServiceImpl optionMarketAPIService = new OptionMarketAPIServiceImpl(configuration);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date time=new Date();


                //资金账户的账单流水测速
                /*JSONArray result = accountAPIService.getLedger("", "BTC", "", "", "");
                JSONArray result1 = accountAPIService.getLedger("", "OKB", "", "", "");
                System.out.println(result);
                System.out.println(result1);*/


                //JSONObject object = tradeAPIService.changeLeverageOnFixed("EOS-USD","EOS-USD-191227","long","20");
                //JSONObject object1 = tradeAPIService.changeLeverageOnFixed("BTC-USDT","BTC-USDT-191227","long","25");

                /*JSONObject object = tradeAPIService.getAccounts();


                System.out.println(object);*/

                /*List<Trades> trades = marketAPIService.getInstrumentTrades("XRP-USD-200327","","","10");
                for(Trades trades1:trades){
                    System.out.println("price:"+trades1.getPrice()+" qty:"+trades1.getQty()+" side:"+trades1.getSide()+" timestamp:"+trades1.getTimestamp()+" trade_id:"+trades1.getTrade_id());
                }*/

                /*String result = swapMarketAPIService.getIndexApi("BTC-USD-SWAP");
                System.out.println(result);*/

                /*String result = swapUserAPIService.selectOrderByOrderId("XRP-USD-SWAP", "403239665856897024");
                System.out.println(result);*/

                //JSONObject result = optionMarketAPIService.getDetailPrice("BTC-USD","BTC-USD-200327-9500-C");
                //System.out.println(result);
                Account result = spotAccountAPIService.getAccountByCurrency("USDT");
                System.out.println(result.getAvailable()+"/t"+result.getBalance()+"/t"+result.getCurrency());



            }
        };
        Timer timer = new Timer();
        long delay = 0;
        //
        long intevalPeriod = 7 * 100;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    } // end of main
}
