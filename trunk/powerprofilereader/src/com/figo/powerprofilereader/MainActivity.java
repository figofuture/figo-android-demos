package com.figo.powerprofilereader;

import org.apache.http.protocol.HTTP;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.figo.powerprofilereader.R;

public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	PowerProfile mPP;
	EditText mET;
	String mTitle;

	String[] mTypes = { PowerProfile.POWER_SCREEN_ON,
			PowerProfile.POWER_SCREEN_FULL, PowerProfile.POWER_BLUETOOTH_ON,
			PowerProfile.POWER_BLUETOOTH_AT_CMD,
			PowerProfile.POWER_BLUETOOTH_ACTIVE, PowerProfile.POWER_WIFI_ON,
			PowerProfile.POWER_WIFI_SCAN, PowerProfile.POWER_WIFI_ACTIVE,
			PowerProfile.POWER_AUDIO, PowerProfile.POWER_VIDEO,
			PowerProfile.POWER_RADIO_ON, PowerProfile.POWER_RADIO_SCANNING,
			PowerProfile.POWER_RADIO_ACTIVE, PowerProfile.POWER_GPS_ON,
			PowerProfile.POWER_CPU_IDLE, PowerProfile.POWER_CPU_AWAKE,
			PowerProfile.POWER_CPU_SPEEDS, PowerProfile.POWER_CPU_ACTIVE,
			PowerProfile.POWER_BATTERY_CAPACITY, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = Build.MANUFACTURER + "/" + Build.MODEL + "'s ";
		mTitle += getResources().getString(R.string.share_subject);

		this.setTitle(mTitle);

		mET = (EditText) findViewById(R.id.content);

		String device = "Board: " + Build.BOARD + "\n" + "Bootloader: "
				+ Build.BOOTLOADER + "\n" + "Brand: " + Build.BRAND + "\n"
				+ "CPU abi: " + Build.CPU_ABI + "\n" + "CPU abi2: "
				+ Build.CPU_ABI2 + "\n" + "Device: " + Build.DEVICE + "\n"
				+ "Display: " + Build.DISPLAY + "\n" + "Fingerprint: "
				+ Build.FINGERPRINT + "\n" + "Hardware: " + Build.HARDWARE
				+ "\n" + "Host: " + Build.HOST + "\n" + "Id: " + Build.ID
				+ "\n" + "Manufacturer: " + Build.MANUFACTURER + "\n"
				+ "Model: " + Build.MODEL + "\n" + "Product: " + Build.PRODUCT
				+ "\n" + "Radio: " + Build.RADIO + "\n" + "Tags: " + Build.TAGS
				+ "\n" + "Time: " + Build.TIME + "\n" + "Type:" + Build.TYPE
				+ "\n" + "User: " + Build.USER + "\n"
				+ "///////////////////////////////////////" + "\n";
		Log.i(LOG_TAG, device);
		mET.setText(device);

		mPP = new PowerProfile(this);

		double value = 0;
		for (int i = 0; i < mTypes.length; i++) {
			if (PowerProfile.POWER_RADIO_ON.equalsIgnoreCase(mTypes[i])) {
				final int bins = 5;
				for (int j = 0; j < bins; j++) {
					value = mPP.getAveragePower(mTypes[i], j);
					String text = mTypes[i] + " level " + j + ": " + value
							+ "\n";
					Log.i(LOG_TAG, text);
					mET.setText(mET.getText() + text);
				}
			} else if (PowerProfile.POWER_CPU_SPEEDS
					.equalsIgnoreCase(mTypes[i])) {
				final int speedSteps = mPP.getNumSpeedSteps();
				for (int j = 0; j < speedSteps; j++) {
					value = mPP.getAveragePower(mTypes[i], j);
					String text = mTypes[i] + " level " + j + ": " + value
							+ "\n";
					Log.i(LOG_TAG, text);
					mET.setText(mET.getText() + text);
				}
			} else if (PowerProfile.POWER_CPU_ACTIVE
					.equalsIgnoreCase(mTypes[i])) {
				final int speedSteps = mPP.getNumSpeedSteps();
				for (int j = 0; j < speedSteps; j++) {
					value = mPP.getAveragePower(mTypes[i], j);
					String text = mTypes[i] + " level " + j + ": " + value
							+ "\n";
					Log.i(LOG_TAG, text);
					mET.setText(mET.getText() + text);
				}
			} else {
				value = mPP.getAveragePower(mTypes[i]);
				String text = mTypes[i] + ": " + value + "\n";
				Log.i(LOG_TAG, text);
				mET.setText(mET.getText() + text);
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_share:
			share();
			return true;
		}
		return false;
	}

	private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(HTTP.PLAIN_TEXT_TYPE);
		String[] email = { "", };
		intent.putExtra(Intent.EXTRA_EMAIL, email);
		intent.putExtra(Intent.EXTRA_SUBJECT, mTitle);
		intent.putExtra(Intent.EXTRA_TEXT, mET.getText());
		startActivity(Intent.createChooser(intent, mTitle));
	}

}
