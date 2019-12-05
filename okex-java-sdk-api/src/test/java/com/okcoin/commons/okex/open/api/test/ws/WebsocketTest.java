package com.okcoin.commons.okex.open.api.test.ws;


import com.okcoin.commons.okex.open.api.websocket.WebSocket;
import com.okcoin.commons.okex.open.api.websocket.WebSocketAdapter;
import com.okcoin.commons.okex.open.api.websocket.WebSocketClient;
import org.junit.Test;


public class WebsocketTest {


    @Test
    public void wsTest() {
        WebSocket ws = new WebSocketClient(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket ws, String text) throws Exception {
                System.out.println("ws message: " + text);
                if (text.contains("checksum")) {
                    boolean res = ws.checkSum(text);
                }
            }

            @Override
            public void onWebsocketOpen(WebSocket ws) {
                System.out.println("ws open");
                ws.subscribe("spot/depth:BTC-USDT");
            }

            @Override
            public void handleCallbackError(WebSocket websocket, Throwable cause) {
                cause.printStackTrace();
            }


            @Override
            public void onWebsocketClose(WebSocket ws, int code) {
                System.out.println("ws close code = " + code);
            }

            @Override
            public void onWebsocketPong(WebSocket ws) {
                System.out.println("receive pong");
            }
        });
        ws.connect();
    }
}
