#include "../okapi.h"

static string SpotProductPrefix = "/api/spot/v3/";

/**
 * 全部币对信息
 *
 * @return
 */
string OKAPI::GetInstruments(){
    return Request(GET, SpotProductPrefix+"instruments");
}


string OKAPI::GetInstrumentsByInstrumentId(string instrument_id, string size, string depth) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("size", size));
    m.insert(make_pair("depth", depth));
    string request_path = BuildParams(SpotProductPrefix+"instruments/"+instrument_id+"/book", m);
    return Request(method, request_path);
}

string OKAPI::GetTickers() {
    return Request(GET, SpotProductPrefix+"instruments/ticker");
}

string OKAPI::GetTickerByInstrumentId(string instrument_id) {
    return Request(GET, SpotProductPrefix+"instruments/"+instrument_id+"/ticker");
}

string OKAPI::GetTrades(string instrument_id, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(SpotProductPrefix+"instruments/"+instrument_id+"/trades", m);
    return Request(method, request_path);
}

string OKAPI::GetCandles(string instrument_id, int granularity, string start, string end) {
    string method(GET);
    value obj;
    map<string,string> m;
    m.insert(make_pair("granularity", to_string(granularity)));
    m.insert(make_pair("start", start));
    m.insert(make_pair("end", end));
    string request_path = BuildParams(SpotProductPrefix+"instruments/"+instrument_id+"/candles", m);
    return Request(method, request_path);
}
