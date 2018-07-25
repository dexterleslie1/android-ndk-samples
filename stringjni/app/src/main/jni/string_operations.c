#include <jni.h>
#include <android/log.h>

#define TAG "stringjni"

// 调用NewString方法
JNIEXPORT jstring JNICALL
Java_com_xy_android_stringjni_StringJNI_newString(JNIEnv *env,jobject jobj){
    return "";
}

// 调用GetStringLength方法
JNIEXPORT jsize JNICALL
Java_com_xy_android_stringjni_StringJNI_getStringLength(JNIEnv *env,jobject jobj,jstring jstr){
    jsize stringLength=(*env)->GetStringLength(env,jstr);
    return stringLength;
}


