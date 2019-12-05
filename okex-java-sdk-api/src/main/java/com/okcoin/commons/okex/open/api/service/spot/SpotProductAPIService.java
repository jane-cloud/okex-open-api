package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;

import java.math.BigDecimal;
import java.util.List;

public interface SpotProductAPIService {

    /**
     * 单个币对行情
     *
     * @param product
     * @return
     */
    Ticker getTickerByProductId(String product);

    /**
     * 行情列表
     *
     * @return
     */
    List<Ticker> getTickers();

    /**
     * @param instrument_id
     * @param size
     * @param depth
     * @return
     */
    Book bookProductsByProductId(String instrument_id, String size, String depth);

    /**
     * 币对列表S
     *
     * @return
     */
    List<Product> getProducts();

    /**
     * 交易列表
     *
     * @param instrument_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    List<Trade> getTrades(String instrument_id, String before, String after, String limit);

    /**
     * @param product
     * @param granularity
     * @param start
     * @param end
     * @return
     */
    JSONArray getCandles(String instrument_id, String granularity, String start, String end);

    List<String[]> getCandles_1(String product, String granularity, String start, String end);

    String getIndex(String instrument_id);

}
