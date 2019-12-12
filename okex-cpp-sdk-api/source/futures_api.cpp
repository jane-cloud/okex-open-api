#include "okapi.h"

/*
 =============================== Futures General Api ===============================
*/

void OKAPI::TestRequest() {
    auto fileStream = std::make_shared<concurrency::streams::ostream>();
    concurrency::streams::ostream outFile = concurrency::streams::fstream::open_ostream(U("result10.html")).get();
    *fileStream = outFile;

    http_client client("https://www.okex.com/");
    uri_builder builder("/api/general/v3/time");
    builder.set_host("www.okex.com/");

    http_response response = client.request(methods::GET, builder.to_string()).get();
    response.body().read_to_end(fileStream->streambuf()).get();
    fileStream->close().get();
}

string OKAPI::GetServerTime() {
    string method("GET");
    string request_path("/api/general/v3/time");
    string str = Request(method, request_path);
    return str;
}

string OKAPI::GetExchangeRate() {
    string method("GET");
    string request_path("/api/general/v3/exchange_rate");
    string str = Request(method, request_path);
    return str;
}

/*
 =============================== Futures Market Api ===============================
*/
/*
  Get all of futures contract list
 */
string OKAPI::GetFuturesInstruments() {
    string method("GET");
    string request_path(FuturesPathPrefix+"instruments");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract currencies
 */
string OKAPI::GetFuturesInstrumentCurrencies(){
    string method("GET");
    string request_path(FuturesPathPrefix+"instruments/currencies");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract instrument book
 depth value：1-200
 merge value：1(merge depth)
*/
string OKAPI::GetFuturesInstrumentBook(string &instrument_id, int book) {
    string method("GET");
    map<string,string> m;
    m.insert(make_pair("book", to_string(book)));
    string request_path = BuildParams(FuturesPathPrefix+"instruments/"+instrument_id+"/book", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract instrument all tickers
 */
string OKAPI::GetFuturesTicker() {
    string method("GET");
    string request_path(FuturesPathPrefix+"instruments/ticker");
    string str = Request(method, request_path);
    return str;
}


/*
 Get the futures contract instrument ticker
 */
string OKAPI::GetFuturesInstrumentTicker(string &instrument_id) {
    string method("GET");
    string request_path(FuturesPathPrefix+"instruments/"+instrument_id+"/ticker");
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract instrument trades
 */
string OKAPI::GetFuturesInstrumentTrades(string &instrument_id,  string from, string to, string limit) {
    string method("GET");
    map<string,string> m;
    if (!from.empty())
        m.insert(make_pair("from", from));
    if (!to.empty())
        m.insert(make_pair("to", to));
    if (!limit.empty())
        m.insert(make_pair("limit", limit));
    string request_path = BuildParams(FuturesPathPrefix+"instruments/"+instrument_id+"/trades", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract instrument candles
 */
string OKAPI::GetFuturesInstrumentCandles(string instrument_id, string start, string end, int granularity) {
    string method("GET");
    map<string,string> m;
    m.insert(make_pair("start", start));
    m.insert(make_pair("end", end));
    m.insert(make_pair("granularity", to_string(granularity)));
    string request_path = BuildParams(FuturesPathPrefix+"instruments/"+instrument_id+"/candles", m);
    string str = Request(method, request_path);
    return str;
}

/*
 Get the futures contract index
 */
string OKAPI::GetFuturesIndex(string instrument_id) {
    return Request(GET, FuturesPathPrefix + "instruments/" + instrument_id + "/index");
}

/*
 Get the futures contract index
 */
string OKAPI::GetFuturesRate() {
    return Request(GET, FuturesPathPrefix+"rate");
}

/*
 Get the futures contract instrument index
 */
string OKAPI::GetFuturesInstrumentIndex(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"instruments/"+instrument_id+"/index");
}

/*
 Get the futures contract instrument estimated price
 */
string OKAPI::GetFuturesInstrumentEstimatedPrice(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"instruments/"+instrument_id+"/estimated_price");
}

/*
 Get the futures contract instrument open interest
 */
string OKAPI::GetFuturesInstrumentOpenInterest(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"instruments/"+instrument_id+"/open_interest");
}

/*
 Get the futures contract instrument holds
 */
string OKAPI::GetFuturesInstrumentHolds(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"accounts/"+instrument_id+"/holds");
}

/*
 Get the futures contract instrument limit price
 */
string OKAPI::GetFuturesInstrumentPriceLimit(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"instruments/"+instrument_id+"/price_limit");
}

/*
 Get the futures contract instrument mark price
 */
string OKAPI::GetFuturesInstrumentMarkPrice(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"instruments/"+instrument_id+"/mark_price");
}

/*
 Get the futures contract liquidation
 */
string OKAPI::GetFuturesInstrumentLiquidation(string instrument_id, int status, string from, string to, string limit) {
    string method("GET");
    map<string,string> m;
    m.insert(make_pair("status", to_string(status)));
    if (!from.empty()) {
        m.insert(make_pair("from", from));
    }
    if (!to.empty()) {
        m.insert(make_pair("to", to));
    }
    if (!limit.empty()) {
        m.insert(make_pair("limit", limit));
    }
    string request_path = BuildParams(FuturesPathPrefix+"instruments/"+instrument_id+"/liquidation", m);
    string str = Request(method, request_path);
    return str;
}


/*
 =============================== Futures Trade Api ===============================
*/

/*
 Get all of futures contract position list.
 return struct: FuturesPositions
*/
string OKAPI::GetFuturesPositions() {
    return Request(GET, FuturesPathPrefix+"position");
}

/*
 Get all of futures contract position list.
 return struct: FuturesPositions
*/
string OKAPI::GetFuturesInstrumentPosition(string instrument_id) {
    return Request(GET, FuturesPathPrefix+instrument_id+"/position");
}

/*
 Get all of futures contract account list
 return struct: FuturesAccounts
*/
string OKAPI::GetFuturesAccounts() {
    return Request(GET, FuturesPathPrefix+"accounts");
}

/*
 Get the futures contract currency account
*/
string OKAPI::GetFuturesAccountsByCurrency(string currency) {
    return Request(GET, FuturesPathPrefix+"accounts/"+currency);
}

/*
 Get the futures contract currency leverage
*/
string OKAPI::GetFuturesLeverageByCurrency(string currency) {
    return Request(GET, FuturesPathPrefix+"accounts/"+currency+"/leverage");
}

/*
 Set the futures contract currency leverage
*/
string OKAPI::SetFuturesLeverageByCurrency(string currency, value &obj) {
    string params = obj.serialize();
    return Request(POST, FuturesPathPrefix+"accounts/"+currency+"/leverage" + params);
}


/*
 Get the futures contract currency ledger
*/
string OKAPI::GetFuturesAccountsLedgerByCurrency(string currency) {
    return Request(GET, FuturesPathPrefix+"accounts/"+currency+"/ledger");
}

/*
 Get the futures contract instrument holds
*/
string OKAPI::GetFuturesAccountsHoldsByInstrumentId(string instrument_id) {
    return Request(GET, FuturesPathPrefix+"accounts/"+instrument_id+"/holds");
}

/*
 Create a new order
*/
string OKAPI::FuturesOrder(value &obj) {
    string params = obj.serialize();
    return Request(POST, FuturesPathPrefix+"order", params);
}

/*
 Batch create new order.(Max of 5 orders are allowed per request)
*/
string OKAPI::FuturesOrders(value &obj) {
    string params = obj.serialize();
    return Request(POST, FuturesPathPrefix+"orders", params);
}

/*
 Cancel the order
*/
string OKAPI::CancelFuturesInstrumentOrder(string instrument_id, string order_id) {
    return Request(POST, FuturesPathPrefix+"cancel_order/"+instrument_id+"/"+order_id);
}

/*
 Batch Cancel the orders
*/
string OKAPI::CancelFuturesInstrumentOrders(string instrument_id, value& jsonObj) {
    return Request(POST, FuturesPathPrefix+"cancel_batch_orders/"+instrument_id, jsonObj.serialize());
}

/*
 Get the futures contract instrument order
*/
string OKAPI::GetFuturesOrder(string instrument_id, string order_id) {
    return Request(GET, FuturesPathPrefix+"orders/"+instrument_id+"/"+order_id);
}

/*
close position
*/
string OKAPI::FuturesClosePositionParams(value &obj) {
    string params = obj.serialize();
    return Request(POST, FuturesPathPrefix+"close_position", params);
}

/*
 Get all of futures contract order list
*/
string OKAPI::GetFuturesOrderList(string status, string instrument_id, string from, string to, string limit) {
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
    string requestPath = FuturesPathPrefix+"orders";
    if (!instrument_id.empty()) {
        requestPath += "/";
        requestPath += instrument_id;
    }
    string request_path = BuildParams(requestPath, m);
    return Request(method, request_path);
}

/*
 Get all of futures contract a order by order id
*/
string OKAPI::GetFuturesOrder(string order_id) {
    return Request(GET, FuturesPathPrefix+"orders/"+order_id);
}

/*
 Get all of futures contract transactions.
*/
string OKAPI::GetFuturesFills(string instrument_id, string order_id, string from, string to, string limit) {
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
    string request_path = BuildParams(FuturesPathPrefix+"fills", m);
    return Request(method, request_path);
}

/*
 Get futures contract account volume
*/
string OKAPI::GetFuturesUsersSelfTrailingVolume() {
    return Request(GET, FuturesPathPrefix+"users/self/trailing_volume");
}

