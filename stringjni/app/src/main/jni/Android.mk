LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:=string_operations.c
LOCAL_MODULE := libstringjni.so
include $(BUILD_SHARED_LIBRARY)