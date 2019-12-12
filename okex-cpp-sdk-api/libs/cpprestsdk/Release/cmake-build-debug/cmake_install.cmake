# Install script for directory: /Users/oak/go/src/github.com/cpprestsdk/Release

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/usr/local")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "Debug")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Is this installation the result of a crosscompile?
if(NOT DEFINED CMAKE_CROSSCOMPILING)
  set(CMAKE_CROSSCOMPILING "FALSE")
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/cpprest" TYPE FILE FILES
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/astreambuf.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/asyncrt_utils.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/base_uri.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/containerstream.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/filestream.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/http_client.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/http_headers.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/http_listener.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/http_msg.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/interopstream.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/json.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/oauth1.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/oauth2.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/producerconsumerstream.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/rawptrstream.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/streams.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/uri.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/uri_builder.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/version.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/ws_client.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/ws_msg.h"
    )
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/pplx" TYPE FILE FILES
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplx.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxcancellation_token.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxconv.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxinterface.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxlinux.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxtasks.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/pplxwin.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/pplx/threadpool.h"
    )
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/cpprest/details" TYPE FILE FILES
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/SafeInt3.hpp"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/basic_types.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/cpprest_compat.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/fileio.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_helpers.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_server.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_server_api.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_server_asio.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_server_httpsys.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/nosal.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/resource.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/uri_parser.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/web_utilities.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/x509_cert_utilities.h"
    "/Users/oak/go/src/github.com/cpprestsdk/Release/include/cpprest/details/http_constants.dat"
    )
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/src/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/samples/cmake_install.cmake")

endif()

if(CMAKE_INSTALL_COMPONENT)
  set(CMAKE_INSTALL_MANIFEST "install_manifest_${CMAKE_INSTALL_COMPONENT}.txt")
else()
  set(CMAKE_INSTALL_MANIFEST "install_manifest.txt")
endif()

string(REPLACE ";" "\n" CMAKE_INSTALL_MANIFEST_CONTENT
       "${CMAKE_INSTALL_MANIFEST_FILES}")
file(WRITE "/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/${CMAKE_INSTALL_MANIFEST}"
     "${CMAKE_INSTALL_MANIFEST_CONTENT}")
