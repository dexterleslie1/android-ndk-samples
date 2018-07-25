#include <jni.h>
#include <android/log.h>

#define TAG "stringjni"

// 调试primitive types
JNIEXPORT void JNICALL
Java_com_xy_android_stringjni_StringJNI_printPrimitiveType(JNIEnv *env,jobject jobj){
    jboolean jb1=1;
    jchar *jcharStr="message from hello world";
    __android_log_print(ANDROID_LOG_DEBUG,TAG,"jboolean:%s",jb1?"true":"false");
    __android_log_print(ANDROID_LOG_DEBUG,TAG,"jchar*:%s",jcharStr);
}

// 调用NewStringUTF方法
JNIEXPORT jstring JNICALL
Java_com_xy_android_stringjni_StringJNI_newStringUTF(JNIEnv *env,jobject jobj){
   jchar *jcharStr="String from JNIEnv->NewStringUTF";
   jstring jstr=(*env)->NewStringUTF(env,jcharStr);
   return jstr;
}

// 调用GetStringLength方法
JNIEXPORT jsize JNICALL
Java_com_xy_android_stringjni_StringJNI_getStringUTFLength(JNIEnv *env,jobject jobj,jstring jstr){
    jsize stringLength=(*env)->GetStringUTFLength(env,jstr);
    return stringLength;
}


