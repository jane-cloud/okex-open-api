# Install script for directory: /Users/oak/go/src/github.com/cpprestsdk/Release/tests/functional

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

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/http/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/json/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/pplx/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/streams/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/uri/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/utils/cmake_install.cmake")
  include("/Users/oak/go/src/github.com/cpprestsdk/Release/cmake-build-debug/tests/functional/websockets/cmake_install.cmake")

endif()

