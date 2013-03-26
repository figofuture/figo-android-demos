/*
 * ***********************************************************************************
 * TREND MICRO HIGHLY CONFIDENTIAL INFORMATION:
 * THIS SOFTWARE CONTAINS CONFIDENTIAL INFORMATION AND TRADE SECRETS OF TREND MICRO 
 * INCORPORATED AND MAY BE PROTECTED BY ONE OR MORE PATENTS. USE, DISCLOSURE, OR 
 * REPRODUCTION OF ANY PORTION OF THIS SOFTWARE IS PROHIBITED WITHOUT THE PRIOR 
 * EXPRESS WRITTEN PERMISSION OF TREND MICRO INCORPORATED. 
 *
 * Copyright (c) 2011 Trend Micro Incorporated. All rights reserved as an unpublished work. 
 * ***********************************************************************************
 */
package com.example.testconnection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.provider.Settings.Secure;
import android.util.Log;

public class Security {

    private static final String LOG_TAG =Security.class.getSimpleName();

    private static final String KEY1 = "TREND#!";
    private static final String KEY2 = "MICRO$1";
    
    private static String sAndroidId = null; 

    private static String sKey = null;

    private static String getKey() {
        if (sKey == null) {
            sKey = generateKey();
        }

        return sKey;
    }
    
    private static String getAndroidId(Context context) {
        if (sAndroidId == null) {
            sAndroidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
        
        return sAndroidId;
    }

    private static String generateKey() {
        byte[] key1Bytes = KEY1.getBytes();
        byte[] key2Bytes = KEY2.getBytes();

        int length = key1Bytes.length < key2Bytes.length ? key1Bytes.length
                : key2Bytes.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) (key1Bytes[i] ^ key2Bytes[i]);
        }

        return BASE64Encoder.encode(result) + "%1%.";
    }

    public static String encrypt(String source) {
        return Encryption.encrypt(getKey(), source);
    }

    public static String descrypt(String encryptedContent) {
        return Encryption.decrypt(getKey(), encryptedContent);
    }
    
    public static String encrytWithAndroidId(Context context, String source) {
        return Encryption.encrypt(getKey() + getAndroidId(context), source);
    }
    
    public static String descrytWithAndroidId(Context context, String encrytedContent) {
        return Encryption.decrypt(getKey() + getAndroidId(context), encrytedContent);
    }

    public static String getEncriptedContentOfFile(Context context,
            String filePath) {
        AssetManager assetManager = context.getResources().getAssets();
        InputStream is = null;

        try {
            is = assetManager.open(filePath);
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                byte b = (byte) result;
                buf.write(b);
                result = bis.read();
            }

            String encriptString = buf.toString();
            String rawString = Security.encrypt(encriptString);

            return rawString;

        } catch (IOException e) {
            

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        
        return null;
    }
}
