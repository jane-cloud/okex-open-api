package com.okcoin.commons.okex.open.api.test.spot;

import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MarginAccountAPITest extends SpotAPIBaseTests {
    private static final Logger LOG = LoggerFactory.getLogger(MarginAccountAPITest.class);

    private MarginAccountAPIService marginAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.marginAccountAPIService = new MarginAccountAPIServiceImpl(this.config);
    }

    /**
     * 币币杠杆账户信息
     * 获取币币杠杆账户资产列表，查询各币种的余额、冻结和可用等信息。
     * GET /api/margin/v3/accounts
     * 限速规则：20次/2s
     */
    @Test
    public void getAccounts() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAccounts();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 单一币对账户信息
     * 获取币币杠杆某币对账户的余额、冻结和可用等信息。
     * GET /api/margin/v3/accounts/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void getAccountsByProductId() {
        final Map<String, Object> result = this.marginAccountAPIService.getAccountsByProductId("BTC-USDT");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 账单流水查询
     * 列出杠杆帐户资产流水。帐户资产流水是指导致帐户余额增加或减少的行为。流水会分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他纪录。 本接口能查询最近3个月的数据。
     * GET /api/margin/v3/accounts/<instrument_id>/ledger
     * 限速规则：20次/2s
     */
    @Test
    public void getLedger() {
        final List<UserMarginBillDto> result = this.marginAccountAPIService.getLedger(
                "eth-usdt", "",
                "", "", "1");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 杠杆配置信息
     * 获取币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。
     * GET /api/margin/v3/accounts/availability
     * 限速规则：20次/2s
     */
    @Test
    public void getAvailability() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailability();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 某个杠杆配置信息
     * 获取某个币币杠杆账户的借币配置信息，包括当前最大可借、借币利率、最大杠杆倍数。
     * GET /api/margin/v3/accounts/<instrument_id>/availability
     * 限速规则：20次/2s
     */
    @Test
    public void getAvailabilityByProductId() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailabilityByProductId("BTC-USDT");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 获取借币记录
     * 获取币币杠杆帐户的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他纪录。
     * GET /api/margin/v3/accounts/borrowed
     * 限速规则：20次/2s
     */
    @Test
    public void getBorrowedAccounts() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccounts("1", "", "", "2");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 某币对借币记录
     * 获取币币杠杆帐户某币对的借币记录。这个请求支持分页，并且按时间倒序排序和存储，最新的排在最前面。
     * 请参阅分页部分以获取第一页之后的其他纪录。
     * GET /api/margin/v3/accounts/<instrument_id>/borrowed
     * 限速规则：20次/2s
     */
    @Test
    public void getBorrowedAccountsByProductId() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccountsByProductId("BTC-USDT", "", "", "2", "0");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }
    /**
     * 借币
     * 在某个币币杠杆账户里进行借币。
     * POST /api/margin/v3/accounts/borrow
     * 限速规则：100次/2s
     */
    @Test
    public void borrow_1() {
        final BorrowRequestDto dto = new BorrowRequestDto();
        dto.setAmount("10");
        dto.setCurrency("usdt");
        dto.setInstrument_id("ltc_usdt");
        final BorrowResult result = this.marginAccountAPIService.borrow_1(dto);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 还币
     * 在某个币币杠杆账户里进行还币。
     * POST /api/margin/v3/accounts/repayment
     * 限速规则：100次/2s
     */
    @Test
    public void repayment_1() {
        final RepaymentRequestDto dto = new RepaymentRequestDto();
        dto.setAmount("1");
        dto.setBorrow_id("185778");
        dto.setCurrency("usdt");
        dto.setInstrument_id("ltc-usdt");
        final RepaymentResult result = this.marginAccountAPIService.repayment_1(dto);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

}
