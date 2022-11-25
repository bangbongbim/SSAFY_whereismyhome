package com.mycom.myapp.info.dto;

public class MarketDto {
	String guName;
	String mName;
	String mAddr;
	String  lat;
	String  lng;
	public String getGuName() {
		return guName;
	}
	public void setGuName(String guName) {
		this.guName = guName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmAddr() {
		return mAddr;
	}
	public void setmAddr(String mAddr) {
		this.mAddr = mAddr;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "MarketDto [guName=" + guName + ", mName=" + mName + ", mAddr=" + mAddr + ", lat=" + lat + ", lng=" + lng
				+ "]";
	}
	
}
