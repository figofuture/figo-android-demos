package com.figo.gpslogger;

import android.location.*;
import android.os.Bundle;
import com.figo.gpslogger.R;
import com.figo.gpslogger.common.Utilities;

import java.util.Iterator;

class GeneralLocationListener implements LocationListener, GpsStatus.Listener {

	private static GpsLoggingService mainService;

	GeneralLocationListener(GpsLoggingService service) {
		Utilities.LogDebug("GeneralLocationListener constructor");
		mainService = service;
	}

	/**
	 * Event raised when a new fix is received.
	 */
	public void onLocationChanged(Location loc) {

		try {
			if (loc != null) {
				Utilities
						.LogVerbose("GeneralLocationListener.onLocationChanged");
				mainService.OnLocationChanged(loc);
			}

		} catch (Exception ex) {
			Utilities.LogError("GeneralLocationListener.onLocationChanged", ex);
			mainService.SetStatus(ex.getMessage());
		}

	}

	public void onProviderDisabled(String provider) {
		Utilities.LogInfo("Provider disabled");
		Utilities.LogDebug(provider);
		mainService.RestartGpsManagers();
	}

	public void onProviderEnabled(String provider) {

		Utilities.LogInfo("Provider enabled");
		Utilities.LogDebug(provider);
		mainService.RestartGpsManagers();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		if (status == LocationProvider.OUT_OF_SERVICE) {
			Utilities.LogDebug(provider + " is out of service");
			mainService.StopManagerAndResetAlarm();
		}

		if (status == LocationProvider.AVAILABLE) {
			Utilities.LogDebug(provider + " is available");
		}

		if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
			Utilities.LogDebug(provider + " is temporarily unavailable");
			mainService.StopManagerAndResetAlarm();
		}
	}

	public void onGpsStatusChanged(int event) {

		switch (event) {
		case GpsStatus.GPS_EVENT_FIRST_FIX:
			Utilities.LogDebug("GPS Event First Fix");
			mainService.SetStatus(R.string.fix_obtained);
			break;

		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:

			Utilities.LogDebug("GPS Satellite status obtained");
			GpsStatus status = mainService.gpsLocationManager
					.getGpsStatus(null);

			int maxSatellites = status.getMaxSatellites();

			Iterator<GpsSatellite> it = status.getSatellites().iterator();
			int count = 0;

			while (it.hasNext() && count <= maxSatellites) {
				it.next();
				count++;
			}

			mainService.SetSatelliteInfo(count);
			break;

		case GpsStatus.GPS_EVENT_STARTED:
			Utilities.LogInfo("GPS started, waiting for fix");
			mainService.SetStatus(R.string.started_waiting);
			break;

		case GpsStatus.GPS_EVENT_STOPPED:
			Utilities.LogInfo("GPS Stopped");
			mainService.SetStatus(R.string.gps_stopped);
			break;

		}
	}

}
