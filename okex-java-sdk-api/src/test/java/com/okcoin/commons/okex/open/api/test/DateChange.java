package com.okcoin.commons.okex.open.api.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChange {
    public static void main(String[] args) throws ParseException {
        String timeStr="2018-03-01T07:53:43.288Z";

        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");

        Date dateTime = SDF.parse(timeStr);

        SimpleDateFormat Time3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(Time3.format(dateTime));
    }
}
