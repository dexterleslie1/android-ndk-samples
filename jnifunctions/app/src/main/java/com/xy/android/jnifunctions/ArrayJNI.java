package com.xy.android.jnifunctions;

import java.util.List;

/**
 * 调用array相关jni函数
 */
public class ArrayJNI {

    static{
        System.loadLibrary("jnifunctions");
    }

    /**
     * 调用JNIEnv->GetArrayLength函数，jni jarray类型对应java所有数组类型例如Object[]、int[]、double[]等
     * @param objArray
     * @return
     */
    public static native int getArrayLength(Object[] objArray);
    public static native int getArrayLength(int [] intArray);
    public static native int getArrayLength(double []doubleArray);

    /**
     *
     * @param intArray
     * @return
     */
    public static native int[] increaseIntArrayElements(int []intArray);
}
