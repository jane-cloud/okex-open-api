package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.FindAlgOrders;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderAlgoParam;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotOrderApiServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpotOrderAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotOrderAPITest.class);

    private SpotOrderAPIServive spotOrderAPIServive;

    @Before
    public void before() {
        this.config = this.config();
        this.spotOrderAPIServive = new SpotOrderApiServiceImpl(this.config);
    }

    /**
     * 下单
     * OKEx币币交易提供限价单和市价单和高级限价单下单模式。只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。
     * POST /api/spot/v3/orders
     * 限速规则：100次/2s
     */
    @Test
    public void addOrder() {

            final PlaceOrderParam order = new PlaceOrderParam();
            order.setClient_oid("20191128sell03");
            order.setInstrument_id("BTC-USDT");
            order.setPrice("8000");
            order.setType("limit");
            order.setSide("sell");
            order.setSize("0.001");
            order.setOrder_type("0");
            //市价单 买入金额必填
            order.setNotional("");

            final OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);
            this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 批量下单
     * 下指定币对的多个订单（每次只能下最多4个币对且每个币对可批量下10个单）
     * POST /api/spot/v3/batch_orders
     * 限速规则：50次/2s
     */
    @Test
    public void batchAddOrder() {
        final List<PlaceOrderParam> list = new ArrayList<>();
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("ctt1127spot1");
        order.setInstrument_id("BTC-USDT");
        order.setPrice("8000");
        order.setType("limit");
        order.setSide("sell");
        order.setSize("0.001");
        order.setOrder_type("0");
        list.add(order);

        final PlaceOrderParam order1 = new PlaceOrderParam();
        order1.setClient_oid("ctt1127spot2");
        order1.setInstrument_id("BTC-USDT");
        order1.setPrice("8100");
        order1.setType("limit");
        order1.setSide("sell");
        order1.setSize("0.001");
        order1.setOrder_type("0");
        list.add(order1);

        final Map<String, List<OrderResult>> orderResult = this.spotOrderAPIServive.addOrders(list);
        this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 撤销指定订单 delete协议 暂不使用
     */
    @Test
    public void cancleOrderByOrderId() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc-usdt");
        order.setClient_oid("1234545");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId(order, "1644664675964928");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 撤销指定订单 post协议
     * POST /api/spot/v3/cancel_orders/<order_id> or <client_oid>
     * 限速规则：100次/2s
     */
    @Test
    public void cancleOrderByOrderId_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("BTC-USDT");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId_post(order, "3927237510176768");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    @Test
    public void cancleOrderByClientOid_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("BTC-USDT");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByClientOid(order, "20191127sell03");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
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
//        order_ids.add(1600593327162368L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, BatchOrdersResult> orderResult = this.spotOrderAPIServive.cancleOrders(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 批量撤单 post协议
     * 撤销指定的某一种或多种币对的未完成订单（每次只能下最多4个币对且每个币对可批量下10个单）。
     * POST /api/spot/v3/cancel_batch_orders
     * 限速规则：50次/2s
     */
    @Test
    public void batchCancleOrders_post() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();
        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("BTC-USDT");
        final List<String> order_ids = new ArrayList<>();
        order_ids.add("3926753841733633");
        order_ids.add("3926753841733632");

        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

        final Map<String, Object> orderResult = this.spotOrderAPIServive.cancleOrders_post(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    @Test
    public void batchCancleOrders_postByClient_oid() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();
        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("BTC-USDT");

        final List<String> client_oid = new ArrayList<>();
        client_oid.add("ctt1127spot1");
        client_oid.add("ctt1127spot2");
        dto.setClient_oids(client_oid);
        cancleOrders.add(dto);

        final Map<String, Object> orderResult = this.spotOrderAPIServive.cancleOrders_post(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 获取指定订单信息
     * 通过订单ID获取单个订单信息。可以获取近3个月订单信息。已撤销的未成交单只保留2个小时。
     * GET /api/spot/v3/orders/<order_id>
     * 限速规则：20次/2s
     */
    @Test
    public void getOrderByOrderId() {
        final OrderInfo orderInfo = this.spotOrderAPIServive.getOrderByOrderId("BTC-USDT", "3939794136426496");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    @Test
    public void getOrderByClientOid() {
        final OrderInfo orderInfo = this.spotOrderAPIServive.getOrderByClientOid("BTC-USDT","20191128sell03");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 查询订单列表
     * 列出您当前的订单信息（本接口能查询最近3个月订单信息）。
     * 这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * GET /api/spot/v3/orders
     * 限速规则：20次/2s
     */
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getOrders("BTC-USDT", "0", "", "", "10");
            this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取所有未成交订单
     * 列出您当前所有的订单信息。这个请求支持分页，并且按时间倒序排序和存储，
     * 最新的排在最前面。请参阅分页部分以获取第一页之后的其他纪录。
     * GET /api/spot/v3/orders_pending
     * 限速规则：20次/2s
     */
    @Test
    public void getPendingOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getPendingOrders("", "", "3", "BTC-USDT");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取成交明细
     * 获取最近的成交明细表。这个请求支持分页，并且按成交时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他记录。 本接口能查询最近3月的数据。
     * 限速规则：20次/2s
     */
    @Test
    public void getFills() {
        final List<Fills> fillsList = this.spotOrderAPIServive.getFills("3927237510176768", "BTC-USDT", "", "", "");
        this.toResultString(SpotOrderAPITest.LOG, "fillsList", fillsList);
    }

    /**
     * 策略委托下单
     * 提供止盈止损、跟踪委托、冰山委托和时间加权委托策略
     * POST /api/spot/v3/order_algo
     * 限速规则：40次/2s
     */
    @Test
    public void addorder_algo(){
        {
            final long st = System.currentTimeMillis();

            final OrderAlgoParam order = new OrderAlgoParam();
         /*   //跟踪委托
            order.setInstrument_id("BTC-USDT");
            order.setMode("1");
            order.setOrder_type("2");
            order.setSide("sell");
            order.setCallback_rate("0.01");
            order.setTrigger_price("10700.9");
            order.setSize("10000");*/
        //止盈止损参数
            order.setInstrument_id("BTC-USDT");
            order.setMode("1");
            order.setOrder_type("1");
            order.setSize("1");
            order.setSide("buy");
            order.setTrigger_price("6400");
            order.setAlgo_price("6350");
       /*//冰山委托
            order.setInstrument_id("btc-usdt");
            order.setMode("1");
            order.setOrder_type("3");
            order.setSize("10000");
            order.setSide("buy");
            order.setAlgo_variance("0.01");
            order.setAvg_amount("10");
            order.setLimit_price("10500");*/
     /*  //时间加权委托
            order.setInstrument_id("btc-usdt");
            order.setMode("2");
            order.setOrder_type("4");
            order.setSize("5000");
            order.setSide("sell");

            order.setSweep_range("0.01");
            order.setSweep_ratio("0.5");
            order.setSingle_limit("20");
            order.setLimit_price("10800");
            order.setTime_interval("10");*/
            final OrderAlgoResult orderAlgoResult = this.spotOrderAPIServive.addorder_algo(order);
            this.toResultString(SpotOrderAPITest.LOG, "orders", orderAlgoResult);


        }
    }

    /**
     * 策略委托撤单
     * 根据指定的algo_id撤销某个币的未完成订单，每次最多可撤6（冰山/时间）/10（计划/跟踪）个。
     * POST /api/spot/v3/cancel_batch_algos
     * 限速规则：20 次/2s
     */
    @Test
    public void cancelOrder_algo(){
        final OrderAlgoParam orderAlgoParam = new OrderAlgoParam();
        String ids[]={"401579"};
        orderAlgoParam.setInstrument_id("BTC-USDT");
        orderAlgoParam.setOrder_type("1");
        orderAlgoParam.setAlgo_ids(ids);

        final OrderAlgoResult orderAlgoResult = this.spotOrderAPIServive.cancelOrder_algo(orderAlgoParam);
        this.toResultString(SpotOrderAPITest.LOG, "cancleorder", orderAlgoResult);

        
    }

    /**
     * 获取委托单列表
     * 列出您当前所有的订单信息。
     * GET /api/spot/v3/algo
     * 限速规则：20次/2s
     */
    @Test
    public void getAlgOrder(){
        final String findAlgOrderResults=this.spotOrderAPIServive.getAlgoOrder("BTC-USDT",
                        "1",
                        "",
                        "401579",
                        "","","20");
                this.toResultString(SpotOrderAPITest.LOG, "findAlgOrderResults", findAlgOrderResults);

    }


}
