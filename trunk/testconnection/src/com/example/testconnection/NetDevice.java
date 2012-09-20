package com.example.testconnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public abstract class NetDevice {

	public abstract String[] getGPRSo3G();

	public abstract String[] getWiFi();

}

/**
 * Automatically discover the network interfaces. No real magic here, just try
 * different possible solutions.
 */
class AndroidDevice extends NetDevice {

	private static final String[] CELL_INTERFACES = { //
	"rmnet0", "pdp0", "ppp0" //
	};

	private static final String[] WIFI_INTERFACES = { //
	"eth0", "tiwlan0", "wlan0", "athwlan0", "eth1" //
	};

	private static final String INTERFACE_PATTERN = "^wifi\\.interface=(\\S+)$";

	private static final String BUILD_PROP = "/system/build.prop";

	@Override
	public String[] getGPRSo3G() {
		return CELL_INTERFACES;
	}

	@Override
	public String[] getWiFi() {
		String interfaceWifi = checkWiFiIf();

		if (interfaceWifi == null) {
			return WIFI_INTERFACES;
		} else
			return new String[] { interfaceWifi };
	}

	public Long getGPRSo3GTx() {
		return tryAll("/sys/class/net/", getGPRSo3G(), "/statistics/tx_bytes");
	}

	public Long getGPRSo3GRx() {
		return tryAll("/sys/class/net/", getGPRSo3G(), "/statistics/rx_bytes");
	}

	public Long getWifiTx() {
		return tryAll("/sys/class/net/", getWiFi(), "/statistics/tx_bytes");
	}

	public Long getWifiRx() {
		return tryAll("/sys/class/net/", getWiFi(), "/statistics/rx_bytes");
	}

	private Long tryAll(String prefixValue, String[] ifList, String sufixValue) {
		for (String eachFace : ifList) {
			Long num = readNumber(prefixValue + eachFace + sufixValue);
			if (num >= 0) {
				return num;
			}
		}
		return Long.valueOf("0");
	}

	private Long readNumber(String eachFace) {
		byte[] buf = new byte[80];
		int len = 0;
		File file = new File(eachFace);
		InputStream in = null;
		try {
			if (!file.exists()) {
				return Long.valueOf("-1");
			}
			in = new BufferedInputStream(new FileInputStream(file));
			len = in.read(buf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Long.valueOf("-1");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		if (len != 0) {
			return Long.valueOf(new String(buf).trim());
		}
		return Long.valueOf("-1");
		
	}

	public String checkWiFiIf() {
		String mWifiFind = null;

		Pattern pattern = Pattern.compile(INTERFACE_PATTERN);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(BUILD_PROP));
			String line;
			while ((line = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					mWifiFind = matcher.group(1);
					break;
				}
			}
		} catch (IOException e) {
			
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// Silently ignore.
				}
			}
		}
		return mWifiFind;
	}

	final static String prefix_uid_stat = "/proc/uid_stat/";
	final static String suffix_rx = "/tcp_rcv";
	final static String suffix_tx = "/tcp_snd";

	public long getUidRxBytes(int uid) {
		return tryAll(prefix_uid_stat, new String[] { String.valueOf(uid) },
				suffix_rx);
	}

	public long getUidTxBytes(int uid) {
		return tryAll(prefix_uid_stat, new String[] { String.valueOf(uid) },
				suffix_tx);
	}
	
	public boolean isSupportUidStat() {
		return new File(prefix_uid_stat).exists();
	}

}