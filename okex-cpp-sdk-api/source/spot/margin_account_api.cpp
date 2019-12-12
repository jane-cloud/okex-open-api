#include "../okapi.h"

static string MarginAccountPrefix = "/api/margin/v3/";

/**
 * 全部杠杆资产
 *
 * @return
 */
string OKAPI::GetAccounts() {
    return Request(GET, MarginAccountPrefix+"accounts");
}

/**
 * 单个币对杠杆账号资产
 *
 * @param instrument
 * @return
 */
string OKAPI::GetAccountsByInstrumentId(string instrument_id) {
    return Request(GET, MarginAccountPrefix+"accounts/"+instrument_id);
}


/**
 * 杠杆账单明细
 *
 * @param beginDate
 * @param endDate
 * @param isHistory
 * @param instrument
 * @param currencyId
 * @param type
 * @param from
 * @param to
 * @param limit
 * @return
 */
string OKAPI::GetMarginLedger(string instrument_id, string beginDate, string endDate, string isHistory, string currencyId, \
string type, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("beginDate", beginDate));
    m.insert(make_pair("endDate", endDate));
    m.insert(make_pair("isHistory", isHistory));
    m.insert(make_pair("currencyId", currencyId));
    m.insert(make_pair("type", type));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(MarginAccountPrefix+"accounts/"+instrument_id+"/ledger", m);
    return Request(method, request_path);
}

/**
 * 全部币对配置
 *
 * @return
 */
string OKAPI::GetMarginInfo() {
    return Request(GET, MarginAccountPrefix+"accounts/availability");
}


/**
 * 单个币对配置
 *
 * @param instrument
 * @return
 */
string OKAPI::GetMarginInfoByInstrumentId(string instrument_id) {
    return Request(GET, MarginAccountPrefix+"accounts/"+instrument_id+"/availability");
}

/**
 * 全部借币历史
 * @param status
 * @param from
 * @param to
 * @param limit
 * @return
 */
string OKAPI::GetBorrowAccounts(string status, string type, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("status", status));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(MarginAccountPrefix+"accounts/borrow", m);
    return Request(method, request_path);
}

/**
 * 单个币对借币历史
 * @param status
 * @param from
 * @param to
 * @param limit
 * @param instrument
 * @return
 */
string OKAPI::GetBorrowAccountsByInstrumentId(string instrument_id, string status, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("status", status));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(MarginAccountPrefix+"accounts/"+instrument_id+"/borrow", m);
    return Request(method, request_path);
}


/**
 * 借币
 * @param instrument
 * @param currency
 * @param amount
 * @return
 */
string OKAPI::Borrow(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, MarginAccountPrefix+"accounts/borrow", params);
}

/**
 * 还币
 * @param instrument
 * @param currency
 * @param amount
 * @param borrow_id
 * @return
 */
string OKAPI::Repayment(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, MarginAccountPrefix+"accounts/repayment", params);
}