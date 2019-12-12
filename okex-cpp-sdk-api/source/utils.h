//
// Created by zxp on 17/07/18.
//

#ifndef CPPSDK_UTILS_H
#define CPPSDK_UTILS_H

#include <ctime>
#include <string>
#include <map>
#include <zlib.h>

char * GetTimestamp(char *timestamp, int len);
std::string BuildParams(std::string requestPath, std::map<std::string,std::string> m);
std::string JsonFormat(std::string jsonStr);
int gzDecompress(Byte *zdata, uLong nzdata, Byte *data, uLong *ndata);
std::string GetSign(std::string key, std::string timestamp, std::string method, std::string requestPath, std::string body);
unsigned int str_hex(unsigned char *str,unsigned char *hex);
void hex_str(unsigned char *inchar, unsigned int len, unsigned char *outtxt);
#endif //CPPSDK_UTILS_H
