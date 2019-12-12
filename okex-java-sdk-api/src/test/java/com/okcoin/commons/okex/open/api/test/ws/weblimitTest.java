package com.okcoin.commons.okex.open.api.test.ws;

import com.okcoin.commons.okex.open.api.test.ws.futures.FuturesPublicChannelTest;
import com.okcoin.commons.okex.open.api.test.ws.futures.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.futures.config.WebSocketConfig;
import com.okcoin.commons.okex.open.api.utils.DateUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class weblimitTest {
    public static void main(String[] args) {
          final WebSocketClient webSocketClient = new WebSocketClient();
          Logger logger = Logger.getLogger(FuturesPublicChannelTest.class);
        WebSocketConfig.publicConnect(webSocketClient);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Date time=new Date();
                    ArrayList<String> list = Lists.newArrayList();
                    list.add("futures/candle60s:BTC-USD-191227");
                    //webSocketClient.subscribe(list);
                    webSocketClient.unsubscribe(list);
                    // task to run goes here
                    //System.out.println(DateUtils.timeToString(time,4));

                }
            };
            Timer timer = new Timer();
            long delay = 0;
            long intevalPeriod = 1;
            // schedules the task to be run in an interval
            timer.scheduleAtFixedRate(task, delay, intevalPeriod);
        } // end of main
    }




