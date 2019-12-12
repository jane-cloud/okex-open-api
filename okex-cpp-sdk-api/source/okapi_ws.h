//
// Created by zxp on 28/12/18.
//

#ifndef OKAPI_OKAPI_WS_H
#define OKAPI_OKAPI_WS_H

#include <string>
#include <iostream>


class okapi_ws {
private:
public:
    static void RequestWithoutLogin(std::string url, std::string channels, std::string op);
    static void Request(std::string url, std::string channels, std::string op, std::string api_key, std::string passphrase, std::string secret_key);

    static void SubscribeWithoutLogin(std::string url, std::string channels);
    static void UnsubscribeWithoutLogin(std::string url, std::string channels);
    static void Subscribe(std::string url, std::string channels, std::string api_key, std::string passphrase, std::string secret_key);
    static void Unsubscribe(std::string url, std::string channels, std::string api_key, std::string passphrase, std::string secret_key);
};


#endif //OKAPI_OKAPI_WS_H
