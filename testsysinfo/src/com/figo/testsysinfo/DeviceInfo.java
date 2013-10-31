package com.figo.testsysinfo;

import java.util.ArrayList;

import android.util.Log;

public class DeviceInfo {
	
	private final static String LOG_TAG = DeviceInfo.class.getSimpleName();
	private long totalSpaceExternal;
	private long availableSpaceExternal;
	private long totalSpaceInternal;
	private long availableSpaceInternal;
	private long timeSinceBoot;
	private long totalRxBytes;
	private long totalTxBytes;
	private long mobileRxBytes;
	private long mobileTxBytes;
	private long consumedTotalPower; // derived from MASIA
	private long consumedWifiPower; // derived from MASIA
	private long consumedBluetoothPower; // derived from MASIA
	private long consumedScreenPower; // derived from MASIA
	private long screenOnTime;
	private ArrayList<Long> screenBrightnessTimes;
	private ArrayList<Long> phoneSignalStrengthTime;
	private long phoneSignalScanningTime;
	private long phoneOnTime;
	private long wifiOnTime;
	private long wifiRunningTime;
	private long bluetoothOnTime;
	private long bluetoothPingCount;
	private long mobileTcpBytesSent;
	private long mobileTcpBytesReceived;
	private long totalTcpBytesSent;
	private long totalTcpBytesReceived;
	private long totalPhoneDataConnectionTime;
	private long radioDataUptime;
	private long availableMemory;
	private boolean lowMemory;
	public long getTotalSpaceExternal() {
		return totalSpaceExternal;
	}
	public void setTotalSpaceExternal(long totalSpaceExternal) {
		this.totalSpaceExternal = totalSpaceExternal;
	}
	public long getAvailableSpaceExternal() {
		return availableSpaceExternal;
	}
	public void setAvailableSpaceExternal(long availableSpaceExternal) {
		this.availableSpaceExternal = availableSpaceExternal;
	}
	public long getTotalSpaceInternal() {
		return totalSpaceInternal;
	}
	public void setTotalSpaceInternal(long totalSpaceInternal) {
		this.totalSpaceInternal = totalSpaceInternal;
	}
	public long getAvailableSpaceInternal() {
		return availableSpaceInternal;
	}
	public void setAvailableSpaceInternal(long availableSpaceInternal) {
		this.availableSpaceInternal = availableSpaceInternal;
	}
	public long getTimeSinceBoot() {
		return timeSinceBoot;
	}
	public void setTimeSinceBoot(long timeSinceBoot) {
		this.timeSinceBoot = timeSinceBoot;
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
	public long getMobileRxBytes() {
		return mobileRxBytes;
	}
	public void setMobileRxBytes(long mobileRxBytes) {
		this.mobileRxBytes = mobileRxBytes;
	}
	public long getMobileTxBytes() {
		return mobileTxBytes;
	}
	public void setMobileTxBytes(long mobileTxBytes) {
		this.mobileTxBytes = mobileTxBytes;
	}
	public long getConsumedTotalPower() {
		return consumedTotalPower;
	}
	public void setConsumedTotalPower(long consumedTotalPower) {
		this.consumedTotalPower = consumedTotalPower;
	}
	public long getConsumedWifiPower() {
		return consumedWifiPower;
	}
	public void setConsumedWifiPower(long consumedWifiPower) {
		this.consumedWifiPower = consumedWifiPower;
	}
	public long getConsumedBluetoothPower() {
		return consumedBluetoothPower;
	}
	public void setConsumedBluetoothPower(long consumedBluetoothPower) {
		this.consumedBluetoothPower = consumedBluetoothPower;
	}
	public long getConsumedScreenPower() {
		return consumedScreenPower;
	}
	public void setConsumedScreenPower(long consumedScreenPower) {
		this.consumedScreenPower = consumedScreenPower;
	}
	public long getScreenOnTime() {
		return screenOnTime;
	}
	public void setScreenOnTime(long screenOnTime) {
		this.screenOnTime = screenOnTime;
	}
	public ArrayList<Long> getPhoneSignalStrengthTime() {
		return phoneSignalStrengthTime;
	}
	public void setPhoneSignalStrengthTime(ArrayList<Long> phoneSignalStrengthTime) {
		this.phoneSignalStrengthTime = phoneSignalStrengthTime;
	}
	public long getPhoneSignalScanningTime() {
		return phoneSignalScanningTime;
	}
	public void setPhoneSignalScanningTime(long phoneSignalScanningTime) {
		this.phoneSignalScanningTime = phoneSignalScanningTime;
	}
	public long getPhoneOnTime() {
		return phoneOnTime;
	}
	public void setPhoneOnTime(long phoneOnTime) {
		this.phoneOnTime = phoneOnTime;
	}
	public long getWifiOnTime() {
		return wifiOnTime;
	}
	public void setWifiOnTime(long wifiOnTime) {
		this.wifiOnTime = wifiOnTime;
	}
	public long getWifiRunningTime() {
		return wifiRunningTime;
	}
	public void setWifiRunningTime(long wifiRunningTime) {
		this.wifiRunningTime = wifiRunningTime;
	}
	public long getBluetoothOnTime() {
		return bluetoothOnTime;
	}
	public void setBluetoothOnTime(long bluetoothOnTime) {
		this.bluetoothOnTime = bluetoothOnTime;
	}
	public long getBluetoothPingCount() {
		return bluetoothPingCount;
	}
	public void setBluetoothPingCount(long bluetoothPingCount) {
		this.bluetoothPingCount = bluetoothPingCount;
	}
	public long getMobileTcpBytesSent() {
		return mobileTcpBytesSent;
	}
	public void setMobileTcpBytesSent(long mobileTcpBytesSent) {
		this.mobileTcpBytesSent = mobileTcpBytesSent;
	}
	public long getMobileTcpBytesReceived() {
		return mobileTcpBytesReceived;
	}
	public void setMobileTcpBytesReceived(long mobileTcpBytesReceived) {
		this.mobileTcpBytesReceived = mobileTcpBytesReceived;
	}
	public long getTotalTcpBytesSent() {
		return totalTcpBytesSent;
	}
	public void setTotalTcpBytesSent(long totalTcpBytesSent) {
		this.totalTcpBytesSent = totalTcpBytesSent;
	}
	public long getTotalTcpBytesReceived() {
		return totalTcpBytesReceived;
	}
	public void setTotalTcpBytesReceived(long totalTcpBytesReceived) {
		this.totalTcpBytesReceived = totalTcpBytesReceived;
	}
	public long getTotalPhoneDataConnectionTime() {
		return totalPhoneDataConnectionTime;
	}
	public void setTotalPhoneDataConnectionTime(long totalPhoneDataConnectionTime) {
		this.totalPhoneDataConnectionTime = totalPhoneDataConnectionTime;
	}
	public long getRadioDataUptime() {
		return radioDataUptime;
	}
	public void setRadioDataUptime(long radioDataUptime) {
		this.radioDataUptime = radioDataUptime;
	}
	public long getAvailableMemory() {
		return availableMemory;
	}
	public void setAvailableMemory(long availableMemory) {
		this.availableMemory = availableMemory;
	}
	public boolean isLowMemory() {
		return lowMemory;
	}
	public void setLowMemory(boolean lowMemory) {
		this.lowMemory = lowMemory;
	}
	
	public void dump () {
		Log.i(LOG_TAG, "Total space external : " + this.getTotalSpaceExternal());
		Log.i(LOG_TAG, "Available space external : " + this.getAvailableSpaceExternal());
		Log.i(LOG_TAG, "Total space internal : " + this.getTotalSpaceInternal());
		Log.i(LOG_TAG, "Available space internal : " + this.getAvailableSpaceInternal());
		Log.i(LOG_TAG, "Time since boot : " + this.getTimeSinceBoot());
		Log.i(LOG_TAG, "Total Rx bytes : " + this.getTotalRxBytes());
		Log.i(LOG_TAG, "Total Tx bytes : " + this.getTotalTxBytes());
		Log.i(LOG_TAG, "Mobile Rx bytes : " + this.getMobileRxBytes());
		Log.i(LOG_TAG, "Mobile Tx bytes : " + this.getMobileTxBytes());
		Log.i(LOG_TAG, "Screen on time : " + this.getScreenOnTime());
		 for (int i = 0; i < BatteryStats.NUM_SCREEN_BRIGHTNESS_BINS; i++) {
			 Log.i(LOG_TAG, "Screen brightness time : " + this.getScreenBrightnessTimes().get(i));
		 }
		 for (int i = 0; i < BatteryStats.NUM_SIGNAL_STRENGTH_BINS; i++) {
			 Log.i(LOG_TAG, "Phone signal strength time : " + this.getPhoneSignalStrengthTime().get(i));
		 }
		Log.i(LOG_TAG, "Phone signal scanning time : " + this.getPhoneSignalScanningTime());
		Log.i(LOG_TAG, "Phone on time : " + this.getPhoneOnTime());
		Log.i(LOG_TAG, "Wifi on time : " + this.getWifiOnTime());
		Log.i(LOG_TAG, "Wifi running time : " + this.getWifiRunningTime());
		Log.i(LOG_TAG, "Bluetooth on time : " + this.getBluetoothOnTime());
		Log.i(LOG_TAG, "Bluetooth ping count : " + this.getBluetoothPingCount());
		Log.i(LOG_TAG, "Mobile tcp bytes sent : " + this.getMobileTcpBytesSent());
		Log.i(LOG_TAG, "Mobile tcp bytes received : " + this.getMobileTcpBytesReceived());
		Log.i(LOG_TAG, "Total tcp bytes sent : " + this.getTotalTcpBytesSent());
		Log.i(LOG_TAG, "Total tcp bytes received : " + this.getTotalTcpBytesReceived());
		Log.i(LOG_TAG, "Total phone data connection time : " + this.getTotalPhoneDataConnectionTime());
		Log.i(LOG_TAG, "Radio data uptime : " + this.getRadioDataUptime());
		Log.i(LOG_TAG, "Available memory : " + this.getAvailableMemory());
		Log.i(LOG_TAG, "Low memory : " + this.isLowMemory());
	}
	public ArrayList<Long> getScreenBrightnessTimes() {
		return screenBrightnessTimes;
	}
	public void setScreenBrightnessTimes(ArrayList<Long> screenBrightnessTimes) {
		this.screenBrightnessTimes = screenBrightnessTimes;
	}
}
