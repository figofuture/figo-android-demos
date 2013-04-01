
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_SRC_FILES := \
	injectso.c \
	ptrace_tools.c \
	shellcode.s

LOCAL_CFLAGS += -O3

LOCAL_MODULE:= injectso

include $(BUILD_STATIC_LIBRARY)

