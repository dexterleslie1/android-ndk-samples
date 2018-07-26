#include <jni.h>
#include <android/log.h>

#include <speex/speex_echo.h>
#include <speex/speex_preprocess.h>

#define TAG "speexjni"

#define FRAME_SIZE 160
#define FILTER_LENGTH 800

SpeexEchoState *echo_state=NULL;
SpeexPreprocessState *preprocessState=NULL;

// 初始化aec
JNIEXPORT void JNICALL
Java_com_xy_android_aec_SpeexJNI_init(JNIEnv *env,jobject jobj){
    int sampleRate=8000;
    echo_state=speex_echo_state_init(FRAME_SIZE, FILTER_LENGTH);
    __android_log_print(ANDROID_LOG_DEBUG,TAG ,"echo_state pointer 0x%x",echo_state);
    preprocessState=speex_preprocess_state_init(FRAME_SIZE, sampleRate);
    __android_log_print(ANDROID_LOG_DEBUG,TAG ,"preprocess state pointer 0x%x",preprocessState);
    speex_echo_ctl(echo_state, SPEEX_ECHO_SET_SAMPLING_RATE, &sampleRate);
    speex_preprocess_ctl(preprocessState, SPEEX_PREPROCESS_SET_ECHO_STATE, echo_state);
    speex_preprocess_ctl(preprocessState, SPEEX_PREPROCESS_SET_DENOISE, echo_state);
    speex_preprocess_ctl(preprocessState, SPEEX_PREPROCESS_SET_DEREVERB, echo_state);
}

JNIEXPORT void JNICALL
Java_com_xy_android_aec_SpeexJNI_cancellation(
                                        JNIEnv *env,
                                        jobject jobj,
                                        jshortArray input_frame,
                                        jshortArray echo_frame,
                                        jshortArray output_frame){
    jboolean iscopy;
    jshort *rec=(*env)->GetShortArrayElements(env,input_frame,&iscopy);
    jshort *play=(*env)->GetShortArrayElements(env,echo_frame,&iscopy);
    jshort *out=(*env)->GetShortArrayElements(env,output_frame,&iscopy);
    speex_echo_cancellation(echo_state, rec, play, out);
    speex_preprocess_run(preprocessState, out);
    (*env)->ReleaseShortArrayElements(env,input_frame,rec,0);
    (*env)->ReleaseShortArrayElements(env,echo_frame,play,0);
    (*env)->ReleaseShortArrayElements(env,output_frame,out,0);
}

// 关闭aec
JNIEXPORT void JNICALL
Java_com_xy_android_aec_SpeexJNI_destroy(JNIEnv *env,jobject jobj){
    if(echo_state){
        speex_echo_state_destroy(echo_state);
    }
    if(preprocessState){
        speex_preprocess_state_destroy(preprocessState);
    }
}