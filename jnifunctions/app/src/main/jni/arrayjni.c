#include <jni.h>
#include <android/log.h>

#define TAG "arrayjni"

// 调用 JNIEnv->GetArrayLength方法
JNIEXPORT jsize JNICALL Java_com_xy_android_jnifunctions_ArrayJNI_getArrayLength
                    (JNIEnv *env,jobject jobj,jarray jArr){
    jsize size=(*env)->GetArrayLength(env,jArr);
    return size;
}

// 测试JNIEnv array操作相关函数，操作jintArray数据会直接修改java传递过来的数组数据
JNIEXPORT jintArray JNICALL Java_com_xy_android_jnifunctions_ArrayJNI_increaseIntArrayElements
                    (JNIEnv *env,jobject jobj,jintArray intArray){
    jsize size=(*env)->GetArrayLength(env,intArray);
    __android_log_print(ANDROID_LOG_DEBUG,TAG,"数组长度：%d",size);

    jboolean iscopy;
    jint *jintPointer=(*env)->GetIntArrayElements(env,intArray,&iscopy);
    for(int i=0;i<size;i++){
        *(jintPointer+i)=*(jintPointer+i)+10;
    }
    (*env)->ReleaseIntArrayElements(env,intArray,jintPointer,0);
    return intArray;
}

