#include "../okapi.h"

static string SpotOrderPrefix = "/api/spot/v3/";

string OKAPI::AddSpotOrder(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, SpotOrderPrefix+"orders", params);
}

string OKAPI::AddSpotBatchOrder(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, SpotOrderPrefix+"batch_orders", params);
}

/**
 * Cancle a order
 *
 * @param instrument_id
 * @param order
 */
string OKAPI::CancleSpotOrdersByInstrumentIdAndOrderId(string order_id, value &jsonObj) {
    string method(POST);
    string body = jsonObj.serialize();
    string request_path(SpotOrderPrefix+"orders/" + order_id);
    return Request(method, request_path, body);
}

/**
 * Cancle batch order
 *
 * @param instrument_id
 * @param order_ids
 */
string OKAPI::CancleSpotBatchOrders(value &jsonObj){
    string method(POST);
    string params = jsonObj.serialize();
    return Request(POST, SpotOrderPrefix+"cancel_batch_orders", params);
}

/**
 * get a order
 *
 * @param instrument_id
 * @param order_id
 * @return
 */
string OKAPI::GetSpotOrderByInstrumentIdAndOrderId(string order_id, string instrument_id) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("instrument_id", instrument_id));
    string request_path = BuildParams(SpotOrderPrefix+"orders/"+order_id, m);
    return Request(method, request_path);
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
string OKAPI::GetSpotOrders(string instrument_id, string status, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("instrument_id", instrument_id));
    m.insert(make_pair("status", status));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(SpotOrderPrefix+"orders", m);
    return Request(method, request_path);
}

string OKAPI::GetSpotOrdersPending(string from, string to, string limit, string instrument_id) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    m.insert(make_pair("instrument_id", instrument_id));
    string request_path = BuildParams(SpotOrderPrefix+"orders_pending", m);
    return Request(method, request_path);
}

string OKAPI::GetSpotFills(string order_id, string instrument_id,  string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("order_id", order_id));
    m.insert(make_pair("instrument_id", instrument_id));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(SpotOrderPrefix+"fills", m);
    return Request(method, request_path);
}

