package com.figo.testsysinfo;

import java.util.ArrayList;

import android.util.Log;

public class AppInfo {
	
	private final static String LOG_TAG = AppInfo.class.getSimpleName();
	
	private String packageName;
	private long versionCode;
	private String versionName;
	private long totalSize;
	private long applicationSize;
	private long dataSize;
	private long cacheSize;
	private long cpuTimeTotal;
	private long cpuTimeForeground;
	private long memoryUSS;
	private long memoryRSS;
	private long memoryPSS;
	private int installedLocation;
	private int launchCount;
	private long usageTime;
	private long wifiRunningTime;
	private long wakeLockTime;
	private long consumedPower;
	private long consumedPowerPercentage;
	private long totalRxBytes;
	private long totalTxBytes;
	private long gpsTime;
	private long sensorAccelerometerTime;
	private long sensorMagneticFieldTime;
	private long sensorOrientationTime;
	private long sensorGyroscopeTime;
	private long sensorLightTime;
	private long sensorPressureTime;
	private long sensorTemperatureTime;
	private long sensorProximityTime;
	private long sensorGravityTime;
	private long sensorLinearAccelerationTime;
	private long sensorRotationVectorTime;
	private long sensorRelativeHumidityTime;
	private long sensorAmbientTemperatureTime;
	private long sensorUncalibratedMagneticFieldTime;
	private long sensorUncalibratedRotationVectorTime;
	private long sensorUncalibratedGyroscopeTime;
	private long sensorSignificantMotionTime;
	private ArrayList<ProcessInfo> processInfo;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public long getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(long versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public long getDataSize() {
		return dataSize;
	}
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
	public long getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}
	public long getApplicationSize() {
		return applicationSize;
	}
	public void setApplicationSize(long applicationSize) {
		this.applicationSize = applicationSize;
	}
	public long getCpuTimeTotal() {
		return cpuTimeTotal;
	}
	public void setCpuTimeTotal(long cpuTimeTotal) {
		this.cpuTimeTotal = cpuTimeTotal;
	}
	public long getCpuTimeForeground() {
		return cpuTimeForeground;
	}
	public void setCpuTimeForeground(long cpuTimeForeground) {
		this.cpuTimeForeground = cpuTimeForeground;
	}
	public long getMemoryUSS() {
		return memoryUSS;
	}
	public void setMemoryUSS(long memoryUSS) {
		this.memoryUSS = memoryUSS;
	}
	public long getMemoryRSS() {
		return memoryRSS;
	}
	public void setMemoryRSS(long memoryRSS) {
		this.memoryRSS = memoryRSS;
	}
	public long getMemoryPSS() {
		return memoryPSS;
	}
	public void setMemoryPSS(long memoryPSS) {
		this.memoryPSS = memoryPSS;
	}
	public int getInstalledLocation() {
		return installedLocation;
	}
	public void setInstalledLocation(int installedLocation) {
		this.installedLocation = installedLocation;
	}
	public int getLaunchCount() {
		return launchCount;
	}
	public void setLaunchCount(int launchCount) {
		this.launchCount = launchCount;
	}
	public long getUsageTime() {
		return usageTime;
	}
	public void setUsageTime(long usageTime) {
		this.usageTime = usageTime;
	}
	public long getWifiRunningTime() {
		return wifiRunningTime;
	}
	public void setWifiRunningTime(long wifiRunningTime) {
		this.wifiRunningTime = wifiRunningTime;
	}
	public long getWakeLockTime() {
		return wakeLockTime;
	}
	public void setWakeLockTime(long wakeLockTime) {
		this.wakeLockTime = wakeLockTime;
	}
	public long getConsumedPower() {
		return consumedPower;
	}
	public void setConsumedPower(long consumedPower) {
		this.consumedPower = consumedPower;
	}
	public long getConsumedPowerPercentage() {
		return consumedPowerPercentage;
	}
	public void setConsumedPowerPercentage(long consumedPowerPercentage) {
		this.consumedPowerPercentage = consumedPowerPercentage;
	}
	public long getTotalRxBytes() {
		return totalRxBytes;
	}
	public void setTotalRxBytes(long totalRxBytes) {
		this.totalRxBytes = totalRxBytes;
	}
	public long getTotalTxBytes() {
		return totalTxBytes;
	}
	public void setTotalTxBytes(long totalTxBytes) {
		this.totalTxBytes = totalTxBytes;
	}
	public long getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}
	public long getSensorAccelerometerTime() {
		return sensorAccelerometerTime;
	}
	public void setSensorAccelerometerTime(long sensorAccelerometerTime) {
		this.sensorAccelerometerTime = sensorAccelerometerTime;
	}
	public long getSensorMagneticFieldTime() {
		return sensorMagneticFieldTime;
	}
	public void setSensorMagneticFieldTime(long sensorMagneticFieldTime) {
		this.sensorMagneticFieldTime = sensorMagneticFieldTime;
	}
	public long getSensorOrientationTime() {
		return sensorOrientationTime;
	}
	public void setSensorOrientationTime(long sensorOrientationTime) {
		this.sensorOrientationTime = sensorOrientationTime;
	}
	public long getSensorGyroscopeTime() {
		return sensorGyroscopeTime;
	}
	public void setSensorGyroscopeTime(long sensorGyroscopeTime) {
		this.sensorGyroscopeTime = sensorGyroscopeTime;
	}
	public long getSensorLightTime() {
		return sensorLightTime;
	}
	public void setSensorLightTime(long sensorLightTime) {
		this.sensorLightTime = sensorLightTime;
	}
	public long getSensorPressureTime() {
		return sensorPressureTime;
	}
	public void setSensorPressureTime(long sensorPressureTime) {
		this.sensorPressureTime = sensorPressureTime;
	}
	public long getSensorTemperatureTime() {
		return sensorTemperatureTime;
	}
	public void setSensorTemperatureTime(long sensorTemperatureTime) {
		this.sensorTemperatureTime = sensorTemperatureTime;
	}
	public long getSensorProximityTime() {
		return sensorProximityTime;
	}
	public void setSensorProximityTime(long sensorProximityTime) {
		this.sensorProximityTime = sensorProximityTime;
	}
	public long getSensorGravityTime() {
		return sensorGravityTime;
	}
	public void setSensorGravityTime(long sensorGravityTime) {
		this.sensorGravityTime = sensorGravityTime;
	}
	public long getSensorLinearAccelerationTime() {
		return sensorLinearAccelerationTime;
	}
	public void setSensorLinearAccelerationTime(long sensorLinearAccelerationTime) {
		this.sensorLinearAccelerationTime = sensorLinearAccelerationTime;
	}
	public long getSensorRotationVectorTime() {
		return sensorRotationVectorTime;
	}
	public void setSensorRotationVectorTime(long sensorRotationVectorTime) {
		this.sensorRotationVectorTime = sensorRotationVectorTime;
	}
	public long getSensorRelativeHumidityTime() {
		return sensorRelativeHumidityTime;
	}
	public void setSensorRelativeHumidityTime(long sensorRelativeHumidityTime) {
		this.sensorRelativeHumidityTime = sensorRelativeHumidityTime;
	}
	public long getSensorAmbientTemperatureTime() {
		return sensorAmbientTemperatureTime;
	}
	public void setSensorAmbientTemperatureTime(long sensorAmbientTemperatureTime) {
		this.sensorAmbientTemperatureTime = sensorAmbientTemperatureTime;
	}
	public long getSensorUncalibratedMagneticFieldTime() {
		return sensorUncalibratedMagneticFieldTime;
	}
	public void setSensorUncalibratedMagneticFieldTime(
			long sensorUncalibratedMagneticFieldTime) {
		this.sensorUncalibratedMagneticFieldTime = sensorUncalibratedMagneticFieldTime;
	}
	public long getSensorUncalibratedRotationVectorTime() {
		return sensorUncalibratedRotationVectorTime;
	}
	public void setSensorUncalibratedRotationVectorTime(
			long sensorUncalibratedRotationVectorTime) {
		this.sensorUncalibratedRotationVectorTime = sensorUncalibratedRotationVectorTime;
	}
	public long getSensorUncalibratedGyroscopeTime() {
		return sensorUncalibratedGyroscopeTime;
	}
	public void setSensorUncalibratedGyroscopeTime(
			long sensorUncalibratedGyroscopeTime) {
		this.sensorUncalibratedGyroscopeTime = sensorUncalibratedGyroscopeTime;
	}
	public long getSensorSignificantMotionTime() {
		return sensorSignificantMotionTime;
	}
	public void setSensorSignificantMotionTime(long sensorSignificantMotionTime) {
		this.sensorSignificantMotionTime = sensorSignificantMotionTime;
	}
	
	public void dump () {
		Log.i(LOG_TAG, "Package name : " + this.getPackageName());
		Log.i(LOG_TAG, "Version code : " + this.getVersionCode());
		Log.i(LOG_TAG, "Version name : " + this.getVersionName());
		Log.i(LOG_TAG, "Total size : " + this.getTotalSize());
		Log.i(LOG_TAG, "Application size : " + this.getApplicationSize());
		Log.i(LOG_TAG, "Data size : " + this.getDataSize());
		Log.i(LOG_TAG, "Cache size : " + this.getCacheSize());
		Log.i(LOG_TAG, "CPU time total : " + this.getCpuTimeTotal());
		Log.i(LOG_TAG, "CPU time foreground : " + this.getCpuTimeForeground());
		Log.i(LOG_TAG, "Memory USS : " + this.getMemoryUSS());
		Log.i(LOG_TAG, "Memory RSS : " + this.getMemoryRSS());
		Log.i(LOG_TAG, "Memory PSS : " + this.getMemoryPSS());
		Log.i(LOG_TAG, "Installed Location : " + this.getInstalledLocation());
		Log.i(LOG_TAG, "Launch count : " + this.getLaunchCount());
		Log.i(LOG_TAG, "Usage time : " + this.getUsageTime());
		Log.i(LOG_TAG, "WiFi running time : " + this.getWifiRunningTime());
		Log.i(LOG_TAG, "Wakelock time : " + this.getWakeLockTime());
		Log.i(LOG_TAG, "Consumed power : " + this.getConsumedPower());
		Log.i(LOG_TAG, "Consumed power percentage : " + this.getConsumedPowerPercentage());
		Log.i(LOG_TAG, "TotalRxBytes : " + this.getTotalRxBytes());
		Log.i(LOG_TAG, "TotalTxBytes : " + this.getTotalTxBytes());
		Log.i(LOG_TAG, "GPS time : " + this.getGpsTime());
		Log.i(LOG_TAG, "Sensor Accelerometer time : " + this.getSensorAccelerometerTime());
		Log.i(LOG_TAG, "Sensor MagneticField time : " + this.getSensorMagneticFieldTime());
		Log.i(LOG_TAG, "Sensor Orientation time : " + this.getSensorOrientationTime());
		Log.i(LOG_TAG, "Sensor Gyroscope time : " + this.getSensorGyroscopeTime());
		Log.i(LOG_TAG, "Sensor Light time : " + this.getSensorLightTime());
		Log.i(LOG_TAG, "Sensor Pressure time : " + this.getSensorPressureTime());
		Log.i(LOG_TAG, "Sensor Temperature time : " + this.getSensorTemperatureTime());
		Log.i(LOG_TAG, "Sensor Proximity time : " + this.getSensorProximityTime());
		Log.i(LOG_TAG, "Sensor Gravity time : " + this.getSensorGravityTime());
		Log.i(LOG_TAG, "Sensor Linear Acceleration time : " + this.getSensorLinearAccelerationTime());
		Log.i(LOG_TAG, "Sensor Rotation Vector time : " + this.getSensorRotationVectorTime());
		Log.i(LOG_TAG, "Sensor Relative Humidity time : " + this.getSensorRelativeHumidityTime());
		Log.i(LOG_TAG, "Sensor Ambient Temperature time : " + this.getSensorAmbientTemperatureTime());
		Log.i(LOG_TAG, "Sensor Uncalibrated MagneticField time : " + this.getSensorUncalibratedMagneticFieldTime());
		Log.i(LOG_TAG, "Sensor Uncalibrated RotationVector time : " + this.getSensorUncalibratedRotationVectorTime());
		Log.i(LOG_TAG, "Sensor Uncalibrated Gyroscope time : " + this.getSensorUncalibratedGyroscopeTime());
		Log.i(LOG_TAG, "Sensor Significant Motion time : " + this.getSensorSignificantMotionTime());
	}
	public ArrayList<ProcessInfo> getProcessInfo() {
		return processInfo;
	}
	public void setProcessInfo(ArrayList<ProcessInfo> processInfo) {
		this.processInfo = processInfo;
	}
}
