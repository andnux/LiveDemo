cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_ANDROID_STL_TYPE gnustl_static)

include_directories(libs/include src/main/cpp
        src/main/cpp/libfaac
        src/main/cpp/librtmp)

file(GLOB CPP src/main/cpp/*.c
        src/main/cpp/*.cpp)

file(GLOB AAC src/main/cpp/libfaac/*.c
        src/main/cpp/libfaac/*.cpp)
file(GLOB RTMP src/main/cpp/librtmp/*.c
        src/main/cpp/librtmp/*.cpp)

set(ABI ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI})

add_library(live SHARED ${CPP} ${AAC} ${RTMP})

target_link_libraries(live log z
        ${ABI}/libx264.a
        ${ABI}/libssl.a
        ${ABI}/libcrypto.a)