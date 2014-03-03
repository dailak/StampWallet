package com.damgeek.stampwallet;

import java.util.ArrayList;
import java.util.List;

public class StampCard {
	private String mShopName;
	private String mShopLogoPath;
	private List<Stamp> stamps;
	
	public StampCard(String shopName, String shopLogoPath) {
		mShopName = shopName;
		mShopLogoPath = shopLogoPath;
		stamps = new ArrayList<Stamp>();
	}
	
	public StampCard(String shopName, String shopLogoPath, int numStamp) {
		this(shopName, shopLogoPath);
		addStamp(numStamp);
	}
	
	public void addStamp(int numStamp) {
		for (int i=0; i<numStamp; i++) {
			stamps.add(new Stamp());
		}
	}
	
	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return mShopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.mShopName = shopName;
	}
	/**
	 * @return the shopLogoUrl
	 */
	public String getShopLogoPath() {
		return mShopLogoPath;
	}
	/**
	 * @param shopLogoUrl the shopLogoUrl to set
	 */
	public void setShopLogoPath(String shopLogoPath) {
		this.mShopLogoPath = shopLogoPath;
	}
	/**
	 * @return the stamps
	 */
	public List<Stamp> getStamps() {
		return stamps;
	}
	/**
	 * @param stamps the stamps to set
	 */
	public void setStamps(List<Stamp> stamps) {
		this.stamps = stamps;
	}
}
