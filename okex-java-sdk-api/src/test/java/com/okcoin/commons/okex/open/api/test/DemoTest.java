package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.bean.futures.param.Order;
import com.okcoin.commons.okex.open.api.bean.futures.result.Instruments;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.ServerTime;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpOrder;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;
import com.okcoin.commons.okex.open.api.service.spot.SpotProductAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginAccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotOrderApiServiceImpl;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotProductAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapUserAPIServiceImpl;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoTest {
    public static void main(String[] args) {


        APIConfiguration config = new APIConfiguration();
        //config.setEndpoint("http://192.168.80.14:8118");
        config.setEndpoint("https://www.okex.com/");
        config.setApiKey("");
        config.setSecretKey("");
        config.setPassphrase("");
        config.setPrint(true);
      /*  //查看钱包账户余额
        List<Wallet> list;
        AccountAPIService accountAPIService=new AccountAPIServiceImpl(config);
        list =accountAPIService.getWallet();
        System.out.println("余额："+list.toString());*/

      /*  //查看币币余额
        List<Account>   accountList;
        SpotAccountAPIService spotAccountAPIService=  new SpotAccountAPIServiceImpl(config);
        accountList = spotAccountAPIService.getAccounts();
        System.out.println("币币账户:"+accountList.toString());
        //账单列表
        List<Ledger> ledgerList;
        ledgerList =spotAccountAPIService.getLedgersByCurrency("usdt",null,null,"100");
        System.out.println("账单列表:");
        for(Ledger ledger:ledgerList){

            System.out.println(ledger.getCurrency()+ledger.getTimestamp()+ledger.getAmount()+ledger.getDetails());
        }
        //币币查询订单
        List<OrderInfo> orderInfoList;
        SpotOrderAPIServive spotOrderAPIServive=new SpotOrderApiServiceImpl(config);

        orderInfoList = spotOrderAPIServive.getOrders("btc-usdt","7","66666",null,"20");
        System.out.println("币币订单："+orderInfoList);
*/
       /*//资金划转记录
        JSONArray jsonArray;
        AccountAPIService accountAPIService=new AccountAPIServiceImpl(config);
        jsonArray=accountAPIService.getDepositHistory();
        System.out.println("资金划转记录"+jsonArray.toString());*/
      /*  //提币手续费
        List<WithdrawFee> withdrawFeeList;
        AccountAPIService accountAPIService=new AccountAPIServiceImpl(config);
        withdrawFeeList = accountAPIService.getWithdrawFee("BTC");
        System.out.println("提取BTC手续费"+withdrawFeeList);*/
       /* //获取币种列表
        List<Instruments> instrumentsList;
        FuturesMarketAPIService futuresMarketAPIService=new FuturesMarketAPIServiceImpl(config);
        instrumentsList=futuresMarketAPIService.getInstruments();
        System.out.println("币种信息");
        for(Instruments instruments:instrumentsList){
            System.out.println(instruments.toString());
        }*/
        /*//获取币种深度
        com.okcoin.commons.okex.open.api.bean.futures.result.Book book;
        FuturesMarketAPIService marketAPIService =new FuturesMarketAPIServiceImpl(config);
        book=marketAPIService.getInstrumentBook("BTC-USD-190906",5);
        System.out.println(book.toString());*/
        //获取k线数据
       /* JSONArray jsonArray;
        SpotProductAPIService spotProductAPIService=new SpotProductAPIServiceImpl(config);
        jsonArray=spotProductAPIService.getCandles("BTC-usdt", null, null, null);
        //System.out.println("k线情况" +jsonArray);
        int i=jsonArray.size();
        System.out.println(i);*/
        /*//币币杠杆账户信息
        List<Map<String, Object>> result;
        MarginAccountAPIService marginAccountAPIService=new MarginAccountAPIServiceImpl(config);
        result=marginAccountAPIService.getAccounts();
        System.out.println("币币杠杆账户信息："+result);*/
      /*  //获取借币记录
        List<MarginBorrowOrderDto> list;
        MarginAccountAPIService marginAccountAPIService=new MarginAccountAPIServiceImpl(config);
        list=marginAccountAPIService.getBorrowedAccounts("1",null,null,"20");
        System.out.println("借币记录：");
        for(MarginBorrowOrderDto marginBorrowOrderDto:list){
            System.out.println(
                    marginBorrowOrderDto
            );
        }
*/
        //单个合约持仓信息
        String singleSwap;
        SwapUserAPIServive swapUserAPIServive=new SwapUserAPIServiceImpl(config);
        //singleSwap=swapUserAPIServive.getPosition("BTC-USD-SWAP");
        //System.out.println("持仓信息:"+singleSwap);

        //查看所有币种合约账户信息
        String allSwap ;
        String strings[] ;
        //       allSwap = swapUserAPIServive.getAccounts();
//        strings=allSwap.split(",");
//        System.out.println("所有币种合约信息");
//        for(int i=0;i<strings.length;i++){
//            System.out.println(strings[i]);
//        }
       /* //获取某个合约的用户配置
        String contractSettings;
        contractSettings=swapUserAPIServive.selectContractSettings("BTC-USD-SWAP");
        System.out.println("获取合约用户配置"+contractSettings);*/

        //永续合约下单

        //下单参数
 /*       PpOrder ppOrder = new PpOrder();
        ppOrder.setClient_oid(null);
        //下单数量
        ppOrder.setSize("1");
        //合约名称
        ppOrder.setInstrument_id("BTC-USD-SWAP");
        //是否以对手价下单
        ppOrder.setMatch_price("1");
        //可填参数（1:开多 2:开空 3:平多 4:平空）
        ppOrder.setType("1");
        //委托价格
        ppOrder.setPrice("10737.8");

        System.out.println(ppOrder);
        Object tickerOrder;
        SwapTradeAPIService swapTradeAPIService=new SwapTradeAPIServiceImpl(config);
        tickerOrder=swapTradeAPIService.order(ppOrder);
        System.out.println(tickerOrder);*/
        }
    }


