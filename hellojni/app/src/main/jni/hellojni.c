#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_xy_android_hellojni_HelloJni_stringFromJNI
                    (JNIEnv *env){
    return (*env)->NewStringUTF(env, "Hello World! I am Native interface");
}