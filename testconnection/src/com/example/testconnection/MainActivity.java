package com.example.testconnection;

import java.util.Timer;
import java.util.TimerTask;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	static TextView mTV = null;
	static ConnectivityManager connMgr = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTV = (TextView) findViewById(R.id.content);

		connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		Timer timer = new Timer();
		timer.schedule(new MyTask(), 0, 500);
		
		TextView tv = (TextView) findViewById(R.id.current);
		// check if current data connection activity
		if ( isDataActivity() ) {
			tv.setText("Current data connection is active!");
		} else {
			tv.setText("Current data connection has no data request and/or transmit!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	static class MyTask extends TimerTask {

		@Override
		public void run() {

			mHandler.obtainMessage(5000).sendToTarget();
		}

	}

	static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 5000:
				String content = null;
				NetworkInfo nif = connMgr.getActiveNetworkInfo();
				if (null != nif) {
					content = "ExtraInfo: " + nif.getExtraInfo() + "\n";
					content += "Reason: " + nif.getReason() + "\n";
					content += "Type: " + nif.getTypeName() + "\n";
					content += "SubType: " + nif.getSubtypeName() + "\n";
					content += nif.toString() + "\n\n";
				}

				content += "(API) Mobile Received Bytes: "
						+ TrafficStats.getMobileRxBytes() + "\n";
				content += "(API) Mobile Transmitted Bytes: "
						+ TrafficStats.getMobileTxBytes() + "\n";

				content += "(API) Total Received Bytes: "
						+ TrafficStats.getTotalRxBytes() + "\n";
				content += "(API) Total Transmitted Bytes: "
						+ TrafficStats.getTotalTxBytes() + "\n\n";

				AndroidDevice ad = new AndroidDevice();

				content += "(Linux) Mobile Received Bytes: "
						+ ad.getGPRSo3GRx() + "\n";
				content += "(Linux) Mobile Transmitted Bytes: "
						+ ad.getGPRSo3GTx() + "\n";

				content += "(Linux) WiFi Received Bytes: " + ad.getWifiRx()
						+ "\n";
				content += "(Linux) WiFi Transmitted Bytes: " + ad.getWifiTx()
						+ "\n";

				mTV.setText(content);
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * Detect current data connection activity
	 * */
	public boolean isDataActivity() {
		AndroidDevice ad = new AndroidDevice();
		long oldMobileRx = ad.getGPRSo3GRx();
		long oldMobileTx = ad.getGPRSo3GTx();
		long oldWiFiRx = ad.getWifiRx();
		long oldWiFiTx = ad.getWifiTx();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long mobileRx = ad.getGPRSo3GRx();
		long mobileTx = ad.getGPRSo3GTx();
		long wifiRx = ad.getWifiRx();
		long wifiTx = ad.getWifiTx();

		if (((mobileRx - oldMobileRx) > 0) || ((mobileTx - oldMobileTx) > 0)
				|| ((wifiRx - oldWiFiRx) > 0) || ((wifiTx - oldWiFiTx) > 0)) {
			return true;
		} else {
			return false;
		}
	}
}
