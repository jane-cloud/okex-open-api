package com.okcoin.commons.okex.open.api.test.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class AccountAPITests extends  AccountAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(AccountAPITests.class);

    private AccountAPIService accountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.accountAPIService = new AccountAPIServiceImpl(this.config);
    }

    /**资金划转
      *OKEx站内在资金账户、交易账户和子账户之间进行资金划转
     * 限速规则：1次/2s（每个币种）
     * POST /api/account/v3/transfer
     */
    @Test
    public void transfer() {
        Transfer transfer = new Transfer();
        transfer.setFrom("6");
        transfer.setTo("3");
        transfer.setCurrency("USDT");
        transfer.setAmount("1");
        //transfer.setSub_account("");
        //transfer.setInstrument_id("");

        transfer.setTo_instrument_id("BTC-USDT");

        JSONObject result = this.accountAPIService.transfer(transfer);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**提币
     * 限速规则：20次/2s
     * POST /api/account/v3/withdrawal
     */
    @Test
    public void withdraw() {
        Withdraw withdraw = new Withdraw();
        withdraw.setTo_address("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
        withdraw.setFee("0.0005");
        withdraw.setCurrency("btc");
        withdraw.setAmount("");
        withdraw.setDestination("");
        withdraw.setTrade_pwd("123456");
        JSONObject result = this.accountAPIService.withdraw(withdraw);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 获取币种列表
     * 限速规则：20次/2s
     * GET /api/account/v3/currencies
     */
    @Test
    public void getCurrencies() {
        List<Currency> result = this.accountAPIService.getCurrencies();
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 账单流水查询
     * 限速规则：20次/2s
     * GET /api/account/v3/ledger
     */
    @Test
    public void getLedger() {
        JSONArray result = this.accountAPIService.getLedger("", "BTC", "", "", "");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 资金账户信息
     * 限速规则：20次/2s
     * GET /api/account/v3/wallet
     */
    @Test
    public void getWallet() {
        //所有的资金账户信息
       /* List<Wallet> result = this.accountAPIService.getWallet();
        this.toResultString(AccountAPITests.LOG, "result", result);*/
        //单一币种账户信息
        List<Wallet> result2 = this.accountAPIService.getWallet("USDT");
        this.toResultString(AccountAPITests.LOG, "result", result2);



    }

    /**
     * 获取充值地址
     * 获取各个币种的充值地址，包括曾使用过的老地址。
     * 限速规则：20次/2s
     * GET /api/account/v3/deposit/address
     */
    @Test
    public void getDepositAddress() {
        JSONArray result = this.accountAPIService.getDepositAddress("BTC");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 提币手续费
     * 查询提现到数字货币地址时，建议网络手续费信息。手续费越高，网络确认越快。
     * 限速规则：20次/2s
     * GET /api/account/v3/withdrawal/fee
     */
    @Test
    public void getWithdrawFee() {
        //List<WithdrawFee> result = this.accountAPIService.getWithdrawFee("btc");
        List<WithdrawFee> result = this.accountAPIService.getWithdrawFee("BTC");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 接口下线
     */
    @Test
    public void getOnHold() {
        JSONArray result = this.accountAPIService.getOnHold("cac");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }
    /**
     * 接口下线
     */
    @Test
    public void lock() {
        JSONObject result = this.accountAPIService.lock("cac", new BigDecimal("10000"));
        this.toResultString(AccountAPITests.LOG, "result", result);
        this.toResultString(AccountAPITests.LOG, "result", result);

    }
    /**
     * 接口下线
     */
    @Test
    public void unlock() {
        JSONObject result = this.accountAPIService.unlock("cac", new BigDecimal("10000"));
        this.toResultString(AccountAPITests.LOG, "result", result);
        this.toResultString(AccountAPITests.LOG, "result", result);

    }

    /**
     * 获取所有币种充值记录
     * 获取所有币种的充值记录，为最近一百条数据。
     * 限速规则：20次/2s
     * GET /api/account/v3/deposit/history
     */
    @Test
    public void getDepositHistory() {
        /*JSONArray result = this.accountAPIService.getDepositHistory();
        this.toResultString(AccountAPITests.LOG, "result", result);*/
        JSONArray result2 = this.accountAPIService.getDepositHistory("btc");
        this.toResultString(AccountAPITests.LOG, "result", result2);
    }

    /**
     * 查询所有/单个币种的提币记录
     * 获取所有币种的充值记录，为最近一百条数据。
     * 限速规则：20次/2s
     * GET /api/account/v3/withdrawal/history
     */
    @Test
    public void getWithdrawalHistory() {
        /*JSONArray result = this.accountAPIService.getWithdrawalHistory();
        this.toResultString(AccountAPITests.LOG, "result", result);*/
        JSONArray result2 = this.accountAPIService.getWithdrawalHistory("btc");
        this.toResultString(AccountAPITests.LOG, "result", result2);
    }
}
