#----------------------------------------------------------------
# Generated CMake target import file for configuration "Debug".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "cpprestsdk::cpprest" for configuration "Debug"
set_property(TARGET cpprestsdk::cpprest APPEND PROPERTY IMPORTED_CONFIGURATIONS DEBUG)
set_target_properties(cpprestsdk::cpprest PROPERTIES
  IMPORTED_LOCATION_DEBUG "${_IMPORT_PREFIX}/lib/libcpprest.2.10.dylib"
  IMPORTED_SONAME_DEBUG "@rpath/libcpprest.2.10.dylib"
  )

list(APPEND _IMPORT_CHECK_TARGETS cpprestsdk::cpprest )
list(APPEND _IMPORT_CHECK_FILES_FOR_cpprestsdk::cpprest "${_IMPORT_PREFIX}/lib/libcpprest.2.10.dylib" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
