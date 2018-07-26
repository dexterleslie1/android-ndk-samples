LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog
LOCAL_SRC_FILES:=arrayjni.c
LOCAL_MODULE := libjnifunctions
include $(BUILD_SHARED_LIBRARY)