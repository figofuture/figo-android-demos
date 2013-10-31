package com.figo.testsysinfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.util.SparseArray;

class BatteryStats {

    static boolean isPerUserWifiStatsSupported() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO;
    }

    static final String LOG_TAG = BatteryStats.class.getSimpleName();

    // static final String CLASS_NAME = "android.os.BatteryStats";
    static final String CLASS_NAME = "com.android.internal.os.BatteryStatsImpl";

    static final Class<?> CLASS;
    static {
        Class<?> klass = null;
        try {
            klass = Class.forName(CLASS_NAME);
        } catch (Exception e) {
            
            
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
            
            klass = null;
        } finally {
            CLASS = klass;
        }
    }

    static final String[] DATA_CONNECTION_NAMES;

    static {
        String[] names = { "none", "gprs", "edge", "umts", "cdma", "evdo_0",
                "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b",
                "lte", "ehrpd", "other" };

        // use system first
        try {
            
            if (CLASS != null) {
                Field field = CLASS.getSuperclass()
                        .getDeclaredField("DATA_CONNECTION_NAMES");
                if (field != null) {
                    field.setAccessible(true);
                    names = (String[]) field.get(null);
                }
            }

        } catch (Exception e) {
           
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
        } finally {
            DATA_CONNECTION_NAMES = names;
        }
    }

    final Object mBatteryInfo;
    final Object mThis;

    /**
     * A constant indicating a partial wake lock timer.
     */
    public static final int WAKE_TYPE_PARTIAL = 0;

    BatteryStats() {
        Object batteryInfo = null;
        Object batteryStats = null;
        try {
            // mBatteryInfo =
            // IBatteryStats.Stub.asInterface(ServiceManager.getService("batteryinfo"));
            batteryInfo = Class
                    .forName("com.android.internal.app.IBatteryStats$Stub")
                    .getDeclaredMethod("asInterface", IBinder.class)
                    .invoke(null,
                            Class.forName("android.os.ServiceManager")
                                    .getMethod("getService", String.class)
                                    .invoke(null, "batteryinfo"));

            // byte[] data = mBatteryInfo.getStatistics();
            final byte[] data = (byte[]) Class
                    .forName("com.android.internal.app.IBatteryStats")
                    .getMethod("getStatistics").invoke(batteryInfo);

            
                Log.d(LOG_TAG, "battery info data length: " + data.length);
            
            if (data.length == 0)
                return;

            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(data, 0, data.length);
            parcel.setDataPosition(0);

            // mStats =
            // com.android.internal.os.BatteryStatsImpl.CREATOR.createFromParcel(parcel);
            batteryStats = CLASS.getField("CREATOR").getType()
                    .getMethod("createFromParcel", Parcel.class)
                    .invoke(CLASS.getField("CREATOR").get(null), parcel);

            // Not exist on Android 2.2
            // mStats.distributeWorkLocked(BatteryStats.STATS_SINCE_CHARGED);
            // CLASS.getMethod("distributeWorkLocked",
            // Integer.TYPE).invoke(batteryStats, STATS_SINCE_CHARGED);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        } finally {
            mBatteryInfo = batteryInfo;
            mThis = batteryStats;
        }
    }

    /**
     * State for keeping track of timing information.
     */
    public static class Timer {
        static final Class<?> TIMER_CLASS;
        static {
            Class<?> klass = null;
            try {
                klass = Class.forName(CLASS_NAME + "$Timer");
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
                klass = null;
            } finally {
                TIMER_CLASS = klass;
            }
        }

        final Object mThis;

        public Timer(Object self) {
            mThis = self;
        }

        /**
         * Returns the count associated with this Timer for the selected type of
         * statistics.
         * 
         * @param which
         *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT
         */
        // public abstract int getCountLocked(int which);

        /**
         * Returns the total time in microseconds associated with this Timer for
         * the selected type of statistics.
         * 
         * @param batteryRealtime
         *            system realtime on battery in microseconds
         * @param which
         *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT
         * @return a time in microseconds
         */
        public long getTotalTimeLocked(long batteryRealtime, int which) {
            try {
                return (Long) TIMER_CLASS.getMethod("getTotalTimeLocked",
                        Long.TYPE, Integer.TYPE).invoke(mThis, batteryRealtime,
                        which);
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return 0;
        }

        /**
         * Temporary for debugging.
         */
        // public abstract void logState(Printer pw, String prefix);
    }

    /**
     * Proxy class for internal class android.os.BatteryStats.Uid
     */
    public static class Uid {
        static final String UID_CLASS_NAME = CLASS_NAME + "$Uid";
        static final Class<?> UID_CLASS;
        static {
            Class<?> klass = null;
            try {
                klass = Class.forName(UID_CLASS_NAME);
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
                
                klass = null;
            } finally {
                UID_CLASS = klass;
            }
        }

        final Object mThis;

        public Uid(Object self) {
            mThis = self;
        }

        public int getUid() {
            try {
                return (Integer) UID_CLASS.getMethod("getUid").invoke(mThis);
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return 0;
        }

        /**
         * Returns a mapping containing wakelock statistics.
         * 
         * @return a Map from Strings to Uid.Wakelock objects.
         */
        public Map<String, Wakelock> getWakelockStats() {
            Map<String, Wakelock> result = new HashMap<String, Wakelock>();
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> stats = (Map<String, Object>) UID_CLASS
                        .getMethod("getWakelockStats").invoke(mThis);
                for (Map.Entry<String, Object> entry : stats.entrySet()) {
                    final Object value = entry.getValue();
                    if (value != null) {
                        result.put(entry.getKey(), new Wakelock(value));
                    }
                }
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return result;
        }

        public long getTcpBytesReceived(int which) {
            try {
                return (Long) UID_CLASS.getMethod("getTcpBytesReceived",
                        Integer.TYPE).invoke(mThis, which);
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return 0;
        }

        public long getTcpBytesSent(int which) {
            try {
                return (Long) UID_CLASS.getMethod("getTcpBytesSent",
                        Integer.TYPE).invoke(mThis, which);
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return 0;
        }

        public long getWifiRunningTime(long batteryRealtime, int which) {
            if (isPerUserWifiStatsSupported()) {
                try {
                    return (Long) UID_CLASS.getMethod("getWifiRunningTime",
                            Long.TYPE, Integer.TYPE).invoke(mThis,
                            batteryRealtime, which);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
            }
            return 0;
        }

        /**
         * The statistics associated with a particular wake lock.
         */
        public static class Wakelock {
            static final Class<?> WAKE_LOCK_CLASS;
            static {
                Class<?> klass = null;
                try {
                    klass = Class.forName(UID_CLASS_NAME + "$Wakelock");
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                    klass = null;
                } finally {
                    WAKE_LOCK_CLASS = klass;
                }
            }

            final Object mThis;

            public Wakelock(Object wakelock) {
                mThis = wakelock;
            }

            public Timer getWakeTime(int type) {
                try {
                    final Object timer = WAKE_LOCK_CLASS.getMethod(
                            "getWakeTime", Integer.TYPE).invoke(mThis, type);
                    if (timer != null) {
                        return new Timer(timer);
                    }
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return null;
            }
        }

        public static class Sensor {
            // Magic sensor number for the GPS.
            public static final int GPS = -10000;

            static final Class<?> SENSOR_CLASS;
            static {
                Class<?> klass = null;
                try {
                    klass = Class.forName(UID_CLASS_NAME + "$Sensor");
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                    klass = null;
                } finally {
                    SENSOR_CLASS = klass;
                }
            }

            final Object mThis;

            public Sensor(Object sensor) {
                mThis = sensor;
            }

            public int getHandle() {
                try {
                    return (Integer) SENSOR_CLASS.getMethod("getHandle")
                            .invoke(mThis);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return 0;
            }

            public Timer getSensorTime() {
                try {
                    final Object timer = SENSOR_CLASS
                            .getMethod("getSensorTime").invoke(mThis);
                    if (timer != null) {
                        return new Timer(timer);
                    }
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return null;
            }
        }

        /**
         * Returns a mapping containing sensor statistics.
         * 
         * @return a Map from Integer sensor ids to Uid.Sensor objects.
         */
        public Map<Integer, Sensor> getSensorStats() {
            Map<Integer, Sensor> result = new HashMap<Integer, Sensor>();
            try {
                @SuppressWarnings("unchecked")
                Map<Integer, Object> stats = (Map<Integer, Object>) UID_CLASS
                        .getMethod("getSensorStats").invoke(mThis);
                for (Map.Entry<Integer, Object> entry : stats.entrySet()) {
                    final Object value = entry.getValue();
                    if (value != null) {
                        result.put(entry.getKey(), new Sensor(value));
                    }
                }
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return result;
        }

        /**
         * Returns a mapping containing process statistics.
         * 
         * @return a Map from Strings to Uid.Proc objects.
         */
        public Map<String, Proc> getProcessStats() {
            Map<String, Proc> result = new HashMap<String, Proc>();
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> stats = (Map<String, Object>) UID_CLASS
                        .getMethod("getProcessStats").invoke(mThis);
                for (Map.Entry<String, Object> entry : stats.entrySet()) {
                    final Object value = entry.getValue();
                    if (value != null) {
                        result.put(entry.getKey(), new Proc(value));
                    }
                }
            } catch (Exception e) {
                    Log.e(LOG_TAG,
                            e.getMessage() != null ? e.getMessage() : "", e);
            }
            return result;
        }

        /**
         * The statistics associated with a particular process.
         */
        public static class Proc {

            // public static class ExcessivePower {
            // public static final int TYPE_WAKE = 1;
            // public static final int TYPE_CPU = 2;

            // public int type;
            // public long overTime;
            // public long usedTime;
            // }

            static final Class<?> PROC_CLASS;
            static {
                Class<?> klass = null;
                try {
                    klass = Class.forName(UID_CLASS_NAME + "$Proc");
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                    klass = null;
                } finally {
                    PROC_CLASS = klass;
                }
            }

            final Object mThis;

            public Proc(Object sensor) {
                mThis = sensor;
            }

            /**
             * Returns the total time (in 1/100 sec) spent executing in user
             * code.
             * 
             * @param which
             *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT.
             */
            public long getUserTime(int which) {
                try {
                    return (Long) PROC_CLASS.getMethod("getUserTime",
                            Integer.TYPE).invoke(mThis, which);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return 0;
            }

            /**
             * Returns the total time (in 1/100 sec) spent executing in system
             * code.
             * 
             * @param which
             *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT.
             */
            public long getSystemTime(int which) {
                try {
                    return (Long) PROC_CLASS.getMethod("getSystemTime",
                            Integer.TYPE).invoke(mThis, which);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return 0;
            }

            /**
             * Returns the number of times the process has been started.
             * 
             * @param which
             *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT.
             */
             public int getStarts(int which) {
                 try {
                        return (Integer) PROC_CLASS.getMethod("getStarts",
                                Integer.TYPE).invoke(mThis, which);
                    } catch (Exception e) {
                            Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                    }
                    return 0;
             }

            /**
             * Returns the cpu time spent in microseconds while the process was
             * in the foreground.
             * 
             * @param which
             *            one of STATS_TOTAL, STATS_LAST, STATS_CURRENT or
             *            STATS_UNPLUGGED
             * @return foreground cpu time in microseconds
             */
            public long getForegroundTime(int which) {
                try {
                    return (Long) PROC_CLASS.getMethod("getForegroundTime",
                            Integer.TYPE).invoke(mThis, which);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return 0;
            }

            /**
             * Returns the approximate cpu time spent in microseconds, at a
             * certain CPU speed.
             * 
             * @param speedStep
             *            the index of the CPU speed. This is not the actual
             *            speed of the CPU.
             * @param which
             *            one of STATS_TOTAL, STATS_LAST, STATS_CURRENT or
             *            STATS_UNPLUGGED
             * @see BatteryStats#getCpuSpeedSteps()
             */
            public long getTimeAtCpuSpeedStep(int speedStep, int which) {
                try {
                    return (Long) PROC_CLASS.getMethod("getTimeAtCpuSpeedStep",
                            Integer.TYPE, Integer.TYPE).invoke(mThis,
                            speedStep, which);
                } catch (Exception e) {
                        Log.e(LOG_TAG,
                                e.getMessage() != null ? e.getMessage() : "", e);
                }
                return 0;
            }

            // public int countExcessivePowers() {
            // return 0;
            // }

            // public ExcessivePower getExcessivePower(int i) {
            // return 0;
            // }
        }
    }

    /**
     * Include all of the data in the stats, including previously saved data.
     */
    public static final int STATS_SINCE_CHARGED = 0;

    /**
     * Include only the last run in the stats.
     */
    public static final int STATS_LAST = 1;

    /**
     * Include only the current run in the stats.
     */
    public static final int STATS_CURRENT = 2;

    /**
     * Include only the run since the last time the device was unplugged in the
     * stats.
     */
    public static final int STATS_SINCE_UNPLUGGED = 3;

    /**
     * Returns the total, last, or current battery realtime in microseconds.
     * 
     * @param curTime
     *            the current elapsed realtime in microseconds.
     * @param which
     *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT.
     */
    public long computeBatteryRealtime(long curTime, int which) {
        try {
            return (Long) CLASS.getMethod("computeBatteryRealtime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, curTime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns a SparseArray containing the statistics for each uid.
     */
    public SparseArray<Uid> getUidStats() {
        SparseArray<Uid> result = new SparseArray<Uid>();
        try {
            @SuppressWarnings("unchecked")
            SparseArray<Object> uids = (SparseArray<Object>) CLASS.getMethod(
                    "getUidStats").invoke(mThis);
            for (int i = 0; i < uids.size(); ++i) {
                Object u = uids.valueAt(i);
                if (u != null) {
                    result.put(i, new Uid(u));
                }
            }
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return result;
    }

    /**
     * Returns the total, last, or current battery uptime in microseconds.
     * 
     * @param curTime
     *            the elapsed realtime in microseconds.
     * @param which
     *            one of STATS_TOTAL, STATS_LAST, or STATS_CURRENT.
     */
    public long computeBatteryUptime(long curTime, int which) {
        try {
            return (Long) CLASS.getMethod("computeBatteryUptime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, curTime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    public long getScreenOnTime(long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getScreenOnTime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;

    /**
     * Returns the time in microseconds that the screen has been on with the
     * given brightness
     * 
     */
    public long getScreenBrightnessTime(int brightnessBin,
            long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getScreenBrightnessTime",
                    Integer.TYPE, Long.TYPE, Integer.TYPE).invoke(mThis,
                    brightnessBin, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;

    /**
     * Returns the time in microseconds that the phone has been running with the
     * given signal strength.
     * 
     */
    public long getPhoneSignalStrengthTime(int strengthBin,
            long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getPhoneSignalStrengthTime",
                    Integer.TYPE, Long.TYPE, Integer.TYPE).invoke(mThis,
                    strengthBin, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns the time in microseconds that the phone has been trying to
     * acquire a signal.
     * 
     */
    public long getPhoneSignalScanningTime(long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getPhoneSignalScanningTime",
                    Long.TYPE, Integer.TYPE).invoke(mThis, batteryRealtime,
                    which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns the time in microseconds that the phone has been on while the
     * device was running on battery.
     * 
     */
    public long getPhoneOnTime(long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getPhoneOnTime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns the time in microseconds that wifi has been on while the device
     * was running on battery.
     * 
     */
    public long getWifiOnTime(long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getWifiOnTime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns the time in microseconds that wifi has been on and the driver has
     * been in the running state while the device was running on battery.
     * 
     */
    public long getGlobalWifiRunningTime(long batteryRealtime, int which) {
        try {
            final String methodName = isPerUserWifiStatsSupported() ? "getGlobalWifiRunningTime"
                    : "getWifiRunningTime";

            return (Long) CLASS.getMethod(methodName, Long.TYPE, Integer.TYPE)
                    .invoke(mThis, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /**
     * Returns the time in microseconds that bluetooth has been on while the
     * device was running on battery.
     * 
     */
    public long getBluetoothOnTime(long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getBluetoothOnTime", Long.TYPE,
                    Integer.TYPE).invoke(mThis, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    public int getBluetoothPingCount() {
        try {
            return (Integer) CLASS.getMethod("getBluetoothPingCount").invoke(
                    mThis);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /** Only STATS_UNPLUGGED works properly */
    public long getMobileTcpBytesSent(int which) {
        try {
            return (Long) CLASS
                    .getMethod("getMobileTcpBytesSent", Integer.TYPE).invoke(
                            mThis, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /** Only STATS_UNPLUGGED works properly */
    public long getMobileTcpBytesReceived(int which) {
        try {
            return (Long) CLASS.getMethod("getMobileTcpBytesReceived",
                    Integer.TYPE).invoke(mThis, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /** Only STATS_UNPLUGGED works properly */
    public long getTotalTcpBytesSent(int which) {
        try {
            return (Long) CLASS.getMethod("getTotalTcpBytesSent", Integer.TYPE)
                    .invoke(mThis, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /** Only STATS_UNPLUGGED works properly */
    public long getTotalTcpBytesReceived(int which) {
        try {
            return (Long) CLASS.getMethod("getTotalTcpBytesReceived",
                    Integer.TYPE).invoke(mThis, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }

        return 0;
    }

    /*
     * Returns the time in microseconds that the phone has been running with the
     * given data connection.
     */
    public long getPhoneDataConnectionTime(int dataTypeIndex,
            long batteryRealtime, int which) {
        try {
            return (Long) CLASS.getMethod("getPhoneDataConnectionTime",
                    Integer.TYPE, Long.TYPE, Integer.TYPE).invoke(mThis,
                    dataTypeIndex, batteryRealtime, which);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);

        }

        return 0;
    }

    /**
     * Returns the duration that the cell radio was up for data transfers.
     */
    public long getRadioDataUptime() {
        try {
            return (Long) CLASS.getMethod("getRadioDataUptime").invoke(mThis);
        } catch (Exception e) {
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
        }
        return 0;
    }

    /*
     * Returns the total time in microseconds that the phone has been running
     * with all the mobile data connection.
     */
    public long getTotalPhoneConnectionTime(long batteryRealtime, int which) {
        long total = 0;
        for (int i = 0; i < DATA_CONNECTION_NAMES.length; i++) {

            total += getPhoneDataConnectionTime(i, batteryRealtime, which);
                Log.d(LOG_TAG, "3g[" + DATA_CONNECTION_NAMES[i] + "]: " + total);
        }

        return total;
    }
}
