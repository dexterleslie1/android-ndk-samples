package com.xy.android.stringjni;

/**
 * 调用string jni方法
 */
public class StringJNI {
    static{
        System.loadLibrary("stringjni");
    }

    /**
     * 调用JNIEnv->NewString方法
     * @return
     */
    public static native String newString();

    /**
     * 调用JNIEnv->GetStringLength方法
     * @param str
     * @return
     */
    public static native int getStringLength(String str);
}
