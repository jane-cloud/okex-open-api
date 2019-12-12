#include "../okapi.h"

static string SpotAccountPrefix = "/api/spot/v3/";

string OKAPI::GetSpotTime() {
    return Request(GET, SpotAccountPrefix+"time");
}

    string OKAPI::GetSpotAccounts() {
    return Request(GET, SpotAccountPrefix+"accounts");
}

string OKAPI::GetSpotAccountByCurrency(string currency) {
    return Request(GET, SpotAccountPrefix+"accounts/"+currency);
}

string OKAPI::GetSpotLedgersByCurrency(string currency, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("currency", currency));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(SpotAccountPrefix+"accounts/"+currency+"/ledger", m);
    return Request(method, request_path);
}

