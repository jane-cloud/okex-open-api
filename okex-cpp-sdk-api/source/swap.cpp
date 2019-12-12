//
// Created by zxp on 18/12/18.
//
#include "okapi.h"

/*
 Get all of futures contract position list.
*/
string OKAPI::GetSwapPositions() {
    return Request(GET, SwapPathPrefix+"position");
}

/*
 Get swap contract position list.
*/
string OKAPI::GetSwapInstrumentPosition(string instrument_id) {
    return Request(GET, SwapPathPrefix+instrument_id+"/position");
}

/*
 Get all of swap contract account list
*/
string OKAPI::GetSwapAccounts() {
    return Request(GET, SwapPathPrefix+"accounts");
}

/*
 Get the swap contract instrument account
*/
string OKAPI::GetSwapAccountsByInstrumentId(string instrument_id) {
    return Request(GET, SwapPathPrefix+instrument_id+"/accounts");
}

/*
 Get the swap contract settings
*/
string OKAPI::GetSwapSettingsByInstrumentId(string instrument_id) {
    return Request(GET, SwapPathPrefix+"/accounts/"+instrument_id+"/settings");
}

/*
 Get the swap contract instrument leverage
*/
//string OKAPI::GetSwapLeverageByInstrumentId(string instrument_id) {
//    return Request(GET, SwapPathPrefix+"accounts/"+instrument_id+"/leverage");
//}

/*
 Set the swap contract instrument leverage
*/
string OKAPI::SetSwapLeverageByInstrumentId(string instrument_id, value &obj) {
    string params = obj.serialize();
    return Request(POST, SwapPathPrefix+"accounts/"+instrument_id+"/leverage" + params);
}

/*
 Get the swap contract instrument ledger
*/
string OKAPI::GetSwapAccountsLedgerByInstrumentId(string instrument_id, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    if (!from.empty())
        m.insert(make_pair("from", from));
    if (!to.empty())
        m.insert(make_pair("to", to));
    if (!limit.empty())
        m.insert(make_pair("limit", limit));
    string requestPath = SwapPathPrefix+"accounts/"+instrument_id+"/ledger";
    string request_path = BuildParams(requestPath, m);
    return Request(method, request_path);
}

/*
 Create a new order
*/
string OKAPI::AddSwapOrder(value &obj) {
    string params = obj.serialize();
    return Request(POST, SwapPathPrefix+"order", params);
}

/*
 Batch create new order.(Max of 5 orders are allowed per request)
*/
string OKAPI::AddBatchSwapOrders(value &obj) {
    string params = obj.serialize();
    return Request(POST, SwapPathPrefix+"orders", params);
}

/*
 Cancel the order
*/
string OKAPI::CancelSwapInstrumentOrder(string instrument_id, string order_id) {
    return Request(POST, SwapPathPrefix+"cancel_order/"+instrument_id+"/"+order_id);
}

/*
 Batch Cancel the orders
*/
string OKAPI::CancelSwapInstrumentOrders(string instrument_id, value& obj) {
    return Request(POST, SwapPathPrefix+"cancel_batch_orders/"+instrument_id, obj.serialize());
}

/*
 Get all of swap contract order list
*/
string OKAPI::GetSwapOrderList(string status, string instrument_id, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    if (!status.empty())
        m.insert(make_pair("status", status));
    if (!from.empty())
        m.insert(make_pair("from", from));
    if (!to.empty())
        m.insert(make_pair("to", to));
    if (!limit.empty())
        m.insert(make_pair("limit", limit));
    string requestPath = SwapPathPrefix+"orders";
    if (!instrument_id.empty()) {
        requestPath += "/";
        requestPath += instrument_id;
    }
    string request_path = BuildParams(requestPath, m);
    return Request(method, request_path);
}

/*
 Get the swap contract instrument order
*/
string OKAPI::GetSwapOrder(string instrument_id, string order_id) {
    return Request(GET, SwapPathPrefix+"orders/"+instrument_id+"/"+order_id);
}

/*
 Get all of swap contract transactions.
*/
string OKAPI::GetSwapFills(string instrument_id, string order_id, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("instrument_id", instrument_id));
    m.insert(make_pair("order_id", order_id));
    if (!from.empty())
        m.insert(make_pair("from", from));
    if (!to.empty())
        m.insert(make_pair("to", to));
    if (!limit.empty())
        m.insert(make_pair("limit", limit));
    string request_path = BuildParams(SwapPathPrefix+"fills", m);
    return Request(method, request_path);
}

/*
  Get all of swap contract list
 */
string OKAPI::GetSwapInstruments() {
    string method("GET");
    string request_path(SwapPathPrefix+"instruments");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument book
 depth value：1-200
 merge value：1(merge depth)
*/
string OKAPI::GetSwapInstrumentDepth(string &instrument_id, string size) {
    string method("GET");
    map<string,string> m;
    if (!size.empty()) {
        m.insert(make_pair("size", size));
    }
    string request_path = BuildParams(SwapPathPrefix+"instruments/"+instrument_id+"/depth", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument all tickers
 */
string OKAPI::GetSwapTicker() {
    string method("GET");
    string request_path(SwapPathPrefix+"instruments/ticker");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument ticker
 */
string OKAPI::GetSwapInstrumentTicker(string &instrument_id) {
    string method("GET");
    string request_path(SwapPathPrefix+"instruments/"+instrument_id+"/ticker");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument trades
 */
string OKAPI::GetSwapInstrumentTrades(string &instrument_id, string from, string to, string limit) {
    string method("GET");
    map<string,string> m;
    if (!from.empty()) {
        m.insert(make_pair("from", from));
    }
    if (!to.empty()) {
        m.insert(make_pair("to", to));
    }
    if (!limit.empty()) {
        m.insert(make_pair("limit", limit));
    }
    string request_path = BuildParams(SwapPathPrefix+"instruments/"+instrument_id+"/trades", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument candles
 */
string OKAPI::GetSwapInstrumentCandles(string instrument_id, string start, string end, int granularity) {
    string method("GET");
    map<string,string> m;
    m.insert(make_pair("start", start));
    m.insert(make_pair("end", end));
    m.insert(make_pair("granularity", to_string(granularity)));
    string request_path = BuildParams(SwapPathPrefix+"instruments/"+instrument_id+"/candles", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract index
 */
string OKAPI::GetSwapIndex(string instrument_id) {
    return Request(GET, SwapPathPrefix + "instruments/" + instrument_id + "/index");
}

/*
 Get the swap contract index
 */
string OKAPI::GetSwapRate() {
    return Request(GET, SwapPathPrefix+"rate");
}

/*
 Get the swap contract instrument estimated price
 */
//string OKAPI::GetSwapInstrumentEstimatedPrice(string instrument_id) {
//    return Request(GET, SwapPathPrefix+"instruments/"+instrument_id+"/estimated_price");
//}

/*
 Get the swap contract instrument open interest
 */
string OKAPI::GetSwapInstrumentOpenInterest(string instrument_id) {
    return Request(GET, SwapPathPrefix+"instruments/"+instrument_id+"/open_interest");
}

/*
 Get the swap contract instrument limit price
 */
string OKAPI::GetSwapInstrumentPriceLimit(string instrument_id) {
    return Request(GET, SwapPathPrefix+"instruments/"+instrument_id+"/price_limit");
}

/*
 Get the swap contract liquidation
 */
string OKAPI::GetSwapInstrumentLiquidation(string instrument_id, string status, string from, string to, string limit) {
    string method("GET");
    map<string,string> m;
    m.insert(make_pair("status", status));
    if (!from.empty()) {
        m.insert(make_pair("from", from));
    }
    if (!to.empty()) {
        m.insert(make_pair("to", to));
    }
    if (!limit.empty()) {
        m.insert(make_pair("limit", limit));
    }
    string request_path = BuildParams(SwapPathPrefix+"instruments/"+instrument_id+"/liquidation", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the swap contract instrument holds
 */
string OKAPI::GetSwapInstrumentHolds(string instrument_id) {
    return Request(GET, SwapPathPrefix+"accounts/"+instrument_id+"/holds");
}

/*
 Get the swap contract funding time
 */
string OKAPI::GetSwapFundingTime(string instrument_id) {
    return Request(GET, SwapPathPrefix+"instruments/"+instrument_id+"/funding_time");
}

/*
 Get the swap contract mark price
 */
string OKAPI::GetSwapMarketPrice(string instrument_id) {
    return Request(GET, SwapPathPrefix+"instruments/"+instrument_id+"/mark_price");
}

string OKAPI::GetSwapHistoricalFundingRate(string instrument_id, string from, string to, string limit) {
    string method("GET");
    map<string,string> m;
    if (!from.empty()) {
        m.insert(make_pair("from", from));
    }
    if (!to.empty()) {
        m.insert(make_pair("to", to));
    }
    if (!limit.empty()) {
        m.insert(make_pair("limit", limit));
    }
    string request_path = BuildParams(SwapPathPrefix+"instruments/"+instrument_id+"/historical_funding_rate", m);
    string str = Request(method, request_path);
    return str;
}




