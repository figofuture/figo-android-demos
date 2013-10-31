package com.figo.testsysinfo;

import android.util.Log;

public class SettingsInfo {
	
	private final static String LOG_TAG = SettingsInfo.class.getSimpleName();
	
	private boolean autoRotateScreen;
	private float screenAnimation;
	private long screenBrightness;
	private boolean automaticBrightness;
	private long screenTimeout;
	public boolean isAutoRotateScreen() {
		return autoRotateScreen;
	}
	public void setAutoRotateScreen(boolean autoRotateScreen) {
		this.autoRotateScreen = autoRotateScreen;
	}
	public float getScreenAnimation() {
		return screenAnimation;
	}
	public void setScreenAnimation(float screenAnimation) {
		this.screenAnimation = screenAnimation;
	}
	public long getScreenBrightness() {
		return screenBrightness;
	}
	public void setScreenBrightness(long screenBrightness) {
		this.screenBrightness = screenBrightness;
	}
	public boolean isAutomaticBrightness() {
		return automaticBrightness;
	}
	public void setAutomaticBrightness(boolean automaticBrightness) {
		this.automaticBrightness = automaticBrightness;
	}
	public long getScreenTimeout() {
		return screenTimeout;
	}
	public void setScreenTimeout(long screenTimeout) {
		this.screenTimeout = screenTimeout;
	}
	
	public void dump () {
		Log.i(LOG_TAG, "Autorotate screen : " + this.isAutoRotateScreen());
		Log.i(LOG_TAG, "Screen animation : " + this.getScreenAnimation());
		Log.i(LOG_TAG, "Screen brightness : " + this.getScreenBrightness());
		Log.i(LOG_TAG, "Automatic brightness : " + this.isAutomaticBrightness());
		Log.i(LOG_TAG, "Screen timeout : " + this.getScreenTimeout());
	}

}
