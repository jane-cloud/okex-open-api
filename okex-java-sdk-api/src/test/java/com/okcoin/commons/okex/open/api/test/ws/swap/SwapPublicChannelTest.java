package com.okcoin.commons.okex.open.api.test.ws.swap;

import com.okcoin.commons.okex.open.api.test.ws.swap.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.swap.config.WebSocketConfig;
import org.apache.commons.compress.utils.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

public class SwapPublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

    @Before
    public void connect() {
        //与服务器建立连接
        WebSocketConfig.publicConnect(webSocketClient);
    }

    @After
    public void close() {
        System.out.println(Instant.now().toString() + " close connect!");
        webSocketClient.closeConnection();
    }

    /**
     * 行情频道
     * Ticker Channel
     */
    @Test
    public void tickerChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/ticker:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * k线频道
     * 频道列表：
     * swap/candle60s // 1分钟k线数据频道
     swap/candle180s // 3分钟k线数据频道
     swap/candle300s // 5分钟k线数据频道
     swap/candle900s // 15分钟k线数据频道
     swap/candle1800s // 30分钟k线数据频道
     swap/candle3600s // 1小时k线数据频道
     swap/candle7200s // 2小时k线数据频道
     swap/candle14400s // 4小时k线数据频道
     swap/candle21600 // 6小时k线数据频道
     swap/candle43200s // 12小时k线数据频道
     swap/candle86400s // 1day k线数据频道
     swap/candle604800s // 1week k线数据频道
     */
    @Test
    public void klineChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/candle60s:EOS-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 交易频道
     * Trade Channel
     */
    @Test
    public void tradeChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/trade:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 资金费率频道
     * Trade Channel
     */
    @Test
    public void funding_rateChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/funding_rate:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 标记价格频道
     * markPrice Channel
     */
    @Test
    public void Channel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/mark_price:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 限价范围频道
     * priceRange Channel
     */
    @Test
    public void priceRangeChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/price_range:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 深度
     * Depth Channel
     * 首次返回200档，后续为增量
     */
    @Test
    public void depthChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/depth:BTC-USD-SWAP");
//        channel.add("swap/depth:XRP-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 5档深度
     * Depth5 Channel
     */
    @Test
    public void depth5Channel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/depth5:BTC-USD-SWAP");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //取消订阅
    @Test
    public void unsubscribeChannel() {
        ArrayList<String> list = Lists.newArrayList();
        //添加要取消订阅的频道名
        list.add("swap/candle60s:BTC-USD-SWAP");
        webSocketClient.unsubscribe(list);
        //为保证收到服务端返回的消息，需要让线程延迟
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
