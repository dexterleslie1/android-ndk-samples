package com.xy.android.hellojni;

/**
 *
 */
public class HelloJni {
    static{
        System.loadLibrary("hellojni");
    }

    public native String stringFromJNI();
}
