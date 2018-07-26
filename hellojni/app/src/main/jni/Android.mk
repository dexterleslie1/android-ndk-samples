LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog
LOCAL_SRC_FILES:=hellojni.c
LOCAL_MODULE := libhellojni
include $(BUILD_SHARED_LIBRARY)