package com.figo.common.utils;

public class LogHelper {
    
    private static String sLogPrefix = "";
    
    private static class LogLevelDefault {
        public static final boolean VERBOSE = true;
        public static final boolean DEBUG = true;
        public static final boolean INFO = true;
        public static final boolean WARN = true;
        public static final boolean ERROR = true;
    }
    
    public static class LogLevel {
        public static boolean VERBOSE = LogLevelDefault.VERBOSE;
        public static boolean DEBUG = LogLevelDefault.DEBUG;
        public static boolean INFO = LogLevelDefault.INFO;
        public static boolean WARN = LogLevelDefault.WARN;
        public static boolean ERROR = LogLevelDefault.ERROR;
    }
    
    public static void resetLogLevel() {
        LogLevel.VERBOSE = LogLevelDefault.VERBOSE;
        LogLevel.DEBUG = LogLevelDefault.DEBUG;
        LogLevel.INFO = LogLevelDefault.INFO;
        LogLevel.WARN = LogLevelDefault.WARN;
        LogLevel.ERROR = LogLevelDefault.ERROR;
    }
    
    public static enum LogLevelEnum {
        Verbose, Debug, Warn, Error, Info
    }

    public static String makeLogTag(Class<?> cls) {
        
        return sLogPrefix + "." + cls.getSimpleName();
    }
    
    public static void init(String logPrefix) {
        sLogPrefix = logPrefix;
    }
}