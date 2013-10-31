package com.figo.testsysinfo;

import java.util.ArrayList;

public class PacketInfo {
	private long timeStamp;
	private long timePeriod;
	private PacketMetaInfo pmi;
	private DeviceMetaInfo dmi;
	private DeviceInfo di;
	private SettingsInfo si;
	private ArrayList<AppInfo> appinfos;
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(long timePeriod) {
		this.timePeriod = timePeriod;
	}
	public PacketMetaInfo getPmi() {
		return pmi;
	}
	public void setPmi(PacketMetaInfo pmi) {
		this.pmi = pmi;
	}
	public DeviceMetaInfo getDmi() {
		return dmi;
	}
	public void setDmi(DeviceMetaInfo dmi) {
		this.dmi = dmi;
	}
	public DeviceInfo getDi() {
		return di;
	}
	public void setDi(DeviceInfo di) {
		this.di = di;
	}
	public SettingsInfo getSi() {
		return si;
	}
	public void setSi(SettingsInfo si) {
		this.si = si;
	}
	public ArrayList<AppInfo> getAppinfos() {
		return appinfos;
	}
	public void setAppinfos(ArrayList<AppInfo> appinfos) {
		this.appinfos = appinfos;
	}
}
