package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;
import com.okcoin.commons.okex.open.api.service.spot.MarginOrderAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginOrderAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarginOrderAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(MarginOrderAPITest.class);
    private MarginOrderAPIService marginOrderAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.marginOrderAPIService = new MarginOrderAPIServiceImpl(this.config);
    }

    /**
     * 下单
     * OKEx API提供limit和market和高级限价委托等下单模式。只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。
     * POST /api/margin/v3/orders
     * 限速规则：100次/2s
     */
    @Test
    public void addOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();

        //公共参数
        order.setClient_oid("20191122buy");
        order.setInstrument_id("okb-usdt");
        order.setType("limit");
        order.setSide("buy");
        order.setOrder_type("0");
        order.setMargin_trading("2");
        //限价委托
        order.setPrice("1.0");
        order.setSize("0.1");

        //市价(买入必填<买入金额>)
        order.setNotional("");

        final OrderResult orderResult = this.marginOrderAPIService.addOrder(order);
        this.toResultString(MarginOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 批量下单
     * 下指定币对的多个订单（每次只能下最多4个币对且每个币对可批量下10个单）。
     * POST /api/margin/v3/batch_orders
     * 限速规则：50次/2s
     */
    @Test
    public void batchAddOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();
        //order.setClient_oid("");
        order.setInstrument_id("LTC-USDT");
        order.setType("limit");
        order.setSide("buy");
        order.setPrice("40");
        order.setSize("1");
        order.setMargin_trading("2");



        final PlaceOrderParam order1 = new PlaceOrderParam();
        //order1.setClient_oid("201807s2802");
        order1.setInstrument_id("LTC-USDT");
        order1.setPrice("35");
        order1.setType("limit");
        order1.setSide("buy");
        order1.setSize("1");
        order1.setMargin_trading("2");

        final List<PlaceOrderParam> list = new ArrayList<>();
        list.add(order);
        list.add(order1);

        final Map<String, List<OrderResult>> orderResult = this.marginOrderAPIService.addOrders(list);
        this.toResultString(MarginOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 指定订单id撤单 delete协议 暂不使用
     */
    @Test
    public void cancleOrderByProductIdAndOrderId() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc_usd");
        order.setClient_oid("20181009-01");
        final OrderResult orderResult = this.marginOrderAPIService.cancleOrderByOrderId(order, "23850");
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 指定订单id撤单 post协议
     * POST /api/margin/v3/cancel_orders/<order_id> or <client_oid>
     * 限速规则：100次/2s
     */
    @Test
    public void cancleOrderByProductIdAndOrderId_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc-usdt");
        order.setClient_oid("20181009-01");
        final OrderResult orderResult = this.marginOrderAPIService.cancleOrderByOrderId_post(order, "23850");
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 批量撤单 delete协议 暂不使用
     */
    @Test
    public void batchCancleOrders() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("btc-usdt");
        final List<String> order_ids = new ArrayList<>();
        order_ids.add("23464L");
        order_ids.add("23465L");
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

        final OrderParamDto dto1 = new OrderParamDto();
        dto1.setInstrument_id("etc_usdt");
        cancleOrders.add(dto1);

        final Map<String, JSONObject> orderResult = this.marginOrderAPIService.cancleOrders(cancleOrders);
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 批量撤单 post协议
     * 撤销指定的某一种或多种币对的所有未完成订单，每个币对可批量撤10个单。
     * POST /api/margin/v3/cancel_batch_orders
     * 限速规则：50次/2s
     */
    @Test
    public void batchCancleOrders_post() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("BTC-USDT");
        final List<String> order_ids = new ArrayList<>();
        order_ids.add("3747071400218624");
        order_ids.add("3747071400284160");
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, Object> orderResult = this.marginOrderAPIService.cancleOrders_post(cancleOrders);
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 获取订单信息
     * 通过订单ID获取单个订单信息。已撤销的未成交单只保留2个小时
     * GET /api/margin/v3/orders/<order_id>或者GET /api/margin/v3/orders/<client_oid>
     * 限速规则：20次/2s
     */
    @Test
    public void getOrderByProductIdAndOrderId() {
        final OrderInfo orderInfo = this.marginOrderAPIService.getOrderByProductIdAndOrderId("btc-usdt", "23844");
        this.toResultString(MarginOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 获取订单列表
     * 列出您当前所有的订单信息（本接口能查询最近100条订单信息）。这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * GET /api/margin/v3/orders
     * 限速规则：20次/2s
     */
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.marginOrderAPIService.getOrders("etc-usdt", "open", null, null, "3");
        this.toResultString(MarginOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取所有未成交订单
     * 列出您当前所有的订单信息。
     * 这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * GET /api/margin/v3/orders_pending
     */
    @Test
    public void getPendingOrders() {
        final List<OrderInfo> orderInfoList = this.marginOrderAPIService.getPendingOrders("", "", "", "BTC-USDT");
        this.toResultString(MarginOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取成交明细
     * 获取最近的成交明细列表。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他纪录。 本接口能查询最近3月的数据。
     * GET /api/margin/v3/fills
     * 限速规则：20次/2s
     */
    @Test
    public void getFills() {
        final List<Fills> fills = this.marginOrderAPIService.getFills("23855", "btc-usdt", "", "", "1");
        this.toResultString(MarginOrderAPITest.LOG, "fills", fills);
    }
}
