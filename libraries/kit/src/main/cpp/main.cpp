#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_paopeye_kit_constant_SecureKeys_getNewsApiKey(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(NEWS_API_KEY);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_paopeye_kit_constant_SecureKeys_getWeatherApiKey(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(WEATHER_API_KEY);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_paopeye_kit_constant_SecureKeys_getNewsKey(JNIEnv *env, jobject thiz) {
    std::string newsKey = "X-API-KEY";
    return env->NewStringUTF(newsKey.c_str());
}
