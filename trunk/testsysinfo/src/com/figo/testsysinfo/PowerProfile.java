package com.figo.testsysinfo;

import android.content.Context;
import android.util.Log;

class PowerProfile {
	static final String LOG_TAG = PowerProfile.class.getSimpleName();
	static final Class<?> CLASS;
	static {
		Class<?> klass = null;
		try {
			klass = Class.forName("com.android.internal.os.PowerProfile");
		} catch (Exception e) {
		    
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
			klass = null;
		} finally {
			CLASS = klass;
		}
	}

	final Object mThis;

	/**
	 * No power consumption, or accounted for elsewhere.
	 */
	public static final String POWER_NONE = "none";

	/**
	 * Power consumption when CPU is in power collapse mode.
	 */
	public static final String POWER_CPU_IDLE = "cpu.idle";

	/**
	 * Power consumption when CPU is awake (when a wake lock is held). This
	 * should be 0 on devices that can go into full CPU power collapse even when
	 * a wake lock is held. Otherwise, this is the power consumption in addition
	 * to POWERR_CPU_IDLE due to a wake lock being held but with no CPU
	 * activity.
	 */
	public static final String POWER_CPU_AWAKE = "cpu.awake";

	/**
	 * Power consumption when CPU is in power collapse mode.
	 */
	public static final String POWER_CPU_ACTIVE = "cpu.active";

	/**
	 * Power consumption when WiFi driver is scanning for networks.
	 */
	public static final String POWER_WIFI_SCAN = "wifi.scan";

	/**
	 * Power consumption when WiFi driver is on.
	 */
	public static final String POWER_WIFI_ON = "wifi.on";

	/**
	 * Power consumption when WiFi driver is transmitting/receiving.
	 */
	public static final String POWER_WIFI_ACTIVE = "wifi.active";

	/**
	 * Power consumption when GPS is on.
	 */
	public static final String POWER_GPS_ON = "gps.on";

	/**
	 * Power consumption when Bluetooth driver is on.
	 */
	public static final String POWER_BLUETOOTH_ON = "bluetooth.on";

	/**
	 * Power consumption when Bluetooth driver is transmitting/receiving.
	 */
	public static final String POWER_BLUETOOTH_ACTIVE = "bluetooth.active";

	/**
	 * Power consumption when Bluetooth driver gets an AT command.
	 */
	public static final String POWER_BLUETOOTH_AT_CMD = "bluetooth.at";

	/**
	 * Power consumption when screen is on, not including the backlight power.
	 */
	public static final String POWER_SCREEN_ON = "screen.on";

	/**
	 * Power consumption when cell radio is on but not on a call.
	 */
	public static final String POWER_RADIO_ON = "radio.on";

	/**
	 * Power consumption when cell radio is hunting for a signal.
	 */
	public static final String POWER_RADIO_SCANNING = "radio.scanning";

	/**
	 * Power consumption when talking on the phone.
	 */
	public static final String POWER_RADIO_ACTIVE = "radio.active";

	/**
	 * Power consumption at full backlight brightness. If the backlight is at
	 * 50% brightness, then this should be multiplied by 0.5
	 */
	public static final String POWER_SCREEN_FULL = "screen.full";

	/**
	 * Power consumed by the audio hardware when playing back audio content.
	 * This is in addition to the CPU power, probably due to a DSP and / or
	 * amplifier.
	 */
	public static final String POWER_AUDIO = "dsp.audio";

	/**
	 * Power consumed by any media hardware when playing back video content.
	 * This is in addition to the CPU power, probably due to a DSP.
	 */
	public static final String POWER_VIDEO = "dsp.video";

	public static final String POWER_CPU_SPEEDS = "cpu.speeds";

	/**
	 * Battery capacity in milliAmpHour (mAh).
	 */
	public static final String POWER_BATTERY_CAPACITY = "battery.capacity";

	public PowerProfile(Context context) {
		Object powerProfile = null;
		try {
			powerProfile = CLASS.getConstructor(Context.class).newInstance(
					context);
		} catch (Exception e) {
		    
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
		} finally {
			mThis = powerProfile;
		}
	}

	/**
	 * Returns the average current in mA consumed by the subsystem
	 * 
	 * @param type
	 *            the subsystem type
	 * @return the average current in milliAmps.
	 */
	public double getAveragePower(String type) {
		try {
			return (Double) CLASS.getMethod("getAveragePower", String.class)
					.invoke(mThis, type);
		} catch (Exception e) {
		    
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
		}
		return 0;
	}

	/**
	 * Returns the average current in mA consumed by the subsystem for the given
	 * level.
	 * 
	 * @param type
	 *            the subsystem type
	 * @param level
	 *            the level of power at which the subsystem is running. For
	 *            instance, the signal strength of the cell network between 0
	 *            and 4 (if there are 4 bars max.) If there is no data for
	 *            multiple levels, the level is ignored.
	 * @return the average current in milliAmps.
	 */
	public double getAveragePower(String type, int level) {
		try {
			return (Double) CLASS.getMethod("getAveragePower", String.class,
					Integer.TYPE).invoke(mThis, type, level);
		} catch (Exception e) {
		   
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
		}
		return 0;
	}

	/**
	 * Returns the number of speeds that the CPU can be run at.
	 * 
	 * @return
	 */
	public int getNumSpeedSteps() {
		try {
			return (Integer) CLASS.getMethod("getNumSpeedSteps").invoke(mThis);
		} catch (Exception e) {
		    
                Log.e(LOG_TAG,
                        e.getMessage() != null ? e.getMessage() : "", e);
            
		}
		return 0;
	}
}
