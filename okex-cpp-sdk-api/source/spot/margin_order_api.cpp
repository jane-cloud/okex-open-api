#include "../okapi.h"

static string MarginOrderPrefix = "/api/margin/v3/";

string OKAPI::AddOrder(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, MarginOrderPrefix+"orders", params);
}

string OKAPI::AddBatchOrder(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, MarginOrderPrefix+"batch_orders", params);
}
/**
 * Cancle a order
 *
 * @param instrument_id
 * @param order_id
 */
string OKAPI::CancleOrdersByInstrumentIdAndOrderId(string order_id, string instrument_id, string client_oid) {
    string method(POST);
    value obj;
    obj[client_oid] = value::string(client_oid);
    obj[instrument_id] = value::string(instrument_id);
    return Request(method, MarginOrderPrefix+"cancel_orders/"+order_id, obj.serialize());
}


/**
 * Batch cancle order
 *
 * @param instrument_id
 */
string OKAPI::CancleBatchOrders(value &jsonObj) {
    string method(POST);
    return Request(method, MarginOrderPrefix+"cancel_batch_orders", jsonObj.serialize());
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
string OKAPI::GetOrders(string instrument_id, string status, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("instrument_id", instrument_id));
    m.insert(make_pair("status", status));
    if (!from.empty()) {
        m.insert(make_pair("from", from));
    }
    if (!to.empty()) {
        m.insert(make_pair("to", to));
    }
    if (limit.empty()) {
        m.insert(make_pair("limit", limit));
    }
    string request_path = BuildParams(MarginOrderPrefix+"orders", m);
    return Request(method, request_path);
}

/**
 * get a order
 *
 * @param instrument_id
 * @param order_id
 * @return
 */
string OKAPI::GetOrderByInstrumentIdAndOrderId(string order_id, string instrument_id) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("instrument_id", instrument_id));
    string request_path = BuildParams(MarginOrderPrefix+"orders/"+order_id, m);
    return Request(method, request_path);
}

string OKAPI::GetMarginOrdersPending(string from, string to, string limit, string instrument_id) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    m.insert(make_pair("instrument_id", instrument_id));
    string request_path = BuildParams(MarginOrderPrefix+"orders_pending", m);
    return Request(method, request_path);
}

string OKAPI::GetFills(string order_id, string instrument_id,  string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("order_id", order_id));
    m.insert(make_pair("instrument_id", instrument_id));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(MarginOrderPrefix+"fills", m);
    return Request(method, request_path);
}

