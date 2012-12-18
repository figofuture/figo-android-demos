package com.figo.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.figo.common.utils.LogHelper.LogLevel;

import android.text.TextUtils;
import android.util.Log;

public class EncryptUtil {

	public static final String LOG_TAG = LogHelper
			.makeLogTag(EncryptUtil.class);
	public static final String HEXES = "0123456789ABCDEF";
	public static final String HEXES_LOWER = "0123456789abcdef";

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}

		final StringBuilder hex = new StringBuilder(2 * raw.length);

		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}

		return hex.toString();
	}

	public static String getHexLowerCase(byte[] raw) {
		if (raw == null) {
			return null;
		}

		final StringBuilder hex = new StringBuilder(2 * raw.length);

		for (final byte b : raw) {
			hex.append(HEXES_LOWER.charAt((b & 0xF0) >> 4)).append(
					HEXES_LOWER.charAt((b & 0x0F)));
		}

		return hex.toString();
	}

	public static byte[] encryptHMAC(byte[] data, byte[] key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}

	public static String getSHA256InLowerCase(String source)
			throws OutOfMemoryError, NoSuchAlgorithmException {

		MessageDigest msgDigest;

		if (TextUtils.isEmpty(source)) {
			return null;
		}

		try {
			msgDigest = MessageDigest.getInstance("SHA-256");
			msgDigest.update(source.getBytes());

			return getHexLowerCase(msgDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getSHA256->Has no algorithm SHA256.");
			}
			throw e;
		} catch (OutOfMemoryError e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getSHA256->Out of memory error.");
			}
			throw e;
		}
	}

	public static String getSHA1(String source) throws OutOfMemoryError,
			NoSuchAlgorithmException {

		MessageDigest msgDigest;

		if (source == null || source.equals("")) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getSHA1->Input source is empty or null.");
			}
			return "";
		}

		try {
			msgDigest = MessageDigest.getInstance("SHA-1");
			byte[] buffer = source.getBytes();
			msgDigest.update(buffer);

			return getHex(msgDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getSHA1->Has no algorithm SHA-1.");
			}
			throw e;
		} catch (OutOfMemoryError e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getSHA1->Out of memory error.");
			}
			throw e;
		}
	}

	public static String getFileSHA1(String path) throws OutOfMemoryError,
			IOException, NoSuchAlgorithmException {
		FileInputStream in = new FileInputStream(path);
		MessageDigest msgDigest;

		try {
			msgDigest = MessageDigest.getInstance("SHA-1");
			byte[] buffer = new byte[1024 * 512];
			int len = 0;

			while ((len = in.read(buffer)) > 0) {
				msgDigest.update(buffer, 0, len);
			}

			return getHex(msgDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getFileSHA1->Has no algorithm SHA-1.");
			}
			throw e;
		} catch (OutOfMemoryError e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getFileSHA1->Out of memory error.");
			}
			throw e;
		} finally {
			in.close();
		}
	}

	public static String getCRC32(String source) {
		if (source == null || source.equals("")) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getCRC32->Input source is empty or null.");
			}
			return "";
		}
		CRC32 crc32 = new CRC32();
		byte[] data = source.getBytes();
		crc32.update(data);
//		if (LogLevel.INFO) {
//			Log.i(LOG_TAG, "getCRC32: " + crc32.getValue());
//		}
		String ret = Long.toHexString(crc32.getValue()).toUpperCase();
		return ret;
	}

	public static String getAdler32(String source) {
		if (source == null || source.equals("")) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getAdler32->Input source is empty or null.");
			}
			return "";
		}
		Adler32 adler32 = new Adler32();
		byte[] data = source.getBytes();
		adler32.update(data);
//		if (LogLevel.INFO) {
//			Log.i(LOG_TAG, "getAdler32: " + adler32.getValue());
//		}
		return Long.toHexString(adler32.getValue()).toUpperCase();
	}

	public static String getMD5(String source) throws OutOfMemoryError,
			NoSuchAlgorithmException {

		MessageDigest msgDigest;

		if (source == null || source.equals("")) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getMD5->Input source is empty or null.");
			}
			return "";
		}

		try {
			msgDigest = MessageDigest.getInstance("MD5");
			byte[] buffer = source.getBytes();
			msgDigest.update(buffer);

			return getHex(msgDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getMD5->Has no algorithm MD5.");
			}
			throw e;
		} catch (OutOfMemoryError e) {
			if (LogLevel.ERROR) {
				Log.e(LOG_TAG, "getMD5->Out of memory error.");
			}
			throw e;
		}
	}
}
