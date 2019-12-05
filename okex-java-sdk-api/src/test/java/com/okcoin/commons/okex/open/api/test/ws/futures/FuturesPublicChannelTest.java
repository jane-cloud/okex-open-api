package com.okcoin.commons.okex.open.api.test.ws.futures;

import com.okcoin.commons.okex.open.api.test.ws.futures.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.futures.config.WebSocketConfig;
import org.apache.commons.compress.utils.Lists;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

/**
 * 公共频道
 * public channel
 *
 * @author oker
 * @date 2019/7/5 1:40 AM
 */
public class FuturesPublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();
    private static Logger logger = Logger.getLogger(FuturesPublicChannelTest.class);

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
        channel.add("futures/ticker:BTC-USD-191227");
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
     * 全量合约信息
     * Ticker Channel
     */
    @Test
    public void instrumentsChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("futures/instruments");
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
     * futures/candle60s // 1分钟k线数据频道
     * futures/candle180s // 3分钟k线数据频道
     * futures/candle300s // 5分钟k线数据频道
     * futures/candle900s // 15分钟k线数据频道
     * futures/candle1800s // 30分钟k线数据频道
     * futures/candle3600s // 1小时k线数据频道
     * futures/candle7200s // 2小时k线数据频道
     * futures/candle14400s // 4小时k线数据频道
     * futures/candle21600 // 6小时k线数据频道
     * futures/candle43200s // 12小时k线数据频道
     * futures/candle86400s // 1day k线数据频道
     * futures/candle604800s // 1week k线数据频道
     */
    @Test
    public void klineChannel() {
        logger.info("info信息");
        logger.debug("debug信息");
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("futures/candle60s:EOS-USD-191227");
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
        channel.add("futures/trade:EOS-USD-191227");
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
        channel.add("futures/mark_price:BTC-USD-191227");
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
        channel.add("futures/price_range:BTC-USD-191227");
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
     * 全量深度
     * Depth5 Channel
     */
    @Test
    public void depthAllChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("futures/depth_l2_tbt:BTC-USD-191227");
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
     * 400深度
     * Depth Channel
     * 首次返回400档，后续为增量
     */
    @Test
    public void depthChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        //channel.add("swap/depth:LTC-USD-SWAP");
        //200档位深度校验
        channel.add("futures/depth:BTC-USD-191227");

        //channel.add("futures/depth_l2_tbt:BTC-USD-191018");
        //调用订阅方法
        webSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(100000000);
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
        channel.add("futures/depth5:BTC-USD-191227");
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
     * 预估交割价频道
     * estimatedPrice Channel
     */
    @Test
    public void estimatedPriceChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("futures/estimated_price:BTC-USD-191227");
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
        list.add("futures/depth5:BTC-USD-191227");
        webSocketClient.subscribe(list);
        webSocketClient.unsubscribe(list);
        webSocketClient.unsubscribe(list);
        webSocketClient.unsubscribe(list);
        webSocketClient.unsubscribe(list);
        webSocketClient.unsubscribe(list);
        webSocketClient.unsubscribe(list);
        //为保证收到服务端返回的消息，需要让线程延迟
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
