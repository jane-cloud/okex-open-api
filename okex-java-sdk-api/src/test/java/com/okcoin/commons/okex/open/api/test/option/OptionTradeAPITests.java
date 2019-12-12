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

    //获取单个标的物账户信息
    @Test
    public void testGetAccount(){
        JSONObject result = tradeAPIService.getAccount("TBTC-USD");
        toResultString(LOG, "Accounts", result);
    }



    //账单流水查询
    @Test
    public void testGetLedger(){
        JSONArray result = tradeAPIService.getLedger("TBTC-USD");
        toResultString(LOG,"result",result);

    }

    //下单
    @Test
    public void testGetOrder(){
        OrderParam param = new OrderParam();
        param.setClient_oid("cttoption1210teset003");
        param.setInstrument_id("TBTC-USD-191227-7500-C");
        param.setPrice("0.0005");
        param.setSide("buy");
        param.setSize("1");
        param.setOrder_type("0");
        param.setMatch_price("0");

        JSONObject result = tradeAPIService.getOrder(param);
        toResultString(LOG,"result",result);
    }

    //批量下单
    @Test
    public void testGetOrders1(){
        OrderDataParam orderDataParam = new OrderDataParam();
        orderDataParam.setUnderlying("TBTC-USD");

        OrderParam orderParam = new OrderParam();
        orderParam.setClient_oid("cttoption1210testttc6");
        orderParam.setInstrument_id("TBTC-USD-191227-7500-C");
        orderParam.setPrice("0.0005");
        orderParam.setSize("1");
        orderParam.setSide("buy");
        orderParam.setMatch_price("0");
        orderParam.setOrder_type("0");

        OrderParam orderParam1 = new OrderParam();
        orderParam1.setClient_oid("cttoption1210testttc7");
        orderParam1.setInstrument_id("TBTC-USD-191227-7500-C");
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

    //撤单
    @Test
    public void testCancelOrders(){
        JSONObject result = tradeAPIService.cancelOrders("TBTC-USD","157737863216934912");
        toResultString(LOG,"result",result);
    }

    //根据client_oid撤单
    @Test
    public void testCancelOrdersByClientOid(){
        JSONObject result = tradeAPIService.cancelOrderByClientOid("TBTC-USD","cttoption1210teset003");
        toResultString(LOG,"result",result);
    }

    //批量撤单
    @Test
    public void testCancelBantchOrders(){
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<>();
        //根据order_id进行批量撤单
        /*list.add("158434511576412160");
        list.add("158434511576412161");
        cancelOrders.setOrder_ids(list);*/

        //根据client_oid进行批量撤单
        list.add("cttoption1210testttc4");
        list.add("cttoption1210testttc5");
        cancelOrders.setClient_oids(list);


        JSONObject result = tradeAPIService.cancelBatchOrders("TBTC-USD",cancelOrders);
        toResultString(LOG,"result",result);
    }

    /***
     *
     * 修改之前下的未完成订单。
     *限速规则：40次/2s
     */
    @Test
    public void testAmendOrder(){
        AmentDate amentDate = new AmentDate();
        //根据order_id进行修改
        /*amentDate.setOrder_id("158444945847922688");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.001");*/

        //根据client_oid进行修改
        amentDate.setClient_oid("cttoption1210testttc7");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.0005");


        JSONObject result = tradeAPIService.amendOrder("TBTC-USD",amentDate);
        toResultString(LOG,"result",result);
    }
    /***
     * 修改之前下的未完成订单，每个标的指数最多可批量修改10个单。
     *限速规则：20次/2s
     * **/

    @Test
    public void testAmendBatchOrders(){
        AmendDateParam param = new AmendDateParam();
        AmentDate amentDate = new AmentDate();
        //根据order_id撤单
        /*amentDate.setOrder_id("158444945847922688");
        amentDate.setNew_size("2");*/
        //根据client_oid撤单
        amentDate.setClient_oid("cttoption1210testttc6");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.001");

        AmentDate amentDate1 = new AmentDate();
        //根据order_id撤单
        /*amentDate1.setOrder_id("158444945847922689");
        amentDate1.setNew_size("2");*/
        //根据client_oid撤单
        amentDate1.setClient_oid("cttoption1210testttc7");
        amentDate1.setNew_size("1");
        amentDate1.setNew_price("0.001");


        List<AmentDate> list = new ArrayList<>();
        list.add(amentDate);
        list.add(amentDate1);

        param.setAmend_data(list);

        JSONObject result = tradeAPIService.amendBatchOrders("TBTC-USD",param);
        toResultString(LOG,"result",result);
    }


    //获取订单信息
    @Test
    public void testGetOrderInfo(){
        JSONObject result = tradeAPIService.getOrderInfo("TBTC-USD","157768004215287808");
        toResultString(LOG,"result",result);
    }
    @Test
    public void testGetOrderInfoByClientOid(){
        JSONObject result = tradeAPIService.getOrderInfoByClientOid("TBTC-USD","cttoption1210testttc7");
        toResultString(LOG,"result",result);
    }

    //获取订单列表
    @Test
    public void testGetOrderList(){
        JSONObject result = tradeAPIService.getOrderList("TBTC-USD","0","","","","");
        toResultString(LOG,"result",result);
    }

    //获取成交明细
    @Test
    public void testGetFills(){
        JSONArray result = tradeAPIService.getFills("TBTC-USD","","","","","");
        toResultString(LOG,"result",result);
    }

    //获取某个标的下的持仓信息
    @Test
    public void testGetPosition(){
        JSONObject result = tradeAPIService.getPosition("TBTC-USD","TBTC-USD-191227-7500-C");
        toResultString(LOG,"result",result);
    }

    //获取当前账户交易等级对应的手续费费率
    @Test
    public void testGetTradeFee(){
        JSONObject result = tradeAPIService.getTradeFee();
        toResultString(LOG,"result",result);
    }










}
