package com.okcoin.commons.okex.open.api.test;

public class BeanShellTest {
    public static void main(String[] args) {
        String str="JMeterVariables:\n" +
                "JMeterThread.last_sample_ok=true\n" +
                "JMeterThread.pack=org.apache.jmeter.threads.SamplePackage@191a4ff8\n" +
                "START.HMS=093039\n" +
                "START.MS=1571880639632\n" +
                "START.YMD=20191024\n" +
                "TESTSTART.MS=1571901287902\n" +
                "__jm__websocket__idx=0\n" +
                "websocket.last_frame_final=true\n";

        System.out.println("长度"+str.length());

    }


}
