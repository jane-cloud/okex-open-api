package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiAccountsVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiDealDetailVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiPositionsVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiUserRiskVO;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapUserAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
     * 所有合约持仓信息
     * 获取账户所有合约的持仓信息。请求此接口，会在数据库遍历所有币对下的持仓数据，
     * 有大量的性能消耗,请求频率较低。建议用户传合约ID获取持仓信息。
     * GET /api/swap/v3/position
     * 限速规则：1次/10s
     */
    @Test
    public void getAllPosition(){
        String jsonObject = swapUserAPIServive.getAllPosition();
        System.out.println("success");
        System.out.println(jsonObject);
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
        System.out.println(apiPositionsVO);
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
        //apiAccountsVO.getInfo().forEach(vo -> System.out.println(vo.getInstrument_id()));
        System.out.println(apiAccountsVO);
    }

    /**
     * 单个币种合约账户信息
     * 获取单个币种合约的账户信息，当用户没有持仓时，保证金率为10000
     * GET /api/swap/v3/<instrument_id>/accounts
     * 限速规则：20次/2s
     */
    @Test
    public void selectAccount() {
        String jsonObject = swapUserAPIServive.selectAccount(instrument_id);
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
        String jsonObject = swapUserAPIServive.selectContractSettings(instrument_id);
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO);
    }

    /**
     * 设定某个合约的杠杆
     * POST /api/swap/v3/accounts/<instrument_id>/leverage
     * 限速规则：5次/2s
     */
    @Test
    public void updateLevelRate() {
        LevelRateParam levelRateParam = new LevelRateParam();
        levelRateParam.setLeverage("20.22");
        levelRateParam.setSide("1");
        String jsonObject = swapUserAPIServive.updateLevelRate(instrument_id,levelRateParam) ;
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO);
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
        String jsonArray = swapUserAPIServive.getLedger(instrument_id, "1", "", "30","");
        System.out.println("success");
        System.out.println(jsonArray);
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
        String jsonObject = swapUserAPIServive.selectOrders(instrument_id, "2", "", "", "20");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 按照order_id查询订单信息
     * 获取订单信息
     * 通过订单id获取单个订单信息。本接口只能查询最近3个月的已成交和已撤销订单信息。
     * 已撤销的未成交单只保留2个小时。
     * GET /api/swap/v3/orders/<instrument_id>/<order_id>
     * 限速规则：40次/2s
     */
    @Test
    public void selectOrder() {
        String jsonObject = swapUserAPIServive.selectOrder(instrument_id, "375649210512384000");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 按照order_id查询订单信息
     * 获取订单信息
     * 通过订单id获取单个订单信息。本接口只能查询最近3个月的已成交和已撤销订单信息。
     * 已撤销的未成交单只保留2个小时。
     * GET /api/swap/v3/orders/<instrument_id>/<order_id>
     * 限速规则：40次/2s
     */
    @Test
    public void selectOrderByClientOid() {
        String jsonObject = swapUserAPIServive.selectOrderByClientOid(instrument_id, "20191126TestOrder");
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
        String jsonArray = swapUserAPIServive.selectDealDetail(instrument_id,"","","","");
        if(jsonArray.startsWith("{")){
            System.out.println(jsonArray);
        }
        else {
            List<ApiDealDetailVO> apiDealDetailVOS = JSONArray.parseArray(jsonArray, ApiDealDetailVO.class);
            apiDealDetailVOS.forEach(vo -> System.out.println(vo));
        }
    }


    /**
     * 获取合约挂单冻结数量
     * GET /api/swap/v3/accounts/<instrument_id>/holds
     * 限速规则：5次/2s
     */
    @Test
    public void getHolds() {
        String jsonObject = swapUserAPIServive.getHolds(instrument_id);
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 获取账户手续费费率
     * 获取您当前账户交易等级对应的手续费费率，母账户下的子账户的费率和母账户一致。每天凌晨0点更新一次
     * GET /api/swap/v3/trade_fee
     * 限速规则：1次/10s
     */
    @Test
    public void getTradeFee(){

        String jsonObject = swapUserAPIServive.getTradeFee();
        System.out.println("success");
        System.out.println(jsonObject);

    }

}
