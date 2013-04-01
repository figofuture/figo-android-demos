
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_SRC_FILES := \
	main.c

LOCAL_C_INCLUDES += \
		    $(LOCAL_PATH)/../injectso

LOCAL_CFLAGS += -O3

LOCAL_MODULE:= loader

LOCAL_STATIC_LIBRARIES := injectso

include $(BUILD_EXECUTABLE)

