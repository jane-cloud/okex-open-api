//
// Created by zxp on 13/07/18.
//

#ifndef CPPSDK_OKAPI_H
#define CPPSDK_OKAPI_H
#include <iostream>
#include <string>
#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include "utils.h"
#include "constants.h"

using namespace std;
using namespace web;
using namespace web::http;
using namespace web::http::client;
using namespace web::json;

struct Config{
    // Rest api endpoint url. eg: http://www.okex.com/
    string Endpoint;
    // The user's api key provided by OKEx.
    string ApiKey;
    // The user's secret key provided by OKEx. The secret key used to sign your request data.
    string SecretKey;
    // The Passphrase will be provided by you to further secure your API access.
    string Passphrase;
    // Http request timeout.
    int TimeoutSecond;
    // Whether to print API information
    bool IsPrint;
    // Internationalization @see file: constants.go
    string  I18n;
};

struct ServerTime {
    string Iso;
    string Epoch;
};

class OKAPI {
public:
    OKAPI() {};
    OKAPI(struct Config &cf) {SetConfig(cf);};
    ~OKAPI() {};
    void SetConfig(struct Config &cf);
    string Request(const string &method, const string &requestPath, const string &params="");
    string GetSign(string timestamp, string method, string requestPath, string body);

    void TestRequest();
    /************************** REST API ***************************/
    string GetServerTime();
    string GetExchangeRate();

    /************************** Futures API ***************************/
    string GetFuturesPositions();
    string GetFuturesInstrumentPosition(string instrument_id);
    string GetFuturesAccountsByCurrency(string currency);
    string GetFuturesLeverageByCurrency(string currency);
    string SetFuturesLeverageByCurrency(string currency, value &obj);
    string GetFuturesAccountsLedgerByCurrency(string currency);

    string FuturesOrder(value &obj);
    string FuturesOrders(value &obj);
    string CancelFuturesInstrumentOrder(string instrument_id, string order_id);
    string CancelFuturesInstrumentOrders(string instrument_id, value& jsonObj);

    string GetFuturesOrderList(string status, string instrument_id, string from="", string to="", string limit="");
    string GetFuturesOrder(string instrument_id, string order_id);
    string GetFuturesFills(string instrument_id, string order_id, string from="", string to="", string limit="");
    string GetFuturesInstruments();
    string GetFuturesInstrumentBook(string &instrument_id, int book);
    string GetFuturesTicker();
    string GetFuturesInstrumentTicker(string &instrument_id);
    string GetFuturesInstrumentTrades(string &instrument_id, string from = "", string to = "", string limit = "");
    string GetFuturesInstrumentCandles(string instrument_id, string start="", string end="", int granularity=604800);
    string GetFuturesIndex(string instrument_id);
    string GetFuturesRate();
    string GetFuturesInstrumentEstimatedPrice(string instrument_id);
    string GetFuturesInstrumentOpenInterest(string instrument_id);
    string GetFuturesInstrumentPriceLimit(string instrument_id);
    string GetFuturesInstrumentMarkPrice(string instrument_id);
    string GetFuturesInstrumentLiquidation(string instrument_id, int status, string from = "", string to = "", string limit = "");
    string GetFuturesInstrumentHolds(string instrument_id);

    // 以下暂未在文档中描述
    string GetFuturesOrder(string order_id);
    string GetFuturesInstrumentCurrencies();
    string GetFuturesInstrumentIndex(string instrument_id);
    string GetFuturesAccounts();
    string GetFuturesAccountsHoldsByInstrumentId(string instrument_id);
    string FuturesClosePositionParams(value &obj);
    string GetFuturesUsersSelfTrailingVolume();

    /************************** Swap API ***************************/
    string GetSwapPositions();
    string GetSwapInstrumentPosition(string instrument_id);
    string GetSwapAccounts();
    string GetSwapAccountsByInstrumentId(string instrument_id);
    string GetSwapSettingsByInstrumentId(string instrument_id);
    string SetSwapLeverageByInstrumentId(string instrument_id, value &obj);
    string GetSwapAccountsLedgerByInstrumentId(string instrument_id, string from="", string to="", string limit="");

    string AddSwapOrder(value &obj);
    string AddBatchSwapOrders(value &obj);
    string CancelSwapInstrumentOrder(string instrument_id, string order_id);
    string CancelSwapInstrumentOrders(string instrument_id, value& obj);

    string GetSwapOrderList(string status, string instrument_id, string from="", string to="", string limit="");
    string GetSwapOrder(string instrument_id, string order_id);
    string GetSwapFills(string instrument_id, string order_id, string from="", string to="", string limit="");
    string GetSwapInstruments();
    string GetSwapInstrumentDepth(string &instrument_id, string size);
    string GetSwapTicker();
    string GetSwapInstrumentTicker(string &instrument_id);
    string GetSwapInstrumentTrades(string &instrument_id, string from="", string to="", string limit="");
    string GetSwapInstrumentCandles(string instrument_id, string start="", string end="", int granularity=604800);
    string GetSwapIndex(string instrument_id);
    string GetSwapRate();
    string GetSwapInstrumentEstimatedPrice(string instrument_id);
    string GetSwapInstrumentOpenInterest(string instrument_id);
    string GetSwapInstrumentPriceLimit(string instrument_id);
    string GetSwapInstrumentLiquidation(string instrument_id, string status, string from="", string to="", string limit="");
    string GetSwapInstrumentHolds(string instrument_id);
    string GetSwapFundingTime(string instrument_id);
    string GetSwapMarketPrice(string instrument_id);
    string GetSwapHistoricalFundingRate(string instrument_id, string from="", string to="", string limit="");

    /************************** Account API ***************************/
    string GetCurrencies();
    string GetWallet();
    string GetWalletCurrency(string currency);
    string DoTransfer(value &jsonObj);
    string WithDrawals(value &jsonObj);
    string GetWithdrawFee();
    string GetWithdrawFee(string currency);
    string GetWithdrawHistory();
    string GetWithdrawHistoryByCurrency(string currency);
    string GetLedger(string type, string currency, string from, string to, string limit);
    string GetDepositAddress(string currency);
    string GetDepositHistory();
    string GetDepositHistoryByCurrency(string currency);

     /************************** Margin Account API ***************************/
    string GetAccounts();
    string GetAccountsByInstrumentId(string instrument_id);
    string GetMarginLedger(string instrument_id, string beginDate, string endDate, string isHistory, string currencyId, string type, string from, string to, string limit);
    string GetMarginInfo();
    string GetBorrowAccounts(string status, string type, string from, string to, string limit);
    string GetMarginInfoByInstrumentId(string instrument_id);
    string GetBorrowAccountsByInstrumentId(string instrument_id, string status, string from, string to, string limit);
    string Borrow(value &jsonObj);
    string Repayment(value &jsonObj);

    /************************** Margin Order API ***************************/
    string AddOrder(value &jsonObj);
    string AddBatchOrder(value &jsonObj);
    string CancleOrdersByInstrumentIdAndOrderId(string order_id, string instrument_id, string client_oid = "");
    string CancleBatchOrders(value &jsonObj);
    string GetOrders(string instrument_id, string status, string from = "", string to = "", string limit = "");
    string GetOrderByInstrumentIdAndOrderId(string order_id, string instrument_id);
    string GetMarginOrdersPending(string from, string to, string limit, string instrument_id);
    string GetFills(string order_id, string instrument_id,  string from, string to, string limit);

    /************************** Spot Account API ***************************/
    string GetSpotAccounts();
    string GetSpotAccountByCurrency(string currency);
    string GetSpotTime();
    string GetSpotLedgersByCurrency(string currency, string from, string to, string limit);

    /************************** Spot Order API ***************************/
    string AddSpotOrder(value &jsonObj);
    string AddSpotBatchOrder(value &jsonObj);
    string CancleSpotOrdersByInstrumentIdAndOrderId(string order_id, value &jsonObj);
    string CancleSpotBatchOrders(value &jsonObj);
    string GetSpotOrders(string instrument_id, string status, string from, string to, string limit);
    string GetSpotOrdersPending(string from, string to, string limit, string instrument_id);
    string GetSpotOrderByInstrumentIdAndOrderId(string order_id, string instrument_id);
    string GetSpotFills(string order_id, string instrument_id, string from, string to, string limit);

    /************************** Spot Product API ***************************/
    string GetInstruments();
    string GetInstrumentsByInstrumentId(string instrument_id, string size, string depth);
    string GetTickers();
    string GetTickerByInstrumentId(string instrument_id);
    string GetTrades(string instrument_id, string from, string to, string limit);
    string GetCandles(string instrument_id, int granularity, string start, string end);

    /************************** Ett API ***************************/
    string GetEttAccounts();
    string GetEttAccountByCurrency(string currency);
    string GetEttLedgersByCurrency(string currency, string from, string to, string limit);
    string AddEttOrder(value &jsonObj);
    string CancleEttOrderByOrderId(string order_id);
    string GetEttOrders(string ett, string type, string status, string from, string to, string limit);
    string GetEttOrderByOrderId(string order_id);
    string GetEttConstituents(string ett);
    string GetEttDefinePrice(string ett);

private:
    struct Config m_config;
};


#endif //CPPSDK_OKAPI_H
