package com.example.testconnection;

import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class Encryption {

    private static final String LOG_TAG = Encryption.class.getSimpleName();

    public static String encrypt(String key, String cleartext) {
        byte[] result = null;
        try {
            byte[] rawKey = getRawKey(key.getBytes());
            result = encrypt(rawKey, cleartext.getBytes());
            if (result == null || result.length == 0) {
                Log.e(LOG_TAG, "encrypt completed, but result is null.");
                return null;
            }

        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (java.lang.Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return toHex(result);
    }

    public static String decrypt(String key, String ciphertext) {
        byte[] result = null;
        try {
            byte[] rawKey = getRawKey(key.getBytes());
            byte[] enc = toByte(ciphertext);
            result = decrypt(rawKey, enc);
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (javax.crypto.BadPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (java.lang.Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        if (result != null) {
            return new String(result);
        } else {
            return null;
        }
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) {
        byte[] encrypted = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(clear);
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (java.lang.Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws BadPaddingException {
        byte[] decrypted = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(encrypted);
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (java.lang.Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return decrypted;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null) {
            return null;
        }
        
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

}
