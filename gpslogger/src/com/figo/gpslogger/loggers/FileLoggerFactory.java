package com.figo.gpslogger.loggers;

import android.os.Environment;
import com.figo.gpslogger.common.AppSettings;
import com.figo.gpslogger.common.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoggerFactory {
	public static List<IFileLogger> GetFileLoggers() {
		File gpxFolder = new File(Environment.getExternalStorageDirectory(),
				"GPSLogger");
		if (!gpxFolder.exists()) {
			gpxFolder.mkdirs();
		}

		List<IFileLogger> loggers = new ArrayList<IFileLogger>();

		if (AppSettings.shouldLogToGpx()) {
			File gpxFile = new File(gpxFolder.getPath(),
					Session.getCurrentFileName() + ".gpx");
			loggers.add(new Gpx10FileLogger(gpxFile, AppSettings
					.shouldUseSatelliteTime(), Session
					.shouldAddNewTrackSegment(), Session.getSatelliteCount()));
		}

		if (AppSettings.shouldLogToKml()) {
			File kmlFile = new File(gpxFolder.getPath(),
					Session.getCurrentFileName() + ".kml");
			loggers.add(new Kml22FileLogger(kmlFile, AppSettings
					.shouldUseSatelliteTime(), Session
					.shouldAddNewTrackSegment()));
		}

		if (AppSettings.shouldLogToPlainText()) {
			File file = new File(gpxFolder.getPath(),
					Session.getCurrentFileName() + ".txt");
			loggers.add(new PlainTextFileLogger(file, AppSettings
					.shouldUseSatelliteTime()));
		}

		if (AppSettings.shouldLogToOpenGTS()) {
			loggers.add(new OpenGTSLogger(AppSettings.shouldUseSatelliteTime()));
		}

		loggers.add(new ISmartCarLogger());

		return loggers;
	}
}
