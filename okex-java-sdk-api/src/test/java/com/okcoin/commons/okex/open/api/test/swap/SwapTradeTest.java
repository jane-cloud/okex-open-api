package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiCancelOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.OrderCancelResult;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapTradeAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class SwapTradeTest extends SwapBaseTest {
    private SwapTradeAPIService tradeAPIService;
    private static final Logger LOG = LoggerFactory.getLogger(SwapTradeTest.class);


    @Before
    public void before() {
        config = config();
        tradeAPIService = new SwapTradeAPIServiceImpl(config);
    }

    /**
     * 下单
     * API交易提供限价单下单模式，只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结，被冻结的资金以及数量取决于订单指定的类型和参数。目前api下单只支持以美元为计价单位
     * POST /api/swap/v3/order
     * 限速规则：40次/2s
     */
    @Test
    public void order() {
        PpOrder ppOrder = new PpOrder("20191126TestOrder1", "1", "1", "0", "7000", "BTC-USD-SWAP","0");
        final  Object apiOrderVO = tradeAPIService.order(ppOrder);
        this.toResultString(SwapTradeTest.LOG, "orders", apiOrderVO);
        System.out.println("jsonObject:::::"+apiOrderVO);

    }


    /**
     * 根据order_id撤销订单
     * 撤单
     * 撤销之前下的未完成订单。
     * POST /api/swap/v3/cancel_order/<instrument_id>/<order_id> or <client_oid>
     * 限速规则：40次/2s
     */
    @Test
    public void cancelOrder() {
        String jsonObject = tradeAPIService.cancelOrder(instrument_id, "375759393425924096");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO);
    }

    /**
     * 根据client_oid撤销订单
     * 撤单
     * 撤销之前下的未完成订单。
     * POST /api/swap/v3/cancel_order/<instrument_id>/<order_id> or <client_oid>
     * 限速规则：40次/2s
     */
    @Test
    public void cancelOrderByClientOid() {
        String jsonObject = tradeAPIService.cancelOrderByClientOid(instrument_id, "20191126TestOrder1");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO);
    }

    /**
     * 批量下单
     * 批量进行下单请求，每个合约可批量下10个单。
     * POST /api/swap/v3/orders
     * 限速规则：20次/2s
     */
    @Test
    public void batchOrder() {
        List<PpBatchOrder> list = new LinkedList<>();
        list.add(new PpBatchOrder("20191126ordersTest1", "1", "1", "0", "7000","0"));
        list.add(new PpBatchOrder("20191126ordersTest2", "1", "1", "0", "6666","0"));
        PpOrders ppOrders = new PpOrders();
        ppOrders.setInstrument_id(instrument_id);
        ppOrders.setOrder_data(list);
        String jsonObject = tradeAPIService.orders(ppOrders);
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 批量撤单根据orderid
     * 撤销之前下的未完成订单，每个币对可批量撤10个单。
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void batchCancelOrder() {
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();
        ppCancelOrderVO.getOrderids().add("375860964235354112");
        ppCancelOrderVO.getOrderids().add("375860964235354113");
        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrders(instrument_id, ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult);
    }

    /**
     * 批量撤单根据clientOid
     * 撤销之前下的未完成订单，每个币对可批量撤10个单。
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void batchCancelOrderByClientOid() {
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();
        //传入clientOid
        ppCancelOrderVO.getClientoids().add("20191126ordersTest1");
        ppCancelOrderVO.getClientoids().add("20191126ordersTest2");
        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrders(instrument_id, ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult);
    }

    /**
     * 委托策略下单
     * 提供止盈止损、跟踪委托、冰山委托和时间加权委托策略
     * POST /api/swap/v3/order_algo
     * 限速规则：40次/2s
     */
    @Test
    public void testSwapOrderAlgo(){
        SwapOrderParam swapOrderParam=new SwapOrderParam();
        swapOrderParam.setInstrument_id("BTC-USD-SWAP");
        swapOrderParam.setType("2");
        swapOrderParam.setOrder_type("1");
        swapOrderParam.setSize("100");
        swapOrderParam.setTrigger_price("10500");
        swapOrderParam.setAlgo_price("10501");

        String jsonObject = tradeAPIService.swapOrderAlgo(swapOrderParam);
        System.out.println("---------success--------");
        System.out.println(jsonObject);
    }

    /**
     * 委托策略撤单
     * 根据指定的algo_id撤销某个合约的未完成订单，每次最多可撤6（冰山/时间）/10（计划/跟踪）个。
     * POST /api/swap/v3/cancel_algos
     * 限速规则：20 次/2s
     */
    @Test
    public void testCancelOrderAlgo(){
        CancelOrderAlgo cancelOrderAlgo=new CancelOrderAlgo();
        cancelOrderAlgo.setAlgo_ids("375882565271363585");
        cancelOrderAlgo.setInstrument_id("BTC-USD-SWAP");
        cancelOrderAlgo.setOrder_type("1");
        String jsonObject = tradeAPIService.cancelOrderAlgo(cancelOrderAlgo);
        System.out.println("---------success--------");
        System.out.println(jsonObject);
    }

    /**
     * 获取委托单列表
     * 列出您当前所有的订单信息。
     * GET /api/swap/v3/order_algo/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void testGetSwapAlgOrders(){
        System.out.println("begin to show the swapAlgpOrders");
        String jsonObject = tradeAPIService.getSwapOrders("BTC-USD-SWAP",
                                                            "1",
                                                            "3",
                                                            null,
                                                            null,
                                                            null,
                                                            "20");
        System.out.println(jsonObject);
    }


}
