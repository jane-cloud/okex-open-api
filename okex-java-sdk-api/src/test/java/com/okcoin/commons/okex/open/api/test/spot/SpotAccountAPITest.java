package com.okcoin.commons.okex.open.api.test.spot;


import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SpotAccountAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotAccountAPITest.class);

    private SpotAccountAPIService spotAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(this.config);
    }

    @Test
    public void time() {
        final ServerTimeDto serverTimeDto = this.spotAccountAPIService.time();
        this.toResultString(SpotAccountAPITest.LOG, "time", serverTimeDto);
        System.out.println(serverTimeDto.getEpoch());
        System.out.println(serverTimeDto.getIso());
    }

    @Test
    public void getMiningData() {
        final Map<String, Object> miningdata = this.spotAccountAPIService.getMiningData();
        this.toResultString(SpotAccountAPITest.LOG, "miningdata", miningdata);
    }

    /**
     * 币币账户信息
     * 获取币币账户资产列表(仅展示拥有资金的币对)，查询各币种的余额、冻结和可用等信息
     * 20次/2s
     * GET /api/spot/v3/accounts
     */
    @Test
    public void getAccounts() {
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
        this.toResultString(SpotAccountAPITest.LOG, "accounts", accounts);
    }

    /**
     * 单一账户信息
     * 获取币币账户单个币种的余额、冻结和可用等信息。
     * 限速规则：20次/2s
     * GET /api/spot/v3/accounts/<currency>
     */
    @Test
    public void getAccountByCurrency() {
        final Account account = this.spotAccountAPIService.getAccountByCurrency("btc");
        this.toResultString(SpotAccountAPITest.LOG, "account", account);
    }

    /**
     * 账单流水
     * 列出账户资产流水。账户资产流水是指导致账户余额增加或减少的行为。
     * 流水会分页，并且按时间倒序排序和存储，最新的排在最前面。请参阅分页部分以获取第一页之后的其他记录。 本接口能查询最近3个月的数据。
     * 限速规则：20次/2s
     * GET /api/spot/v3/accounts/<currency>/ledger
     */
    @Test
    public void getLedgersByCurrency() {
        final Object ledgers = this.spotAccountAPIService.getLedgersByCurrency("USDT", "", "", "100","");
        this.toResultString(SpotAccountAPITest.LOG, "ledges", ledgers);
    }


}
