package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.bean.swap.result.*;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapUserAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SwapUserTest extends SwapBaseTest {
    private SwapUserAPIServive swapUserAPIServive;
    private String jsonObject;

    @Before
    public void before() {
        config = config();
        swapUserAPIServive = new SwapUserAPIServiceImpl(config);
    }

    /**
     *所有合约持仓信息
     * GET /api/swap/v3/position
     * 限速规则：20次/2s
     */
    @Test
    public void testGetPositions(){
        String result = this.swapUserAPIServive.getPositions();
        System.out.println("success");
        System.out.println(result);
    }

    /**
     *单个合约持仓信息
     * GET /api/swap/v3/<instrument_id>/position
     * 限速规则：20次/2s
     */
    @Test
    public void getPosition() {
        String jsonObject = swapUserAPIServive.getPosition("BTC-USD-SWAP");
        ApiPositionsVO apiPositionsVO = JSONObject.parseObject(jsonObject, ApiPositionsVO.class);
        System.out.println("success");
        apiPositionsVO.getHolding().forEach(vp -> System.out.println(apiPositionsVO.getMargin_mode()));
    }

    /**
     * 所有币种合约账户信息
     * 获取所有币种合约的账户信息，当用户没有持仓时，保证金率为10000
     * GET /api/swap/v3/accounts
     * 限速规则：1次/10s
     */
    @Test
    public void getAccounts() {
        String jsonObject = swapUserAPIServive.getAccounts();
        ApiAccountsVO apiAccountsVO = JSONObject.parseObject(jsonObject, ApiAccountsVO.class);
        System.out.println("success");
        apiAccountsVO.getInfo().forEach(vo -> System.out.println(vo.getInstrument_id()));
    }

    /**
     * 单个币种合约账户信息
     * 获取单个币种合约的账户信息，当用户没有持仓时，保证金率为10000
     * GET /api/swap/v3/<instrument_id>/accounts
     * 限速规则：20次/2s
     */
    @Test
    public void selectAccount() {
       //String jsonObject = swapUserAPIServive.selectAccount(instrument_id);
        String jsonObject = swapUserAPIServive.selectAccount("XRP-USDT-SWAP");
        System.out.println("success");
        System.out.println(jsonObject);

    }

    /**
     * 获取某个合约的用户配置
     * 获取某个合约的杠杆倍数，持仓模式
     * GET /api/swap/v3/accounts/<instrument_id>/settings
     * 限速规则：5次/2s
     */
    @Test
    public void selectContractSettings() {
        String jsonObject = swapUserAPIServive.selectContractSettings("BTC-USD-SWAP");
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO.getInstrument_id());
    }

    /**
     * 设定某个合约的杠杆
     * POST /api/swap/v3/accounts/<instrument_id>/leverage
     * 限速规则：5次/2s
     */
    @Test
    public void updateLevelRate() {
        LevelRateParam levelRateParam = new LevelRateParam();
        levelRateParam.setLeverage("25");
        levelRateParam.setSide("1");
        String jsonObject = swapUserAPIServive.updateLevelRate("BTC-USD-SWAP", levelRateParam);
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO.getInstrument_id());
    }

    /**
     * 获取所有订单列表
     * 列出您当前所有的订单信息。本接口能查询最近7天20000条数据。
     * 这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * GET /api/swap/v3/orders/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void selectOrders() {
        String jsonObject = swapUserAPIServive.selectOrders("BTC-USD-SWAP", "2", "", "", "20");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 获取订单信息
     * 通过订单id获取单个订单信息。本接口只能查询最近3个月的已成交和已撤销订单信息。
     * 已撤销的未成交单只保留2个小时。
     * GET /api/swap/v3/orders/<instrument_id>/<order_id>
     * 限速规则：40次/2s
     */
    @Test
    public void selectOrderByOrderId() {
        String jsonObject = swapUserAPIServive.selectOrderByOrderId("XRP-USDT-SWAP", "411935092828454912");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    @Test
    public void selectOrderByClientOid() {
        String jsonObject = swapUserAPIServive.selectOrderByClientOid("BTC-USDT-SWAP", "ctt1216testswap04");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 获取成交明细
     * 获取最近的成交明细列表，本接口能查询最近7天的数据。
     * GET /api/swap/v3/fills
     * 限速规则：20次/2s
     */
    @Test
    public void selectDealDetail(){
        String jsonArray = swapUserAPIServive.selectDealDetail("XRP-USDT-SWAP","411935092828454912","","","");
        if(jsonArray.startsWith("{")){
            System.out.println(jsonArray);
        }
        else {
            List<ApiDealDetailVO> apiDealDetailVOS = JSONArray.parseArray(jsonArray, ApiDealDetailVO.class);
            //apiDealDetailVOS.forEach(vo -> System.out.println(vo.getInstrument_id()));
        }
    }

    /**
     * 账单流水查询
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。
     * 流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。 本接口能查询最近7天的数据。
     * GET /api/swap/v3/accounts/<instrument_id>/ledger
     * 限速规则：5次/2s
     */
    @Test
    public void getLedger() {
        String jsonArray = swapUserAPIServive.getLedger("XRP-USDT-SWAP", "", "", "30","");
        System.out.println("success");
        System.out.println(jsonArray);
    }

    /**
     * 获取合约挂单冻结数量
     * GET /api/swap/v3/accounts/<instrument_id>/holds
     * 限速规则：5次/2s
     */
    @Test
    public void getHolds() {
        String jsonObject = swapUserAPIServive.getHolds("BTC-USD-SWAP");
        System.out.println("success");
        System.out.println(jsonObject);
    }
    //获取手续费等级费率
    @Test
    public void TestGetTradeFee(){
        String result = swapUserAPIServive.getTradeFee();
        System.out.println("success");
        System.out.println(result);
    }

}
