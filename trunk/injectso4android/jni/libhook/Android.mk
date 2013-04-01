LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CFLAGS  +=  -Wall \
		  -O3

LOCAL_LDLIBS +=  -llog 

LOCAL_SRC_FILES += \
		libhook.c 

LOCAL_MODULE := hook

include $(BUILD_SHARED_LIBRARY)
