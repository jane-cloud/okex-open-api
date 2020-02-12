package com.okcoin.commons.okex.open.api.test.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;
import com.okcoin.commons.okex.open.api.service.option.OptionTradeAPIService;
import com.okcoin.commons.okex.open.api.service.option.impl.OptionTradeAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class OptionTradeAPITests extends OptionAPIBaseTests{

    private static final Logger LOG = LoggerFactory.getLogger(OptionTradeAPITests.class);

    private OptionTradeAPIService tradeAPIService;


    @Before
    public void before() {
        config = config();
        tradeAPIService = new OptionTradeAPIServiceImpl(config);
    }

    /***
     * 单个标的指数持仓信息
     * 获取某个标的下的持仓信息
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/<underlying>/position
     *
     * **/
    @Test
    public void testGetPosition(){
        JSONObject result = tradeAPIService.getPosition("BTC-USD","BTC-USD-200327-7000-P");
        toResultString(LOG,"result",result);
    }


    /***
     * 单个标的物账户信息
     * 获取单个标的物账户信息。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/accounts/<underlying>
     * **/
    @Test
    public void testGetAccount(){
        JSONObject result = tradeAPIService.getAccount("BTC-USD");
        toResultString(LOG, "Accounts", result);
    }
    /****
     * 下单
     * 限速规则：20次/s
     * HTTP请求
     * POST /api/option/v3/order
     * */

    @Test
    public void testGetOrder(){
        OrderParam param = new OrderParam();
        param.setClient_oid("cttoption0212teset1");
        param.setInstrument_id("BTC-USD-200327-7000-C");
        param.setPrice("0.0015");
        param.setSide("buy");
        param.setSize("1");
        param.setOrder_type("0");
        param.setMatch_price("0");

        JSONObject result = tradeAPIService.getOrder(param);
        toResultString(LOG,"result",result);
    }

    /***
     *  批量下单
     * 批量进行下单请求，每个标的指数最多可批量下10个单。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * POST /api/option/v3/orders
     * **/
    @Test
    public void testGetOrders1(){
        OrderDataParam orderDataParam = new OrderDataParam();
        orderDataParam.setUnderlying("BTC-USD");

        OrderParam orderParam = new OrderParam();
        orderParam.setClient_oid("cttoption1212testttc5");
        orderParam.setInstrument_id("BTC-USD-200327-7500-C");
        orderParam.setPrice("0.0005");
        orderParam.setSize("1");
        orderParam.setSide("buy");
        orderParam.setMatch_price("0");
        orderParam.setOrder_type("0");

        OrderParam orderParam1 = new OrderParam();
        orderParam1.setClient_oid("cttoption1212testttc6");
        orderParam1.setInstrument_id("BTC-USD-200327-7500-C");
        orderParam1.setPrice("0.001");
        orderParam1.setSize("1");
        orderParam1.setSide("buy");
        orderParam1.setMatch_price("0");
        orderParam1.setOrder_type("0");

        List<OrderParam> list = new ArrayList();
        list.add(orderParam);
        list.add(orderParam1);
        orderDataParam.setOrderdata(list);

        JSONObject result = tradeAPIService.getOrders1(orderDataParam);
        toResultString(LOG,"result",result);

    }
    /***
     * 撤单
     * 撤销之前下的未完成订单。
     *
     * 限速规则：20次/s
     * HTTP请求
     * POST /api/option/v3/cancel_order/<underlying>/<order_id or client_oid>
     * ***/
    //撤单
    @Test
    public void testCancelOrders(){
        JSONObject result = tradeAPIService.cancelOrders("BTC-USD","127775390743711744");
        toResultString(LOG,"result",result);
    }

    //根据client_oid撤单
    @Test
    public void testCancelOrdersByClientOid(){
        JSONObject result = tradeAPIService.cancelOrderByClientOid("BTC-USD","cttoption0212teset1");
        toResultString(LOG,"result",result);
    }
    /***
     *批量撤单
     * 批量撤销之前下的未完成订单，每个标的指数最多可批量撤10个单。
     * 限速规则：20次/2s
     * HTTP请求
     * POST /api/option/v3/cancel_batch_orders/<underlying>
     * */
    //批量撤单
    @Test
    public void testCancelBantchOrders(){
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<>();
        //根据order_id进行批量撤单
        list.add("125243617098915840");
        list.add("125243617098915841");
        cancelOrders.setOrder_ids(list);

        //根据client_oid进行批量撤单
     /*   list.add("cttoption1212testttc1");
        list.add("cttoption1212testttc2");
        cancelOrders.setClient_oids(list);*/


        JSONObject result = tradeAPIService.cancelBatchOrders("BTC-USD",cancelOrders);
        toResultString(LOG,"result",result);
    }

    /***
     *修改订单
     * 修改之前下的未完成订单。
     *
     * 限速规则：20次/s
     * HTTP请求
     * POST /api/option/v3/amend_order/<underlying>
     *
     */
    @Test
    public void testAmendOrder(){
        AmentDate amentDate = new AmentDate();
        //根据order_id进行修改
        /*amentDate.setOrder_id("158444945847922688");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.001");*/

        //根据client_oid进行修改
        amentDate.setClient_oid("cttoption0212teset1");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.0005");


        JSONObject result = tradeAPIService.amendOrder("BTC-USD",amentDate);
        toResultString(LOG,"result",result);
    }
    /***
     * 批量修改订单
     * 修改之前下的未完成订单，每个标的指数最多可批量修改10个单。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * POST /api/option/v3/amend_batch_orders/<underlying>
     * **/

    @Test
    public void testAmendBatchOrders(){
        AmendDateParam param = new AmendDateParam();
        AmentDate amentDate = new AmentDate();
        //根据order_id撤单
        /*amentDate.setOrder_id("158444945847922688");
        amentDate.setNew_size("2");*/
        //根据client_oid撤单
        amentDate.setClient_oid("cttoption1212testttc5");
        amentDate.setNew_size("1");
        amentDate.setNew_price("0.001");

        AmentDate amentDate1 = new AmentDate();
        //根据order_id撤单
        /*amentDate1.setOrder_id("158444945847922689");
        amentDate1.setNew_size("2");*/
        //根据client_oid撤单
        amentDate1.setClient_oid("cttoption1212testttc6");
        amentDate1.setNew_size("2");
        amentDate1.setNew_price("0.001");


        List<AmentDate> list = new ArrayList<>();
        list.add(amentDate);
        list.add(amentDate1);

        param.setAmend_data(list);

        JSONObject result = tradeAPIService.amendBatchOrders("BTC-USD",param);
        toResultString(LOG,"result",result);
    }

    /***
     * 获取单个订单状态
     * 查询单个订单状态。已撤销的未成交单只保留2个小时。
     *
     * 限速规则：40次/2s
     * HTTP请求
     * GET /api/option/v3/orders/<underlying>/<order_id or client_oid>
     *
     * **/
    //根据order_id获取
    @Test
    public void testGetOrderInfo(){
        JSONObject result = tradeAPIService.getOrderInfo("BTC-USD","136805624537206784");
        toResultString(LOG,"result",result);
    }
    //根据 client_oid 获取
    @Test
    public void testGetOrderInfoByClientOid(){
        JSONObject result = tradeAPIService.getOrderInfoByClientOid("BTC-USD","cttoption0212teset1");
        toResultString(LOG,"result",result);
    }
    /***
     * 获取订单列表
     * 获取当前所有的订单列表。本接口能查询7天内数据。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/orders/<underlying>
     *
     * */
    //获取订单列表
    @Test
    public void testGetOrderList(){
        JSONObject result = tradeAPIService.getOrderList("BTC-USD","0","BTC-USD-200327-7000-P","","","");
        toResultString(LOG,"result",result);
    }

    /***
     * 获取成交明细
     * 获取最近的成交明细列表。本接口能查询7天内数据。
     *
     * 限速规则：20次/2s
     * HTTP请求
     * GET /api/option/v3/fills/<underlying>
     * **/
    //获取成交明细
    @Test
    public void testGetFills(){
        JSONArray result = tradeAPIService.getFills("BTC-USD","","","","","");
        toResultString(LOG,"result",result);
    }

    /***
     *获取账单流水
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。 本接口能查询最近7天的数据。
     *
     * 限速规则：5次/2s
     * HTTP请求
     * GET /api/option/v3/accounts/<underlying>/ledger
     * */
    //账单流水查询
    @Test
    public void testGetLedger(){
        JSONArray result = tradeAPIService.getLedger("BTC-USD");
        toResultString(LOG,"result",result);

    }
    /***
     *获取手续费费率
     * 获取当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致（每天凌晨0点更新）。
     *
     * 限速规则：1次/10s
     * HTTP请求
     * GET /api/option/v3/trade_fee
     * */
    //获取当前账户交易等级对应的手续费费率
    @Test
    public void testGetTradeFee(){
        JSONObject result = tradeAPIService.getTradeFee();
        toResultString(LOG,"result",result);
    }










}
