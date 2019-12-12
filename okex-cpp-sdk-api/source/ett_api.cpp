#include "okapi.h"

static string EttPrefix = "/api/ett/v3/";

string OKAPI::GetEttAccounts() {
    return Request(GET, EttPrefix+"accounts");
}

string OKAPI::GetEttAccountByCurrency(string currency) {
    return Request(GET, EttPrefix+"accounts/"+currency);
}

string OKAPI::GetEttLedgersByCurrency(string currency, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(EttPrefix+"accounts/"+currency+"ledger", m);
    return Request(method, request_path);
}


/**
 * Create a order
 *
 * @param order
 */
string OKAPI::AddEttOrder(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, EttPrefix+"orders", params);
}

/**
 * Cancle a order
 *
 * @param instrument_id
 * @param order
 */
string OKAPI::CancleEttOrderByOrderId(string order_id) {
    return Request(DELETE, EttPrefix+"orders/"+order_id);
}

/**
 * get a order
 *
 * @param instrument_id
 * @param order_id
 * @return
 */
string OKAPI::GetEttOrderByOrderId(string order_id) {
    return Request(GET, EttPrefix+"orders/"+order_id);
}

/**
 * get order list
 *
 * @param instrument_id
 * @param status    pending、done、archive
 * @param from
 * @param to
 * @param limit
 * @return
 */
string OKAPI::GetEttOrders(string ett, string type, string status, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("ett", ett));
    m.insert(make_pair("type", type));
    m.insert(make_pair("status", status));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(EttPrefix+"orders", m);
    return Request(method, request_path);
}


string OKAPI::GetEttConstituents(string ett) {
    return Request(GET, EttPrefix+"constituents/"+ett);
}

string OKAPI::GetEttDefinePrice(string ett) {
    return Request(GET, EttPrefix+"define-price/"+ett);
}