package com.figo.notificationmanager;

import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class LNotificationManager {

	private static LNotificationManager mInstance = null;
	private Context mContext = null;
	private NotificationManager mNM = null;
	private Map<String, LNotification> mNotifications;

	private LNotificationManager(Context ctx) {
		mContext = ctx;
		mNM = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifications = new HashMap<String, LNotification>();
		mNotifications.clear();
	}

	public static LNotificationManager getInstance(Context ctx) {
		if (null == mInstance) {
			mInstance = new LNotificationManager(ctx);
		}
		return mInstance;
	}

	public void add(String id, LNotification notification) {
		if (id != null && notification != null) {
			mNotifications.put(id, notification);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					mContext).setSmallIcon(notification.getIconId())
					.setContentTitle(notification.getTitleId())
					.setContentText(mContext.getString(R.string.notification_tips)).setAutoCancel(true);
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(mContext,
					NotificationInboxActivity.class);
			// The stack builder object will contain an artificial back stack
			// for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out
			// of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(NotificationInboxActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
					0, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);
			mNM.notify(0, mBuilder.build());
		}

	}

	void notify(int id, Notification notification) {
		mNM.notify(id, notification);
	}

	void cancel(int id) {
		mNM.cancel(id);
	}

	void cancelAll() {
		mNM.cancelAll();
	}
	
	Map<String, LNotification> getNotifications() {
		return this.mNotifications;
	}
	
}
