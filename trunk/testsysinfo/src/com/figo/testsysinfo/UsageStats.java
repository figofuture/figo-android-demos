package com.figo.testsysinfo;

import java.util.List;

import com.android.internal.os.PkgUsageStats;

import android.content.ComponentName;
import android.os.IBinder;
import android.util.Log;

public class UsageStats {
	static final String CLASS_NAME = "com.android.internal.app.IUsageStats";
	static final String LOG_TAG = UsageStats.class.getSimpleName();
	
	 static final Class<?> CLASS;
	    static {
	        Class<?> klass = null;
	        try {
	            klass = Class.forName(CLASS_NAME);
	        } catch (Exception e) {
	                Log.e(LOG_TAG,
	                        e.getMessage() != null ? e.getMessage() : "", e);
	            klass = null;
	        } finally {
	            CLASS = klass;
	        }
	    }
	    
	    final Object mThis;
	    public List<PkgUsageStats> mUsageStats;
	    
	    UsageStats() {
	        Object usageStatsService = null;
	        PkgUsageStats[]  stats = null;
	        try {
	        	// mUsageStatsService = IUsageStats.Stub.asInterface(ServiceManager.getService("usagestats"));
	        	usageStatsService = Class
	                    .forName("com.android.internal.app.IUsageStats$Stub")
	                    .getDeclaredMethod("asInterface", IBinder.class)
	                    .invoke(null,
	                            Class.forName("android.os.ServiceManager")
	                                    .getMethod("getService", String.class)
	                                    .invoke(null, "usagestats"));

	            // PkgUsageStats[] stats = mUsageStatsService.getAllPkgUsageStats();
	            stats = (PkgUsageStats[]) Class
	                    .forName("com.android.internal.app.IUsageStats")
	                    .getMethod("getAllPkgUsageStats").invoke(usageStatsService);

	                Log.d(LOG_TAG, "All package usage data length: " + stats.length);
	            if (stats.length == 0)
	                return;
	        } catch (Exception e) {
	            
	                Log.e(LOG_TAG,
	                        e.getMessage() != null ? e.getMessage() : "", e);
	            
	        } finally {
	            mThis = usageStatsService;
	            for (PkgUsageStats ps : stats) {
	                mUsageStats.add(ps);
	            }
	        }
	    }
	    
	    PkgUsageStats getPkgUsageStats(ComponentName componentName) {
	    	try {
	            return (PkgUsageStats) CLASS.getMethod("getPkgUsageStats",
	                   ComponentName.class).invoke(mThis,
	                    		componentName);
	        } catch (Exception e) {
	                Log.e(LOG_TAG,
	                        e.getMessage() != null ? e.getMessage() : "", e);
	            
	        }
	        return null;
	    }
	    
	    PkgUsageStats[] getAllPkgUsageStats( ) {
	    	try {
	            return (PkgUsageStats[]) CLASS.getMethod("getAllPkgUsageStats").invoke(mThis);
	        } catch (Exception e) {
	                Log.e(LOG_TAG,
	                        e.getMessage() != null ? e.getMessage() : "", e);
	            
	        }
	        return null;
	    }
}
