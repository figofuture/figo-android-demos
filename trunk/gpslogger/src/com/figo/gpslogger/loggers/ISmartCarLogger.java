package com.figo.gpslogger.loggers;

import java.util.HashMap;
import java.util.Map;

import com.figo.gpslogger.common.AppSettings;
import com.figo.gpslogger.common.ISmartCarClient;

import android.location.Location;

public class ISmartCarLogger implements IFileLogger {

	protected final String name = "ISmartCar";

	@Override
	public void Write(Location loc) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("location_latitude", String.valueOf(loc.getLatitude()));
		params.put("location_longitude", String.valueOf(loc.getLongitude()));
		params.put("guid", AppSettings.getGUID());
		new ISmartCarClient().makeAsyncRequest(AppSettings.getServerAddress(), params,
				AppSettings.getProxyAddress(), AppSettings.getProxyPort());
	}

	@Override
	public void Annotate(String description, Location loc) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return AppSettings.getServerAddress();
	}

}
