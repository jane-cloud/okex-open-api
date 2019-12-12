#include "okapi.h"
#include <ctime>
#include "algo_hmac.h"
#include "base64.hpp"
#include "utils.h"
#include "constants.h"

using namespace websocketpp;

void OKAPI::SetConfig(struct Config &cf) {
    m_config.Passphrase = cf.Passphrase;
    m_config.IsPrint = cf.IsPrint;
    m_config.I18n = cf.I18n;
    m_config.Endpoint = cf.Endpoint;
    m_config.ApiKey = cf.ApiKey;
    m_config.SecretKey = cf.SecretKey;
}

string OKAPI::GetSign(string timestamp, string method, string requestPath, string body) {
    string sign;
    unsigned char * mac = NULL;
    unsigned int mac_length = 0;
    string data = timestamp + method + requestPath + body;
    string key = m_config.SecretKey;
    int ret = HmacEncode("sha256", key.c_str(), key.length(), data.c_str(), data.length(), mac, mac_length);
    sign = base64_encode(mac, mac_length);
    return sign;
}

string OKAPI::Request(const string &method, const string &requestPath, const string &params) {
    /************************** set request method ***************************/
    http_request request;
    /************************** set request uri ***************************/
    uri_builder builder;
    builder.append_path(requestPath);

    request.set_method(method);
    request.set_request_uri(builder.to_uri());
    request._set_base_uri("www.okex.com");

    /************************** set request headers ***************************/
    char * timestamp = new char[32];
    timestamp = GetTimestamp(timestamp, 32);
    string sign = GetSign(timestamp, method, builder.to_string(), params);
    request.headers().clear();
    request.headers().add(U("OK-ACCESS-KEY"), m_config.ApiKey);
    request.headers().add(U("OK-ACCESS-SIGN"), sign);
    request.headers().add(U("OK-ACCESS-TIMESTAMP"), timestamp);
    request.headers().add(U("OK-ACCESS-PASSPHRASE"), m_config.Passphrase);
    request.headers().add(U("Accept"), U("application/json"));
    request.headers().set_content_type(U("application/json; charset=UTF-8"));
    request.headers().add(U("Cookie"),U("locale="+m_config.I18n));
    request.headers().add(U("Host"),U("www.okex.com"));

    /************************** set request body ***************************/
    request.set_body(params,"application/json; charset=UTF-8");
    /************************** print request ***************************/
    if (m_config.IsPrint) {
        cout << "request:\n" << request.to_string().c_str() << endl;
        if (!params.empty()) {
            cout << "body:\n" << JsonFormat(params) << endl;
        }
    }
    /************************** get response ***************************/
    http_response response;
    string str;
    try {
        http_client client(m_config.Endpoint);
        // Wait for headers
        response = client.request(request).get();

        // Wait for data
        response.content_ready().wait();
    }
    catch (std::exception& ex) {
        cout << "Exception: " << ex.what() << endl;
        exit(0);
    }
    try
    {
        str = response.extract_string(true).get();  // get() is needed!
    }
    catch (const std::exception& e)
    {
        cout << e.what() << endl;
    }

    if (m_config.IsPrint) {
        cout << "response:\n" << str << endl;
        // json format
        cout << "body:\n" << JsonFormat(str) << endl;
        cout << endl << endl;

        /************************** print response ***************************/
        auto fp = fopen("result.txt", "a");
        fputs("request:\n", fp);
        fputs(method.c_str(), fp);
        fputs(requestPath.c_str(), fp);
        fputs("\nresponse:\n", fp);
        fputs(str.c_str(), fp);
        fputs("\n\n", fp);
        fclose(fp);
    }

    delete []timestamp;
    return str;
}