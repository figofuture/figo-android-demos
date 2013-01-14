package com.figo.notificationmanager;

import android.content.Intent;

public class LNotification {

	private String titleId;
	private String msgId;
	private int iconId;
	private Intent intent;
	private long timestamp;
	private int type;
	
	public final static int ACTIONS_TYPE_ACTIVITY = 0;
	public final static int ACTIONS_TYPE_SERVICE = 1;
	public final static int ACTIONS_TYPE_BROADCAST = 2;
	
	public LNotification ( String titleId, String msgId, int iconId, Intent intent, long timestamp, int type ) {
		this.titleId = titleId;
		this.msgId = msgId;
		this.iconId = iconId;
		this.intent = intent;
		this.timestamp = timestamp;
		this.type = type;
	}
	
	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
