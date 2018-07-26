package com.xy.android.aec;

/**
 *
 */
public class SpeexJNI {
    static{
        System.loadLibrary("speexjni");
    }

    public static native void init();
    public static native void cancellation(short []inputFrame,short []echoFrame,short[] outFrame);
    public static native void destroy();
}
