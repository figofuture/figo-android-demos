package com.figo.testsysinfo;

import java.util.ArrayList;

import android.util.Log;

public class DeviceMetaInfo {
	
	private final static String LOG_TAG = DeviceMetaInfo.class.getSimpleName();

	private String manufacturer;
	private String model;
	private int sdkVersionCode;
	private String countryCode;
	private String languageCode;
	private String networkOperator;
	private String simOperator;
	private int memoryClass;
	private long memoryThreshold;
	private long totalMemory;
	private String cpuModelName;
	private double batteryCapacity;
	private double screenOnPower;
	private double screenFullPower;
	private double bluetoothOnPower;
	private double bluetoothATPower;
	private double bluetoothActivePower;
	private double wifiOnPower;
	private double wifiScanPower;
	private double wifiActivePower;
	private double dspAudioPower;
	private double dspVideoPower;
	private double radioOnLevel0;
	private double radioOnLevel1;
	private double radioOnLevel2;
	private double radioOnLevel3;
	private double radioOnLevel4;
	private double radioScanningPower;
	private double radioActivePower;
	private double gpsOnPower;
	private double cpuIdlePower;
	private double cpuAwakePower;
	private ArrayList<Double> cpuSpeeds;
	private ArrayList<Double> cpuActivePowers;
	private float sensorAccelerometerPower;
	private float sensorMagneticFieldPower;
	private float sensorOrientationPower;
	private float sensorGyroscopePower;
	private float sensorLightPower;
	private float sensorPressurePower;
	private float sensorTemperaturePower;
	private float sensorProximityPower;
	private float sensorGravityPower;
	private float sensorLinearAccelerationPower;
	private float sensorRotationVectorPower;
	private float sensorRelativeHumidityPower;
	private float sensorAmbientTemperaturePower;
	private float sensorUncalibratedMagneticFieldPower;
	private float sensorUncalibratedRotationVectorPower;
	private float sensorUncalibratedGyroscopePower;
	private float sensorSignificantMotionPower;
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getSdkVersionCode() {
		return sdkVersionCode;
	}
	public void setSdkVersionCode(int sdkVersionCode) {
		this.sdkVersionCode = sdkVersionCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getNetworkOperator() {
		return networkOperator;
	}
	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}
	public String getSimOperator() {
		return simOperator;
	}
	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}
	public int getMemoryClass() {
		return memoryClass;
	}
	public void setMemoryClass(int memoryClass) {
		this.memoryClass = memoryClass;
	}
	public long getMemoryThreshold() {
		return memoryThreshold;
	}
	public void setMemoryThreshold(long memoryThreshold) {
		this.memoryThreshold = memoryThreshold;
	}
	public long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public String getCpuModelName() {
		return cpuModelName;
	}
	public void setCpuModelName(String cpuModelName) {
		this.cpuModelName = cpuModelName;
	}
	public double getBatteryCapacity() {
		return batteryCapacity;
	}
	public void setBatteryCapacity(double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public double getScreenOnPower() {
		return screenOnPower;
	}
	public void setScreenOnPower(double screenOnPower) {
		this.screenOnPower = screenOnPower;
	}
	public double getScreenFullPower() {
		return screenFullPower;
	}
	public void setScreenFullPower(double screenFullPower) {
		this.screenFullPower = screenFullPower;
	}
	public double getBluetoothOnPower() {
		return bluetoothOnPower;
	}
	public void setBluetoothOnPower(double bluetoothOnPower) {
		this.bluetoothOnPower = bluetoothOnPower;
	}
	public double getBluetoothATPower() {
		return bluetoothATPower;
	}
	public void setBluetoothATPower(double bluetoothATPower) {
		this.bluetoothATPower = bluetoothATPower;
	}
	public double getBluetoothActivePower() {
		return bluetoothActivePower;
	}
	public void setBluetoothActivePower(double bluetoothActivePower) {
		this.bluetoothActivePower = bluetoothActivePower;
	}
	public double getWifiOnPower() {
		return wifiOnPower;
	}
	public void setWifiOnPower(double wifiOnPower) {
		this.wifiOnPower = wifiOnPower;
	}
	public double getWifiScanPower() {
		return wifiScanPower;
	}
	public void setWifiScanPower(double wifiScanPower) {
		this.wifiScanPower = wifiScanPower;
	}
	public double getWifiActivePower() {
		return wifiActivePower;
	}
	public void setWifiActivePower(double wifiActivePower) {
		this.wifiActivePower = wifiActivePower;
	}
	public double getDspAudioPower() {
		return dspAudioPower;
	}
	public void setDspAudioPower(double dspAudioPower) {
		this.dspAudioPower = dspAudioPower;
	}
	public double getDspVideoPower() {
		return dspVideoPower;
	}
	public void setDspVideoPower(double dspVideoPower) {
		this.dspVideoPower = dspVideoPower;
	}
	public double getRadioOnLevel0() {
		return radioOnLevel0;
	}
	public void setRadioOnLevel0(double radioOnLevel0) {
		this.radioOnLevel0 = radioOnLevel0;
	}
	public double getSensorSignificantMotionPower() {
		return sensorSignificantMotionPower;
	}
	public void setSensorSignificantMotionPower(float sensorSignificantMotionPower) {
		this.sensorSignificantMotionPower = sensorSignificantMotionPower;
	}
	public double getSensorUncalibratedGyroscopePower() {
		return sensorUncalibratedGyroscopePower;
	}
	public void setSensorUncalibratedGyroscopePower(
			float sensorUncalibratedGyroscopePower) {
		this.sensorUncalibratedGyroscopePower = sensorUncalibratedGyroscopePower;
	}
	public float getSensorUncalibratedRotationVectorPower() {
		return sensorUncalibratedRotationVectorPower;
	}
	public void setSensorUncalibratedRotationVectorPower(
			float sensorUncalibratedRotationVectorPower) {
		this.sensorUncalibratedRotationVectorPower = sensorUncalibratedRotationVectorPower;
	}
	public float getSensorUncalibratedMagneticFieldPower() {
		return sensorUncalibratedMagneticFieldPower;
	}
	public void setSensorUncalibratedMagneticFieldPower(
			float sensorUncalibratedMagneticFieldPower) {
		this.sensorUncalibratedMagneticFieldPower = sensorUncalibratedMagneticFieldPower;
	}
	public float getSensorAmbientTemperaturePower() {
		return sensorAmbientTemperaturePower;
	}
	public void setSensorAmbientTemperaturePower(
			float sensorAmbientTemperaturePower) {
		this.sensorAmbientTemperaturePower = sensorAmbientTemperaturePower;
	}
	public float getSensorRotationVectorPower() {
		return sensorRotationVectorPower;
	}
	public void setSensorRotationVectorPower(float sensorRotationVectorPower) {
		this.sensorRotationVectorPower = sensorRotationVectorPower;
	}
	public float getSensorRelativeHumidityPower() {
		return sensorRelativeHumidityPower;
	}
	public void setSensorRelativeHumidityPower(float sensorRelativeHumidityPower) {
		this.sensorRelativeHumidityPower = sensorRelativeHumidityPower;
	}
	public float getSensorGyroscopePower() {
		return sensorGyroscopePower;
	}
	public void setSensorGyroscopePower(float sensorGyroscopePower) {
		this.sensorGyroscopePower = sensorGyroscopePower;
	}
	public float getSensorTemperaturePower() {
		return sensorTemperaturePower;
	}
	public void setSensorTemperaturePower(float sensorTemperaturePower) {
		this.sensorTemperaturePower = sensorTemperaturePower;
	}
	public float getSensorPressurePower() {
		return sensorPressurePower;
	}
	public void setSensorPressurePower(float sensorPressurePower) {
		this.sensorPressurePower = sensorPressurePower;
	}
	public float getSensorLightPower() {
		return sensorLightPower;
	}
	public void setSensorLightPower(float sensorLightPower) {
		this.sensorLightPower = sensorLightPower;
	}
	public float getSensorLinearAccelerationPower() {
		return sensorLinearAccelerationPower;
	}
	public void setSensorLinearAccelerationPower(
			float sensorLinearAccelerationPower) {
		this.sensorLinearAccelerationPower = sensorLinearAccelerationPower;
	}
	public float getSensorGravityPower() {
		return sensorGravityPower;
	}
	public void setSensorGravityPower(float sensorGravityPower) {
		this.sensorGravityPower = sensorGravityPower;
	}
	public float getSensorProximityPower() {
		return sensorProximityPower;
	}
	public void setSensorProximityPower(float sensorProximityPower) {
		this.sensorProximityPower = sensorProximityPower;
	}
	public float getSensorOrientationPower() {
		return sensorOrientationPower;
	}
	public void setSensorOrientationPower(float sensorOrientationPower) {
		this.sensorOrientationPower = sensorOrientationPower;
	}
	public float getSensorMagneticFieldPower() {
		return sensorMagneticFieldPower;
	}
	public void setSensorMagneticFieldPower(float sensorMagneticFieldPower) {
		this.sensorMagneticFieldPower = sensorMagneticFieldPower;
	}
	public float getSensorAccelerometerPower() {
		return sensorAccelerometerPower;
	}
	public void setSensorAccelerometerPower(float sensorAccelerometerPower) {
		this.sensorAccelerometerPower = sensorAccelerometerPower;
	}
	public ArrayList<Double> getCpuActivePowers() {
		return cpuActivePowers;
	}
	public void setCpuActivePowers(ArrayList<Double> cpuActivePowers) {
		this.cpuActivePowers = cpuActivePowers;
	}
	public ArrayList<Double> getCpuSpeeds() {
		return cpuSpeeds;
	}
	public void setCpuSpeeds(ArrayList<Double> cpuSpeeds) {
		this.cpuSpeeds = cpuSpeeds;
	}
	public double getCpuAwakePower() {
		return cpuAwakePower;
	}
	public void setCpuAwakePower(double cpuAwakePower) {
		this.cpuAwakePower = cpuAwakePower;
	}
	public double getCpuIdlePower() {
		return cpuIdlePower;
	}
	public void setCpuIdlePower(double cpuIdlePower) {
		this.cpuIdlePower = cpuIdlePower;
	}
	public double getRadioActivePower() {
		return radioActivePower;
	}
	public void setRadioActivePower(double radioActivePower) {
		this.radioActivePower = radioActivePower;
	}
	public double getRadioOnLevel1() {
		return radioOnLevel1;
	}
	public void setRadioOnLevel1(double radioOnLevel1) {
		this.radioOnLevel1 = radioOnLevel1;
	}
	public double getRadioOnLevel2() {
		return radioOnLevel2;
	}
	public void setRadioOnLevel2(double radioOnLevel2) {
		this.radioOnLevel2 = radioOnLevel2;
	}
	public double getRadioOnLevel3() {
		return radioOnLevel3;
	}
	public void setRadioOnLevel3(double radioOnLevel3) {
		this.radioOnLevel3 = radioOnLevel3;
	}
	public double getRadioScanningPower() {
		return radioScanningPower;
	}
	public void setRadioScanningPower(double radioScanningPower) {
		this.radioScanningPower = radioScanningPower;
	}
	public double getRadioOnLevel4() {
		return radioOnLevel4;
	}
	public void setRadioOnLevel4(double radioOnLevel4) {
		this.radioOnLevel4 = radioOnLevel4;
	}
	public double getGpsOnPower() {
		return gpsOnPower;
	}
	public void setGpsOnPower(double gpsOnPower) {
		this.gpsOnPower = gpsOnPower;
	}
	
	public void dump () {
		Log.i(LOG_TAG, "Manufacturer : " + this.getManufacturer());
		Log.i(LOG_TAG, "Model : " + this.getModel());
		Log.i(LOG_TAG, "SDK version code : " + this.getSdkVersionCode());
		Log.i(LOG_TAG, "Country code : " + this.getCountryCode());
		Log.i(LOG_TAG, "Language code : " + this.getLanguageCode());
		Log.i(LOG_TAG, "Network operator : " + this.getNetworkOperator());
		Log.i(LOG_TAG, "SIM operator : " + this.getSimOperator());
		Log.i(LOG_TAG, "Memory class (MB) : " + this.getMemoryClass());
		Log.i(LOG_TAG, "Memory threshold : " + this.getMemoryThreshold());
		Log.i(LOG_TAG, "Total memory : " + this.getTotalMemory());
		Log.i(LOG_TAG, "CPU model name : " + this.getCpuModelName());
		Log.i(LOG_TAG, "Battery capacity (mAH) : " + this.getBatteryCapacity());
		Log.i(LOG_TAG, "Screen on power(mA) : " + this.getScreenOnPower());
		Log.i(LOG_TAG, "Screen Full power : " + this.getScreenFullPower());
		Log.i(LOG_TAG, "Bluetooth on power : " + this.getBluetoothOnPower());
		Log.i(LOG_TAG, "Bluetooth at power : " + this.getBluetoothATPower());
		Log.i(LOG_TAG, "Bluetooth active power : " + this.getBluetoothActivePower());
		Log.i(LOG_TAG, "WiFi on power : " + this.getWifiOnPower());
		Log.i(LOG_TAG, "WiFi scan power : " + this.getWifiScanPower());
		Log.i(LOG_TAG, "WiFi active power : " + this.getWifiActivePower());
		Log.i(LOG_TAG, "DSP Audio power : " + this.getDspAudioPower());
		Log.i(LOG_TAG, "DSP Video power : " + this.getDspVideoPower());
		Log.i(LOG_TAG, "Radio on level 0 power : " + this.getRadioOnLevel0());
		Log.i(LOG_TAG, "Radio on level 1 power : " + this.getRadioOnLevel1());
		Log.i(LOG_TAG, "Radio on level 2 power : " + this.getRadioOnLevel2());
		Log.i(LOG_TAG, "Radio on level 3 power : " + this.getRadioOnLevel3());
		Log.i(LOG_TAG, "Radio on level 4 power : " + this.getRadioOnLevel4());
		Log.i(LOG_TAG, "Radio scanning power : " + this.getRadioScanningPower());
		Log.i(LOG_TAG, "Radio active power : " + this.getRadioActivePower());
		Log.i(LOG_TAG, "GPS on power : " + this.getGpsOnPower());
		Log.i(LOG_TAG, "CPU idle power : " + this.getCpuIdlePower());
		Log.i(LOG_TAG, "CPU awake power : " + this.getCpuAwakePower());
		Log.i(LOG_TAG, "CPU speeds : " + this.getCpuSpeeds().size());
		for (int i =0; i < this.getCpuSpeeds().size();i++ ) {
			Log.i(LOG_TAG, "CPU speed : " + this.getCpuSpeeds().get(i) + " CPU active power : " + this.getCpuActivePowers().get(i));
		}
		Log.i(LOG_TAG, "Sensor Accelerometer power : " + this.getSensorAccelerometerPower());
		Log.i(LOG_TAG, "Sensor MagneticField power : " + this.getSensorMagneticFieldPower());
		Log.i(LOG_TAG, "Sensor Orientation power : " + this.getSensorOrientationPower());
		Log.i(LOG_TAG, "Sensor Gyroscope power : " + this.getSensorGyroscopePower());
		Log.i(LOG_TAG, "Sensor Light power : " + this.getSensorLightPower());
		Log.i(LOG_TAG, "Sensor Pressure power : " + this.getSensorPressurePower());
		Log.i(LOG_TAG, "Sensor Temperature power : " + this.getSensorTemperaturePower());
		Log.i(LOG_TAG, "Sensor Proximity power : " + this.getSensorProximityPower());
		Log.i(LOG_TAG, "Sensor Gravity power : " + this.getSensorGravityPower());
		Log.i(LOG_TAG, "Sensor Linear Acceleration power : " + this.getSensorLinearAccelerationPower());
		Log.i(LOG_TAG, "Sensor Rotation Vector power : " + this.getSensorRotationVectorPower());
		Log.i(LOG_TAG, "Sensor Relative Humidity power : " + this.getSensorRelativeHumidityPower());
		Log.i(LOG_TAG, "Sensor Ambient Temperature power : " + this.getSensorAmbientTemperaturePower());
		Log.i(LOG_TAG, "Sensor Uncalibrated Magnetic Field power : " + this.getSensorUncalibratedMagneticFieldPower());
		Log.i(LOG_TAG, "Sensor Uncalibrated Rotation Vector power : " + this.getSensorUncalibratedRotationVectorPower());
		Log.i(LOG_TAG, "Sensor Uncalibrated Gyroscope power : " + this.getSensorUncalibratedGyroscopePower());
		Log.i(LOG_TAG, "Sensor Significant Motion power : " + this.getSensorSignificantMotionPower());
	}

}
