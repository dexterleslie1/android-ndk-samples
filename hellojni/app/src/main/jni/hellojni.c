#include <jni.h>
#include <android/log.h>

#define TAG "hellojni"

JNIEXPORT jstring JNICALL Java_com_xy_android_hellojni_HelloJni_stringFromJNI
                    (JNIEnv *env,jobject jobj){
    __android_log_print(ANDROID_LOG_DEBUG,TAG ,"调试打印信息");
    return (*env)->NewStringUTF(env, "Hello World! I am Native interface");
}