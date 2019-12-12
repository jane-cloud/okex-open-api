#include "okapi.h"

static string AccountPathPrefix = "/api/account/v3/";

string OKAPI::DoTransfer(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, AccountPathPrefix+"transfer",params);
}

string OKAPI::WithDrawals(value &jsonObj) {
    string params = jsonObj.serialize();
    return Request(POST, AccountPathPrefix+"withdrawal",params);
}

string OKAPI::GetCurrencies() {
    return Request(GET, AccountPathPrefix+"currencies");
}

string OKAPI::GetLedger(string type, string currency, string from, string to, string limit) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("type", type));
    m.insert(make_pair("currency", currency));
    m.insert(make_pair("from", from));
    m.insert(make_pair("to", to));
    m.insert(make_pair("limit", limit));
    string request_path = BuildParams(AccountPathPrefix+"ledger", m);
    return Request(method, request_path);
}

string OKAPI::GetWallet() {
    return Request(GET, AccountPathPrefix+"wallet");
}

string OKAPI::GetWalletCurrency(string currency) {
    return Request(GET, AccountPathPrefix+"wallet/"+currency);
}

string OKAPI::GetDepositAddress(string currency) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("currency", currency));
    string request_path = BuildParams(AccountPathPrefix+"deposit/address", m);
    return Request(method, request_path);
}

string OKAPI::GetWithdrawFee() {
    string method(GET);
    return Request(GET, AccountPathPrefix+"withdrawal/fee");

}

string OKAPI::GetWithdrawFee(string currency) {
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("currency", currency));
    string request_path = BuildParams(AccountPathPrefix+"withdrawal/fee", m);
    return Request(method, request_path);
}

string OKAPI::GetWithdrawHistory() {
    return Request(GET, AccountPathPrefix+"withdrawal/history");
}

string OKAPI::GetWithdrawHistoryByCurrency(string currency){
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("currency", currency));
    string request_path = BuildParams(AccountPathPrefix+"withdrawal/history", m);
    return Request(method, request_path);
}

string OKAPI::GetDepositHistory() {
    return Request(GET, AccountPathPrefix+"deposit/history");
}

string OKAPI::GetDepositHistoryByCurrency(string currency){
    string method(GET);
    map<string,string> m;
    m.insert(make_pair("currency", currency));
    string request_path = BuildParams(AccountPathPrefix+"deposit/history", m);
    return Request(method, request_path);
}