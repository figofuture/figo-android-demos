package com.figo.testsysinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.figo.testsysinfo.BatteryStats.Uid;

import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;

public class MainActivity extends Activity {

	private final static String LOG_TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 buildDeviceMetaInfo(this);
		 buildDeviceInfo(this);
		 buildSettingsInfo(this);
		 buildAppsInfo(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	void buildDeviceMetaInfo(Context ctx) {
		DeviceMetaInfo dmi = new DeviceMetaInfo();
		dmi.setManufacturer(Build.MANUFACTURER);
		dmi.setModel(Build.MODEL);
		dmi.setSdkVersionCode(Build.VERSION.SDK_INT);
		dmi.setCountryCode(ctx.getResources().getConfiguration().locale
				.getCountry());
		dmi.setLanguageCode(ctx.getResources().getConfiguration().locale
				.getLanguage());
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		dmi.setNetworkOperator(tm.getNetworkOperator());
		dmi.setSimOperator(tm.getSimOperator());
		ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		dmi.setMemoryClass(am.getMemoryClass());
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		dmi.setMemoryThreshold(outInfo.threshold);
		if (Build.VERSION.SDK_INT > 15)
			dmi.setTotalMemory(outInfo.totalMem);
		else {
			dmi.setTotalMemory(getMemSize(ctx));
		}
		dmi.setCpuModelName(getCpuModelName());

		PowerProfile powerProfile = new PowerProfile(ctx);
		dmi.setBatteryCapacity(powerProfile
				.getAveragePower(PowerProfile.POWER_BATTERY_CAPACITY));
		dmi.setScreenOnPower(powerProfile
				.getAveragePower(PowerProfile.POWER_SCREEN_ON));
		dmi.setScreenFullPower(powerProfile
				.getAveragePower(PowerProfile.POWER_SCREEN_FULL));
		dmi.setBluetoothOnPower(powerProfile
				.getAveragePower(PowerProfile.POWER_BLUETOOTH_ON));
		dmi.setBluetoothATPower(powerProfile
				.getAveragePower(PowerProfile.POWER_BLUETOOTH_AT_CMD));
		dmi.setBluetoothActivePower(powerProfile
				.getAveragePower(PowerProfile.POWER_BLUETOOTH_ACTIVE));
		dmi.setWifiOnPower(powerProfile
				.getAveragePower(PowerProfile.POWER_WIFI_ON));
		dmi.setWifiScanPower(powerProfile
				.getAveragePower(PowerProfile.POWER_WIFI_SCAN));
		dmi.setWifiActivePower(powerProfile
				.getAveragePower(PowerProfile.POWER_WIFI_ACTIVE));
		dmi.setDspAudioPower(powerProfile
				.getAveragePower(PowerProfile.POWER_AUDIO));
		dmi.setDspVideoPower(powerProfile
				.getAveragePower(PowerProfile.POWER_VIDEO));
		dmi.setRadioOnLevel0(powerProfile.getAveragePower(
				PowerProfile.POWER_RADIO_ON, 0));
		dmi.setRadioOnLevel1(powerProfile.getAveragePower(
				PowerProfile.POWER_RADIO_ON, 1));
		dmi.setRadioOnLevel2(powerProfile.getAveragePower(
				PowerProfile.POWER_RADIO_ON, 2));
		dmi.setRadioOnLevel3(powerProfile.getAveragePower(
				PowerProfile.POWER_RADIO_ON, 3));
		dmi.setRadioOnLevel4(powerProfile.getAveragePower(
				PowerProfile.POWER_RADIO_ON, 4));
		dmi.setRadioScanningPower(powerProfile
				.getAveragePower(PowerProfile.POWER_RADIO_SCANNING));
		dmi.setRadioActivePower(powerProfile
				.getAveragePower(PowerProfile.POWER_RADIO_ACTIVE));
		dmi.setGpsOnPower(powerProfile
				.getAveragePower(PowerProfile.POWER_GPS_ON));
		dmi.setCpuIdlePower(powerProfile
				.getAveragePower(PowerProfile.POWER_CPU_IDLE));
		dmi.setCpuAwakePower(powerProfile
				.getAveragePower(PowerProfile.POWER_CPU_AWAKE));
		ArrayList<Double> cpuSpeeds = new ArrayList<Double>();
		final int speedSteps = powerProfile.getNumSpeedSteps();
		for (int i = 0; i < speedSteps; i++) {
			cpuSpeeds.add(powerProfile.getAveragePower(
					PowerProfile.POWER_CPU_SPEEDS, i));
		}
		dmi.setCpuSpeeds(cpuSpeeds);
		ArrayList<Double> cpuActivePowers = new ArrayList<Double>();
		for (int i = 0; i < speedSteps; i++) {
			cpuActivePowers.add(powerProfile.getAveragePower(
					PowerProfile.POWER_CPU_ACTIVE, i));
		}
		dmi.setCpuActivePowers(cpuActivePowers);

		SensorManager sm = (SensorManager) ctx
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);
		for (Sensor sensor : sensors) {
			switch (sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				dmi.setSensorAccelerometerPower(sensor.getPower());
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				dmi.setSensorMagneticFieldPower(sensor.getPower());
				break;
			case Sensor.TYPE_ORIENTATION:
				dmi.setSensorOrientationPower(sensor.getPower());
				break;
			case Sensor.TYPE_GYROSCOPE:
				dmi.setSensorGyroscopePower(sensor.getPower());
				break;
			case Sensor.TYPE_LIGHT:
				dmi.setSensorLightPower(sensor.getPower());
				break;
			case Sensor.TYPE_PRESSURE:
				dmi.setSensorPressurePower(sensor.getPower());
				break;
			case Sensor.TYPE_TEMPERATURE:
				dmi.setSensorTemperaturePower(sensor.getPower());
				break;
			case Sensor.TYPE_PROXIMITY:
				dmi.setSensorProximityPower(sensor.getPower());
				break;
			case Sensor.TYPE_GRAVITY:
				dmi.setSensorGravityPower(sensor.getPower());
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				dmi.setSensorLinearAccelerationPower(sensor.getPower());
				break;
			case Sensor.TYPE_ROTATION_VECTOR:
				dmi.setSensorRotationVectorPower(sensor.getPower());
				break;
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				dmi.setSensorRelativeHumidityPower(sensor.getPower());
				break;
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				dmi.setSensorAmbientTemperaturePower(sensor.getPower());
				break;
			case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
				dmi.setSensorUncalibratedMagneticFieldPower(sensor.getPower());
				break;
			case Sensor.TYPE_GAME_ROTATION_VECTOR:
				dmi.setSensorUncalibratedRotationVectorPower(sensor.getPower());
				break;
			case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
				dmi.setSensorUncalibratedGyroscopePower(sensor.getPower());
				break;
			case Sensor.TYPE_SIGNIFICANT_MOTION:
				dmi.setSensorSignificantMotionPower(sensor.getPower());
				break;
			}
		}

		dmi.dump();
	}

	public static long getMemSize(Context context) {
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		FileReader localFileReader = null;
		BufferedReader localBufferedReader = null;
		try {
			localFileReader = new FileReader(str1);
			localBufferedReader = new BufferedReader(localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			if (str2 == null) {
				return 0;
			}
			arrayOfString = str2.split("\\s+");
			if (arrayOfString != null && arrayOfString.length > 0) {
				initial_memory = Integer.valueOf(arrayOfString[1]) * 1024;
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		} catch (NumberFormatException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		} finally {
			try {
				if (localBufferedReader != null) {
					localBufferedReader.close();
				}
				if (localFileReader != null) {
					localFileReader.close();
				}
			} catch (IOException e) {
				Log.e(LOG_TAG, e.getLocalizedMessage(), e);
			}
		}
		return initial_memory;
	}

	static String getCpuModelName() {
		BufferedReader reader = null;
		try {
			String line;
			String modelName = null;
			String hardware = null;
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("/proc/cpuinfo"))), 1024);
			while ((line = reader.readLine()) != null) {
				if (modelName == null && line.startsWith("model name")) {
					modelName = line;
					break;
				}
				if (hardware == null && line.startsWith("Hardware")) {
					hardware = line;
				}
			}
			if (modelName != null) {
				int idx = modelName.indexOf(':');
				if (idx != -1) {
					return modelName.substring(idx + 1).trim();
				}
			} else if (hardware != null) {
				int idx = hardware.indexOf(':');
				if (idx != -1) {
					return hardware.substring(idx + 1).trim();
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getLocalizedMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ie) {
					Log.e(LOG_TAG, ie.getLocalizedMessage(), ie);
				}
			}
		}
		return null;
	}

	static class StorageInfo {
		long availableSize = -1L;
		long totalSize = -1L;
	}

	private StorageInfo getStorageInfo(File path) {
		if (path != null) {
			try {
				StatFs stat = new StatFs(path.getAbsolutePath());
				long blockSize = stat.getBlockSize();
				StorageInfo storageInfo = new StorageInfo();
				storageInfo.totalSize = stat.getBlockCount() * blockSize;
				storageInfo.availableSize = stat.getAvailableBlocks()
						* blockSize;

				return storageInfo;
			} catch (Exception e) {
				Log.e(LOG_TAG, "Cannot access path: " + path.getAbsolutePath(),
						e);
			}
		}

		return null;
	}

	private StorageInfo getInternalStorageInfo() {
		return getStorageInfo(Environment.getDataDirectory());
	}

	private StorageInfo getExternalStorageInfo() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
				|| Environment.MEDIA_MOUNTED.equals(state)) {
			return getStorageInfo(Environment.getExternalStorageDirectory());
		}

		return null;
	}

	void buildDeviceInfo(Context ctx) {

		DeviceInfo di = new DeviceInfo();
		StorageInfo storageInfo = getExternalStorageInfo();
		if (null != storageInfo) {
			di.setTotalSpaceExternal(storageInfo.totalSize);
			di.setAvailableSpaceExternal(storageInfo.availableSize);
		}
		storageInfo = getInternalStorageInfo();
		if (null != storageInfo) {
			di.setTotalSpaceInternal(storageInfo.totalSize);
			di.setAvailableSpaceInternal(storageInfo.availableSize);
		}
		long rt = SystemClock.elapsedRealtime();
		if (rt == 0) {
			rt = 1;
		}
		di.setTimeSinceBoot(rt);

		di.setTotalRxBytes(TrafficStats.getTotalRxBytes());
		di.setTotalTxBytes(TrafficStats.getTotalTxBytes());
		di.setMobileRxBytes(TrafficStats.getMobileRxBytes());
		di.setMobileTxBytes(TrafficStats.getMobileTxBytes());
		
		BatteryStats bs = new BatteryStats();
		 long uSecTime = SystemClock.elapsedRealtime() * 1000;
		 final long uSecNow = bs.computeBatteryRealtime(uSecTime, BatteryStats.STATS_SINCE_UNPLUGGED);
		 di.setScreenOnTime(bs.getScreenOnTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 ArrayList<Long> screenBrightnessTimes = new ArrayList<Long>();
		 for (int i = 0; i < BatteryStats.NUM_SCREEN_BRIGHTNESS_BINS; i++) {
			 screenBrightnessTimes.add(bs.getScreenBrightnessTime(i, uSecNow,
					 BatteryStats.STATS_SINCE_UNPLUGGED));
		 }
		 di.setScreenBrightnessTimes(screenBrightnessTimes);
		 ArrayList<Long> phoneSignalStrengthTime = new ArrayList<Long>();
		 for (int i = 0; i < BatteryStats.NUM_SIGNAL_STRENGTH_BINS; i++) {
			 phoneSignalStrengthTime.add(bs.getPhoneSignalStrengthTime(i, uSecNow,
					 BatteryStats.STATS_SINCE_UNPLUGGED));
		 }
		 di.setPhoneSignalStrengthTime(phoneSignalStrengthTime);
		 di.setPhoneSignalScanningTime(bs.getPhoneSignalScanningTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setPhoneOnTime(bs.getPhoneOnTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setWifiOnTime(bs.getWifiOnTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setWifiRunningTime(bs.getGlobalWifiRunningTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setBluetoothOnTime(bs.getBluetoothOnTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setBluetoothPingCount(bs.getBluetoothPingCount());
		 di.setMobileTcpBytesSent(bs.getMobileTcpBytesSent(BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setMobileTcpBytesReceived(bs.getMobileTcpBytesReceived(BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setTotalTcpBytesSent(bs.getTotalTcpBytesSent(BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setTotalTcpBytesReceived(bs.getTotalTcpBytesReceived(BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setTotalPhoneDataConnectionTime(bs.getTotalPhoneConnectionTime(uSecNow, BatteryStats.STATS_SINCE_UNPLUGGED));
		 di.setRadioDataUptime(bs.getRadioDataUptime());

		ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		di.setAvailableMemory(outInfo.availMem);
		di.setLowMemory(outInfo.lowMemory);

		di.dump();
	}

	void buildSettingsInfo(Context ctx) {
		SettingsInfo si = new SettingsInfo();

		int value = -1;
		float valuef = 0;
		float valuef1 = 0;
		ContentResolver contentResolver = ctx.getContentResolver();
		try {
			value = Settings.System.getInt(contentResolver,
					Settings.System.ACCELEROMETER_ROTATION);
		} catch (SettingNotFoundException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		si.setAutoRotateScreen(value == 1 ? true : false);

		try {
			valuef = Settings.System.getFloat(contentResolver,
					Settings.System.WINDOW_ANIMATION_SCALE);
		} catch (SettingNotFoundException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}

		Log.i(LOG_TAG, "WINDOW_ANIMATION_SCALE " + valuef);

		try {
			valuef1 = Settings.System.getFloat(contentResolver,
					Settings.System.TRANSITION_ANIMATION_SCALE);
		} catch (SettingNotFoundException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		Log.i(LOG_TAG, "TRANSITION_ANIMATION_SCALE " + valuef1);

		si.setScreenAnimation(valuef + valuef1);

		try {
			value = Settings.System.getInt(contentResolver,
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {

			Log.e(LOG_TAG, e.getMessage(), e);
		}
		si.setScreenBrightness(value);

		try {
			value = Settings.System.getInt(contentResolver,
					Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage(), e.getCause());
		}
		si.setAutomaticBrightness(value == 1 ? true : false);

		try {
			value = Settings.System.getInt(contentResolver,
					Settings.System.SCREEN_OFF_TIMEOUT);
		} catch (SettingNotFoundException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		si.setScreenTimeout(value);

		si.dump();
	}

	/**
	 * @param ctx
	 */
	void buildAppsInfo(Context ctx) {
		ArrayList<AppInfo> appsInfo = new ArrayList<AppInfo>();
		PackageManager pm = ctx.getPackageManager();
		List<PackageInfo> pInfos = pm
				.getInstalledPackages(PackageManager.GET_META_DATA);
		for (PackageInfo pInfo : pInfos) {
			if (!isSystemPackage(pInfo)) {
				final AppInfo appInfo = new AppInfo();
				appInfo.setPackageName(pInfo.packageName);
				appInfo.setVersionCode(pInfo.versionCode);
				appInfo.setVersionName(pInfo.versionName);

				Method getPackageSizeInfo;
				try {
					getPackageSizeInfo = pm.getClass().getMethod(
							"getPackageSizeInfo", String.class,
							IPackageStatsObserver.class);
					getPackageSizeInfo.invoke(pm, appInfo.getPackageName(),
							new IPackageStatsObserver.Stub() {

								@Override
								public void onGetStatsCompleted(
										PackageStats pStats, boolean succeeded)
										throws RemoteException {

									Log.i(LOG_TAG, "Code size: " + pStats.codeSize);
									Log.i(LOG_TAG, "Data size: " + pStats.dataSize);
									Log.i(LOG_TAG, "Cache size: " + pStats.cacheSize);
									appInfo.setTotalSize(pStats.dataSize
											+ pStats.codeSize);
									appInfo.setApplicationSize(pStats.codeSize);
									appInfo.setDataSize(pStats.dataSize);
									appInfo.setCacheSize(pStats.cacheSize);
								}
							});
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				ApplicationInfo aInfo = pInfo.applicationInfo;
				if (null == aInfo)
					continue;
				appInfo.setInstalledLocation(aInfo.flags
						& ApplicationInfo.FLAG_EXTERNAL_STORAGE);

				appsInfo.add(appInfo);
			}
		}
		
		ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningAppProcessInfo rapInfo : am.getRunningAppProcesses()) {
			for (AppInfo appInfo : appsInfo) {
				if (rapInfo.processName.equals(appInfo.getPackageName())) {
					Log.i(LOG_TAG, "Process name: " + rapInfo.processName);
					int pids[] = { rapInfo.pid };
					android.os.Debug.MemoryInfo[] memoryInfoArray = am
							.getProcessMemoryInfo(pids);
					appInfo.setMemoryUSS(memoryInfoArray[0].getTotalPrivateDirty());
					appInfo.setMemoryRSS(memoryInfoArray[0].getTotalSharedDirty());
					appInfo.setMemoryPSS(memoryInfoArray[0].getTotalPss());
				}
			}
		}
		
		BatteryStats bs = new BatteryStats();
		int statsType = BatteryStats.STATS_SINCE_UNPLUGGED;
		long statsPeriod = bs.computeBatteryRealtime(
                SystemClock.elapsedRealtime() * 1000, statsType);
		PowerProfile powerProfile = new PowerProfile(ctx);
		final int speedSteps = powerProfile.getNumSpeedSteps();
		SparseArray<? extends Uid> uidStats = bs.getUidStats();
        final int NU = uidStats.size();
        for (int iu = 0; iu < NU; iu++) {
            Uid u = uidStats.valueAt(iu);
            // for only one UID, there are maybe several different processes
            Map<String, ? extends Uid.Proc> processStats = u.getProcessStats();
            long cpuTime = 0;
            long cpuFgTime = 0;
            long wakelockTime = 0;
            long gpsTime = 0;
            long sensorAccelerometerTime = 0;
        	long sensorMagneticFieldTime = 0;
        	long sensorOrientationTime = 0;
        	long sensorGyroscopeTime = 0;
        	long sensorLightTime = 0;
        	long sensorPressureTime = 0;
        	long sensorTemperatureTime = 0;
        	long sensorProximityTime = 0;
        	long sensorGravityTime = 0;
        	long sensorLinearAccelerationTime = 0;
        	long sensorRotationVectorTime = 0;
        	long sensorRelativeHumidityTime = 0;
        	long sensorAmbientTemperatureTime = 0;
        	long sensorUncalibratedMagneticFieldTime = 0;
        	long sensorUncalibratedRotationVectorTime = 0;
        	long sensorUncalibratedGyroscopeTime = 0;
        	long sensorSignificantMotionTime = 0;
            int starts = 0;
            ArrayList<ProcessInfo> processInfo = new ArrayList<ProcessInfo>();
            if (processStats.size() > 0) {
                // Process CPU time
                for (Map.Entry<String, ? extends Uid.Proc> ent : processStats
                        .entrySet()) {
                	ProcessInfo pi = new ProcessInfo();
                    Uid.Proc process = ent.getValue();
                    final long userTime = process.getUserTime(statsType);
                    final long systemTime = process.getSystemTime(statsType);
                    final long foregroundTime = process
                            .getForegroundTime(statsType);
                    cpuFgTime += foregroundTime * 10; // convert to millisecond
                    final long tmpCpuTime = (userTime + systemTime) * 10; // convert to millisecond

                    starts += process.getStarts(statsType);
                    
                    final long[] cpuSpeedStepTimes = new long[speedSteps];
                    ArrayList<Long> speedTimes = new ArrayList<Long>();
                    // Get the total first
                    for (int step = 0; step < speedSteps; step++) {
                        cpuSpeedStepTimes[step] = process.getTimeAtCpuSpeedStep(
                                step, statsType);
                        speedTimes.add(cpuSpeedStepTimes[step]);
                    }
                    pi.setCpuSpeedStepTimes(speedTimes);
                    processInfo.add(pi);
                    cpuTime += tmpCpuTime;                 
                }
            } // processes
            
            if (cpuFgTime > cpuTime) {
                // if (DEBUG && cpuFgTime > cpuTime + 10000) {
                // Log.i(TAG,
                // "WARNING! Cputime is more than 10 seconds behind Foreground time");
                // }
                cpuTime = cpuFgTime; // Statistics may not have been gathered
                                     // yet.
            }
            
            // Process wake lock usage
            Map<String, ? extends BatteryStats.Uid.Wakelock> wakelockStats = u
                    .getWakelockStats();
            for (Map.Entry<String, ? extends BatteryStats.Uid.Wakelock> wakelockEntry : wakelockStats
                    .entrySet()) {
                Uid.Wakelock wakelock = wakelockEntry.getValue();
                // Only care about partial wake locks since full wake locks
                // are canceled when the user turns the screen off.
                BatteryStats.Timer timer = wakelock
                        .getWakeTime(BatteryStats.WAKE_TYPE_PARTIAL);
                if (timer != null) {
                    wakelockTime += timer.getTotalTimeLocked(statsPeriod,
                    		statsType);
                }
            }

            // data traffic
            long tcpBytesReceived = u.getTcpBytesReceived(statsType);
            long tcpBytesSent = u.getTcpBytesSent(statsType);
           
            // keeping WIFI running.
            long wifiRunningTime = u.getWifiRunningTime(statsPeriod,
                   statsType);

            // Process Sensor usage
            Map<Integer, ? extends BatteryStats.Uid.Sensor> sensorStats = u
                    .getSensorStats();
            for (Map.Entry<Integer, ? extends BatteryStats.Uid.Sensor> sensorEntry : sensorStats
                    .entrySet()) {
                Uid.Sensor sensor = sensorEntry.getValue();
                int sensorType = sensor.getHandle();
                BatteryStats.Timer timer = sensor.getSensorTime();

                if (null != timer) {
                    long sensorTime = timer.getTotalTimeLocked(statsPeriod,
                            statsType);
                    switch (sensorType) {
                    case Uid.Sensor.GPS:
                        gpsTime = sensorTime;
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                    	sensorAccelerometerTime = sensorTime;
                    	break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                    	sensorMagneticFieldTime = sensorTime;
                    	break;
                    case Sensor.TYPE_ORIENTATION:
                    	sensorOrientationTime = sensorTime;
                    	break;
                    case Sensor.TYPE_GYROSCOPE:
                    	sensorGyroscopeTime = sensorTime;
                    	break;
                    case Sensor.TYPE_LIGHT:
                    	sensorLightTime = sensorTime;
                    	break;
                    case Sensor.TYPE_PRESSURE:
                    	sensorPressureTime = sensorTime;
                    	break;
                    case Sensor.TYPE_TEMPERATURE:
                    	sensorTemperatureTime = sensorTime;
                    	break;
                    case Sensor.TYPE_PROXIMITY:
                    	sensorProximityTime = sensorTime;
                    	break;
                    case Sensor.TYPE_GRAVITY:
                    	sensorGravityTime = sensorTime;
                    	break;
                    case Sensor.TYPE_LINEAR_ACCELERATION:
                    	sensorLinearAccelerationTime = sensorTime;
                    	break;
                    case Sensor.TYPE_ROTATION_VECTOR:
                    	sensorRotationVectorTime = sensorTime;
                    	break;
                    case Sensor.TYPE_RELATIVE_HUMIDITY:
                    	sensorRelativeHumidityTime = sensorTime;
                    	break;
                    case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    	sensorAmbientTemperatureTime = sensorTime;
                    	break;
                    case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    	sensorUncalibratedMagneticFieldTime = sensorTime;
                    	break;
                    case Sensor.TYPE_GAME_ROTATION_VECTOR:
                    	sensorUncalibratedRotationVectorTime = sensorTime;
                    	break;
                    case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    	sensorUncalibratedGyroscopeTime = sensorTime;
                    	break;
                    case Sensor.TYPE_SIGNIFICANT_MOTION:
                    	sensorSignificantMotionTime = sensorTime;
                    	break;
                    default:
                    	break;
                    }                    
                }
            }
            
    		for (RunningAppProcessInfo rapInfo : am.getRunningAppProcesses()) {
    			for (AppInfo appInfo : appsInfo) {
    				if (rapInfo.processName.equals(appInfo.getPackageName())) {
    					if ( rapInfo.uid == u.getUid() ) {
    						appInfo.setCpuTimeTotal(cpuTime);
    						appInfo.setCpuTimeForeground(cpuFgTime);
    						appInfo.setLaunchCount(starts);
    						appInfo.setWifiRunningTime(wifiRunningTime);
    						appInfo.setWakeLockTime(wakelockTime);
    						appInfo.setTotalRxBytes(tcpBytesReceived);
    						appInfo.setTotalTxBytes(tcpBytesSent);
    						appInfo.setGpsTime(gpsTime);
    						appInfo.setSensorAccelerometerTime(sensorAccelerometerTime);
    						appInfo.setSensorAmbientTemperatureTime(sensorAmbientTemperatureTime);
    						appInfo.setSensorGravityTime(sensorGravityTime);
    						appInfo.setSensorGyroscopeTime(sensorGyroscopeTime);
    						appInfo.setSensorLightTime(sensorLightTime);
    						appInfo.setSensorLinearAccelerationTime(sensorLinearAccelerationTime);
    						appInfo.setSensorMagneticFieldTime(sensorMagneticFieldTime);
    						appInfo.setSensorOrientationTime(sensorOrientationTime);
    						appInfo.setSensorPressureTime(sensorPressureTime);
    						appInfo.setSensorProximityTime(sensorProximityTime);
    						appInfo.setSensorRelativeHumidityTime(sensorRelativeHumidityTime);
    						appInfo.setSensorRotationVectorTime(sensorRotationVectorTime);
    						appInfo.setSensorSignificantMotionTime(sensorSignificantMotionTime);
    						appInfo.setSensorTemperatureTime(sensorTemperatureTime);
    						appInfo.setSensorUncalibratedGyroscopeTime(sensorUncalibratedGyroscopeTime);
    						appInfo.setSensorUncalibratedMagneticFieldTime(sensorUncalibratedMagneticFieldTime);
    						appInfo.setSensorUncalibratedRotationVectorTime(sensorUncalibratedRotationVectorTime);
    					}
    				}
    			}
    		}
            
        }
		
		for (AppInfo appInfo : appsInfo) {
			appInfo.dump();
		}

	}

	private boolean isSystemPackage(PackageInfo pkgInfo) {
		return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}
}
