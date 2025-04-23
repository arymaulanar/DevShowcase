cmake_minimum_required(VERSION 3.22.1)
project("DevShowcase")

# These will be overridden by Gradle arguments
set(WEATHER_API_KEY "" CACHE STRING "Weather API key")
set(NEWS_API_KEY "" CACHE STRING "News API key")

add_library(
        secure-keys
        SHARED
        main.cpp
)

find_library(log-lib log)
target_link_libraries(secure-keys ${log-lib})

# Proper way to pass definitions to C++
target_compile_definitions(secure-keys PRIVATE
        WEATHER_API_KEY="${WEATHER_API_KEY}"
        NEWS_API_KEY="${NEWS_API_KEY}"
)
