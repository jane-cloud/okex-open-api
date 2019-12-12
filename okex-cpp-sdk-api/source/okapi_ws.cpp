//
// Created by zxp on 28/12/18.
//

#include "okapi_ws.h"
#include "utils.h"
#include <cpprest/ws_client.h>
#include <cpprest/json.h>
#include <cpprest/filestream.h>

#define BUFLEN 65536

using namespace web;
using namespace web::websockets::client;
using namespace concurrency::streams;       // Asynchronous streams

// subscribe channel without login
/*
 swap/ticker // 行情数据频道
 swap/candle60s // 1分钟k线数据频道
 swap/candle180s // 3分钟k线数据频道
 swap/candle300s // 5分钟k线数据频道
 swap/candle900s // 15分钟k线数据频道
 swap/candle1800s // 30分钟k线数据频道
 swap/candle3600s // 1小时k线数据频道
 swap/candle7200s // 2小时k线数据频道
 swap/candle14400s // 4小时k线数据频道
 swap/candle21600 // 6小时k线数据频道
 swap/candle43200s // 12小时线数据频道
 swap/candle180s // 3分钟k线数据频道
 swap/candle300s // 5分钟k线数据频道
 swap/candle900s // 15分钟k线数据频道
 swap/candle1800s // 30分钟k线数据频道
 swap/candle3600s // 1小时k线数据频道
 swap/candle7200s // k线数据频道
 swap/candle86400s // 1day k线数据频道
 swap/candle604800s // 1week k线数据频道
 swap/trade // 交易信息频道
 swap/funding_rate//资金费率频道
 swap/price_range//限价范围频道
 swap/depth //深度数据频道，首次200档，后续增量
 swap/depth5 //深度数据频道，每次返回前5档
 swap/mark_price// 标记价格频道
*/
void okapi_ws::SubscribeWithoutLogin(std::string url, std::string channels){
    RequestWithoutLogin(url, channels, "subscribe");
}

void okapi_ws::UnsubscribeWithoutLogin(std::string url, std::string channels){
    RequestWithoutLogin(url, channels, "unsubscribe");
}

void okapi_ws::RequestWithoutLogin(std::string url, std::string channels, std:: string op) {
    websocket_client client;

    auto fileStream = std::make_shared<concurrency::streams::ostream>();

    // Open stream to output file.
    pplx::task<void> requestTask = fstream::open_ostream(U("results.html"))
            .then([&](ostream outFile)
                  {
                      *fileStream = outFile;

                      // Create http_client to send the request.
                      uri wsuri(url);
                      client.connect(wsuri).wait();
                      printf("connect success: %s\n", url.c_str());

                      json::value obj;
                      obj["op"] = json::value::string(op);
                      obj["args"] = json::value::string(channels);
                      websocket_outgoing_message msg;
                      msg.set_utf8_message(obj.serialize());
                      client.send(msg).wait();
                      printf("send success: %s\n", obj.serialize().c_str());

                      return client.receive().get();
                  })

                    // Handle response headers arriving.
            .then([&](websocket_incoming_message msg)
                  {
                      while (op.compare("subscribe")==0) {

                          // Write response body into the file.
                          unsigned char buf[BUFLEN]= {0};
                          unsigned char data[BUFLEN] = {0};

                          auto buflen = msg.body().streambuf().scopy(buf, BUFLEN);
                          uLong datalen = sizeof(data);

                          if (msg.message_type() == websocket_message_type::binary_message) {
                              gzDecompress((Byte *) buf, buflen, (Byte *) data, &datalen);
                          } else {
                              strcpy((char *) data, (char *) buf);
                          }

                          printf("receive success: \n");
                          printf("\t data: %s\n", data);
                          printf("\t datalen: %ld\n\n\n", datalen);

                          msg = client.receive().get();
                      }
                      return msg.body().read_to_end(fileStream->streambuf());

                  })


                    // Close the file stream.
            .then([&](size_t size)
                  {
                      client.close();
                      printf("client closed\n\n\n");
                      return fileStream->close();
                  });

    // Wait for all the outstanding I/O to complete and handle any exceptions
    try
    {
        requestTask.wait();
    }
    catch (const std::exception &e)
    {
        printf("Error exception:%s\n", e.what());
    }

}


/*
 subscribe channel need login

 swap/account //用户账户信息频道
 swap/position //用户持仓信息频道
 swap/order //用户交易数据频道
*/
void okapi_ws::Subscribe(std::string url, std::string channels, std::string api_key, std::string passphrase, std::string secret_key) {
    Request(url, channels, "subscribe", api_key, passphrase, secret_key);
}

void okapi_ws::Unsubscribe(std::string url, std::string channels, std::string api_key, std::string passphrase, std::string secret_key) {
    Request(url, channels, "unsubscribe", api_key, passphrase, secret_key);
}

void okapi_ws::Request(std::string url, std::string channels, std::string op, std::string api_key, std::string passphrase, std::string secret_key) {
    websocket_client client;

    //{"op":"login","args":["<api_key>","<passphrase>","<timestamp>","<sign>"]}
    auto getTime = [](char *timestamp){
        time_t t;
        time(&t);
        sprintf(timestamp, "%ld", t);
    };

    char timestamp[32];
    getTime(timestamp);
    std::string sign = GetSign(secret_key, timestamp, "GET", "/users/self/verify", "");

    auto getLoginObj = [=](json::value &obj){
        obj["op"] = json::value::string("login");
        std::vector<json::value> array;
        array.push_back(json::value::string(api_key));
        array.push_back(json::value::string(passphrase));
        array.push_back(json::value::string(timestamp));
        array.push_back(json::value::string(sign));
        obj["args"] = json::value::array(array);
    };

    json::value obj;
    getLoginObj(obj);

    auto fileStream = std::make_shared<concurrency::streams::ostream>();

    // Open stream to output file.
    pplx::task<void> requestTask = fstream::open_ostream(U("results2.html"))
            .then([&](ostream outFile)
                  {
                      *fileStream = outFile;
                      // Create http_client to send the request.
                      uri wsuri(url);
                      client.connect(wsuri).wait();
                      printf("connect success: %s\n", url.c_str());
                      websocket_outgoing_message msg;
                      msg.set_utf8_message(obj.serialize());
                      client.send(msg).wait();
                      printf("send success: %s\n", obj.serialize().c_str());
                      return client.receive().get();
                  })

                    // Handle response headers arriving.
            .then([&](websocket_incoming_message msg)
                  {
                      // Write response body into the file.
                      unsigned char buf[BUFLEN] = {0};
                      unsigned char data[BUFLEN] = {0};

                      auto buflen = msg.body().streambuf().scopy(buf, BUFLEN);
                      uLong datalen = sizeof(data);

                      if (msg.message_type() == websocket_message_type::binary_message) {
                          gzDecompress((Byte *) buf, buflen, (Byte *) data, &datalen);
                      } else {
                          strcpy((char *)data, (char *)buf);
                      }

                      printf("receive success: \n");
                      printf("\t data: %s\n", data);
                      printf("\t datalen: %ld\n", datalen);

                      msg.body().read_to_end(fileStream->streambuf());
                      std::string datastr = (char *)data;
                      return datastr;
                  })
                    // Close the file stream.
            .then([&](std::string datastr)
                  {
                      json::value value;
                      value = json::value::parse(datastr);
                      json::value b = value.at("success");
                      if (b == json::value::boolean(true)) {
                          printf("login success \n");
                      } else {
                          printf("login failed \n");
                      }

                      json::value obj;
                      websocket_outgoing_message sendmsg;
                      obj["op"] = json::value::string(op);
                      obj["args"] = json::value::string(channels);
                      sendmsg.set_utf8_message(obj.serialize());
                      client.send(sendmsg).wait();
                      printf("send success: %s\n", obj.serialize().c_str());
                      return client.receive().get();
                  })
            .then([&](websocket_incoming_message msg)
                  {
                      while (op.compare("subscribe")==0) {

                          // Write response body into the file.
                          unsigned char buf[BUFLEN] = {0};
                          unsigned char data[BUFLEN] = {0};

                          auto buflen = msg.body().streambuf().scopy(buf, BUFLEN);
                          uLong datalen = sizeof(data);

                          if (msg.message_type() == websocket_message_type::binary_message) {
                              gzDecompress((Byte *) buf, buflen, (Byte *) data, &datalen);
                          } else {
                              strcpy((char *) data, (char *) buf);
                          }
                          printf("receive success: \n");
                          printf("\t data: %s\n", data);
                          printf("\t datalen: %ld\n\n\n", datalen);

                          msg = client.receive().get();
                      }
                      msg.body().read_to_end(fileStream->streambuf());
                  })
            .then([&]()
                  {
                      client.close();
                      printf("client closed\n\n\n");
                      return fileStream->close();
                  });

    // Wait for all the outstanding I/O to complete and handle any exceptions
    try
    {
        requestTask.wait();
    }
    catch (const std::exception &e)
    {
        printf("Error exception:%s\n", e.what());
    }
}




