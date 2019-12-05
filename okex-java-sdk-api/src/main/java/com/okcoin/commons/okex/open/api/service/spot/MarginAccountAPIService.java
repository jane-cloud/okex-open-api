package com.okcoin.commons.okex.open.api.service.spot;

import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * 杠杆资产相关接口
 */
public interface MarginAccountAPIService {

    /**
     * 全部杠杆资产
     *
     * @return
     */
    List<Map<String, Object>> getAccounts();

    /**
     * 单个币对杠杆账号资产
     *
     * @param product
     * @return
     */
    Map<String, Object> getAccountsByProductId(@Path("instrument_id") final String product);

    /**
     * 杠杆账单明细
     *
     * @param instrument_id
     * @param type
     * @param before
     * @param after
     * @param limit
     * @return
     */
    List<UserMarginBillDto> getLedger(@Path("instrument_id") final String instrument_id,
                                      @Query("type") final String type,
                                      @Query("before") final String before,
                                      @Query("after") final String after,
                                      @Query("limit") String limit);

    /**
     * 全部币对配置
     *
     * @return
     */
    List<Map<String, Object>> getAvailability();
    /**
     * 单个币对配置
     *
     * @param product
     * @return
     */
    List<Map<String, Object>> getAvailabilityByProductId(@Path("instrument_id") final String product);

    /**
     * 全部借币历史
     *
     * @param status
     * @param before
     * @param after
     * @param limit
     * @return
     */
    List<MarginBorrowOrderDto> getBorrowedAccounts(
            @Query("status") final String status,
            @Query("before") final String before,
            @Query("after") final String after,
            @Query("limit") String limit);

    /**
     * 单个币对借币历史
     * @param status
     * @param before
     * @param after
     * @param limit
     * @param product
     * @return
     */
    List<MarginBorrowOrderDto> getBorrowedAccountsByProductId(@Path("instrument_id") final String instrument_id,
                                                              @Query("before") final String before,
                                                              @Query("after") final String after,
                                                              @Query("limit") final String limit,
                                                              @Query("status") final String status);

    /**
     * 借币
     *
     * @param order
     * @return
     */
    BorrowResult borrow_1(BorrowRequestDto order);

    /**
     * 还币
     *
     * @param order
     * @return
     */
    RepaymentResult repayment_1(RepaymentRequestDto order);
}
