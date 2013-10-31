package com.figo.testsysinfo;

public class PacketMetaInfo {
	private String UUID;
	private String versionName;
	private String clientName;
	private String clientVersionName;
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientVersionName() {
		return clientVersionName;
	}
	public void setClientVersionName(String clientVersionName) {
		this.clientVersionName = clientVersionName;
	}
}
