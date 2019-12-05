package com.okcoin.commons.okex.open.api.test.ws.spot.config;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.okcoin.commons.okex.open.api.bean.other.OrderBookItem;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBook;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBookDiff;
import com.okcoin.commons.okex.open.api.bean.other.SpotOrderBookItem;
import com.okcoin.commons.okex.open.api.enums.CharsetEnum;
import com.okcoin.commons.okex.open.api.utils.DateUtils;
import lombok.Data;
import net.sf.json.JSONObject;
import okhttp3.*;
import okio.ByteString;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * webSocket client
 *
 * @author oker
 * @create 2019-06-12 15:57
 **/
public class WebSocketClient {
    private static WebSocket webSocket = null;
    private static Boolean isLogin = false;
    private static Boolean isConnect = false;
    private final static ObjectReader objectReader = new ObjectMapper().readerFor(OrderBookData.class);
    private final static ObjectReader objectReader2 = new ObjectMapper().readerFor(SpotOrderBook.class);
    private final static HashFunction crc32 = Hashing.crc32();
    private static Map<String,Optional<SpotOrderBook>> bookMap = new HashMap<>();

    @Test
    public void test(){
        String tmp = "";
        //测试计算checksum值
        Optional<SpotOrderBook> book = parse(tmp);
        String str = getStr(book.get().getAsks(), book.get().getBids());
        System.out.println(str);
        int checksum = checksum(book.get().getAsks(), book.get().getBids());
        System.out.println(checksum);
    }

    /**
     * 解压函数
     * Decompression function
     *
     * @param bytes
     * @return
     */
    private static String uncompress(final byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {
            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 与服务器建立连接，参数为服务器的URL
     * connect server
     *
     * @param url
     */
    public void connection(final String url) {

        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(final WebSocket webSocket, final Response response) {
                isConnect = true;
                System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Connected to the server success!");

                //连接成功后，设置定时器，每隔25，自动向服务器发送心跳，保持与服务器连接
                final Runnable runnable = new Runnable() {
                    String time = new Date().toString();
                    @Override
                    public void run() {
                        // task to run goes here
                        sendMessage("ping");
                    }
                };
                final ScheduledExecutorService service = Executors
                        .newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 25, 25, TimeUnit.SECONDS);
            }

            @Override
            public void onMessage(final WebSocket webSocket, final String s) {
                System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive--------: " + s);
                if (null != s && s.contains("login")) {
                    if (s.endsWith("true}")) {
                        isLogin = true;
                    }
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, final int code, final String reason) {
                System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Connection is disconnected !!！");
                webSocket.close(1000, "Long time not to send and receive messages! ");
                webSocket = null;
                isConnect = false;
            }

            @Override
            public void onClosed(final WebSocket webSocket, final int code, final String reason) {
                System.out.println("Connection has been disconnected.");
                isConnect = false;
            }

            @Override
            public void onFailure(final WebSocket webSocket, final Throwable t, final Response response) {
                t.printStackTrace();
                System.out.println("Connection failed!");
                isConnect = false;
            }

            /**
             * 深度合并
             * @param webSocket
             * @param bytes
             */
            @Override
            public void onMessage(final WebSocket webSocket, final ByteString bytes) {
                //解压返回的数据
                final String s = WebSocketClient.uncompress(bytes.toByteArray());
                //判断是否是深度接口
                if (s.contains("\"table\":\"spot/depth\",")) {//是深度接口
                    if (s.contains("partial")) {//是第一次的200档，记录下第一次的200档
                        //System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                        JSONObject rst = JSONObject.fromObject(s);
                        net.sf.json.JSONArray dataArr = net.sf.json.JSONArray.fromObject(rst.get("data"));
                        JSONObject data = JSONObject.fromObject(dataArr.get(0));
                        String dataStr = data.toString();
                        Optional<SpotOrderBook> oldBook = parse(dataStr);
                        String instrumentId = data.get("instrument_id").toString();
                        bookMap.put(instrumentId,oldBook);
                    } else if (s.contains("\"action\":\"update\",")) {//是后续的增量，则需要进行深度合并
                        //System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                        JSONObject rst = JSONObject.fromObject(s);
                        net.sf.json.JSONArray dataArr = net.sf.json.JSONArray.fromObject(rst.get("data"));
                        JSONObject data = JSONObject.fromObject(dataArr.get(0));
                        String dataStr = data.toString();
                        String instrumentId = data.get("instrument_id").toString();
                        Optional<SpotOrderBook> oldBook = bookMap.get(instrumentId);
                        Optional<SpotOrderBook> newBook = parse(dataStr);
                        //获取增量的ask
                        List<SpotOrderBookItem> askList = newBook.get().getAsks();
                        //获取增量的bid
                        List<SpotOrderBookItem> bidList = newBook.get().getBids();
                        System.out.println("oldBook.get--------------"+oldBook.get());
                        //oldBook.get()拿到全量的数据
                        SpotOrderBookDiff bookdiff = oldBook.get().diff(newBook.get());
                        System.out.println("名称："+instrumentId+",深度合并成功！checknum值为：" + bookdiff.getChecksum() + ",合并后的数据为：" + bookdiff.toString());
                        String str = getStr(bookdiff.getAsks(), bookdiff.getBids());
                        System.out.println("名称："+instrumentId+",拆分要校验的字符串：" + str);
                        int checksum = checksum(bookdiff.getAsks(), bookdiff.getBids());
                        System.out.println("名称："+instrumentId+",校验的checksum:" + checksum);
                        boolean flag = checksum==bookdiff.getChecksum()?true:false;
                        if(flag){
                            System.out.println("名称："+instrumentId+",深度校验结果为："+flag);
                            oldBook = parse(bookdiff.toString());
                            bookMap.put(instrumentId,oldBook);
                        }else{
                            System.out.println("名称："+instrumentId+",深度校验结果为："+flag+"数据有误！请重连！");
                            //获取订阅的频道和币对
                            String channel = rst.get("table").toString();
                            String unSubStr = "{\"op\": \"unsubscribe\", \"args\":[\"" + channel+":"+instrumentId + "\"]}";
                            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + unSubStr);
                            webSocket.send(unSubStr);
                            String subStr = "{\"op\": \"subscribe\", \"args\":[\"" + channel+":"+instrumentId + "\"]}";
                            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + subStr);
                            webSocket.send(subStr);
                            System.out.println("名称："+instrumentId+",正在重新订阅！");
                        }
                    }
                } else {//不是深度接口
                    System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Receive: " + s);
                }
                if (null != s && s.contains("login")) {
                    if (s.endsWith("true}")) {
                        isLogin = true;
                    }
                }
            }
        });
    }

    /**
     * 获得sign
     * @param message
     * @param secret
     * @return
     */
    private String sha256_HMAC(final String message, final String secret) {
        String hash = "";
        try {
            final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(CharsetEnum.UTF_8.charset()), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            final byte[] bytes = sha256_HMAC.doFinal(message.getBytes(CharsetEnum.UTF_8.charset()));
            hash = Base64.getEncoder().encodeToString(bytes);
        } catch (final Exception e) {
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * list转换成json
     * @param list
     * @return
     */
    private String listToJson(final List<String> list) {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray.toJSONString();
    }

    /**
     * 登录
     * login
     *
     * @param apiKey
     * @param passPhrase
     * @param secretKey
     */
    public void login(final String apiKey, final String passPhrase, final String secretKey) {
        final String timestamp = (Double.parseDouble(DateUtils.getEpochTime()) + 28800) + "";
        final String message = timestamp + "GET" + "/users/self/verify";
        final String sign = sha256_HMAC(message, secretKey);
        final String str = "{\"op\"" + ":" + "\"login\"" + "," + "\"args\"" + ":" + "[" + "\"" + apiKey + "\"" + "," + "\"" + passPhrase + "\"" + "," + "\"" + timestamp + "\"" + "," + "\"" + sign + "\"" + "]}";
        sendMessage(str);
    }


    /**
     * 订阅，参数为频道组成的集合
     * Bulk Subscription
     *
     * @param list
     */
    public void subscribe(final List<String> list) {
        final String s = listToJson(list);
        final String str = "{\"op\": \"subscribe\", \"args\":" + s + "}";
        sendMessage(str);
    }

    /**
     * 取消订阅，参数为频道组成的集合
     * unsubscribe
     *
     * @param list
     */
    public void unsubscribe(final List<String> list) {
        final String s = listToJson(list);
        final String str = "{\"op\": \"unsubscribe\", \"args\":" + s + "}";
        sendMessage(str);
    }

    private void sendMessage(final String str) {
        if (null != webSocket) {
            try {
                Thread.sleep(1000);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Send: " + str);
            if (isConnect) {
                webSocket.send(str);
                return;
            }
        }
        System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Please establish a connection before operation !!!");
    }

    /**
     * 断开连接
     * Close Connection
     */
    public void closeConnection() {
        if (null != webSocket) {
            webSocket.close(1000, "User close connect !!!");
        } else {
            System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + " Please establish a connection before operation !!!");
        }
        isConnect = false;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public boolean getIsConnect() {
        return isConnect;
    }

    public static <T extends OrderBookItem> int checksum(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice().toString());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice().toString());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }

        return crc32.hashString(str, StandardCharsets.UTF_8).asInt();
    }

    private static <T extends OrderBookItem> String getStr(List<T> asks, List<T> bids) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i < bids.size()) {
                s.append(bids.get(i).getPrice().toString());
                s.append(":");
                s.append(bids.get(i).getSize());
                s.append(":");
            }
            if (i < asks.size()) {
                s.append(asks.get(i).getPrice().toString());
                s.append(":");
                s.append(asks.get(i).getSize());
                s.append(":");
            }
        }
        final String str;
        if (s.length() > 0) {
            str = s.substring(0, s.length() - 1);
        } else {
            str = "";
        }
        return str;
    }

    public static Optional<SpotOrderBook> parse(String json) {

        try {
            OrderBookData data = objectReader.readValue(json);
            List<SpotOrderBookItem> asks =
                    data.getAsks().stream().map(x -> new SpotOrderBookItem(new String(x.get(0)), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            List<SpotOrderBookItem> bids =
                    data.getBids().stream().map(x -> new SpotOrderBookItem(new String(x.get(0)), x.get(1), x.get(2)))
                            .collect(Collectors.toList());

            return Optional.of(new SpotOrderBook(data.getInstrument_id(), asks, bids, data.getTimestamp(),data.getChecksum()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Data
    static class OrderBookData {
        private String instrument_id;
        private List<List<String>> asks;
        private List<List<String>> bids;
        private String timestamp;
        private int checksum;
    }
}
