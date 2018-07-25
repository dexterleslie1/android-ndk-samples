package com.xy.android.stringjni;

/**
 * 调用string jni方法
 */
public class StringJNI {
    static{
        System.loadLibrary("stringjni");
    }

    public static native void printPrimitiveType();

    /**
     * 调用JNIEnv->NewStringUTF方法
     * @return
     */
    public static native String newStringUTF();

    /**
     * 调用JNIEnv->GetStringUTFLength方法
     * @param str
     * @return
     */
    public static native int getStringUTFLength(String str);
}
