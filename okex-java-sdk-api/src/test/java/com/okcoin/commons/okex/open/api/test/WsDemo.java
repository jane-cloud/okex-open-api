package com.okcoin.commons.okex.open.api.test;

import okhttp3.*;
import okio.ByteString;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WsDemo {
    public static void main(String[] args) {
        System.out.println("start the main:::");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("wss://149.129.81.70/websocket/okexapi?compress=true")
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {

                webSocket.send("{\"op\": \"subscribe\", \"args\": [\"spot/ticker:BTC-USDT\",\"spot/candle60s:ETH-USDT\"]}");

            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {

                System.out.println(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {

                System.out.println(uncompress(bytes.toByteArray()));
            }
        });
        System.out.println("time is over");
    }



    private static String uncompress(byte[] bytes) {
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
}

