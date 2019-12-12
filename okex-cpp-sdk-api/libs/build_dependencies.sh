#!/bin/bash

RESTSDK_VERSION="v2.9.1"
DEFAULT_LIB_DIRECTORY_PATH="."

libDir=${1:-$DEFAULT_LIB_DIRECTORY_PATH}


install_cpprestsdk(){
	restsdkDir="$libDir/cpprestsdk"
	restsdkBuildDir="$restsdkDir/build.debug"

	mkdir "$restsdkBuildDir"

	(cd "$restsdkBuildDir" && cmake ../Release -DCMAKE_BUILD_TYPE=Debug -DBUILD_SHARED_LIBS=OFF)
	(cd "$restsdkBuildDir" && make -j 7)
}

mkdir -p "$libDir"

install_cpprestsdk
