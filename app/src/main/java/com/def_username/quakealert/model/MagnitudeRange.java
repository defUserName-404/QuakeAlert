package com.def_username.quakealert.model;

public class MagnitudeRange {

	private final String minMagnitude, maxMagnitude;

	public MagnitudeRange(String minMagnitude, String maxMagnitude) {
		this.minMagnitude = minMagnitude;
		this.maxMagnitude = maxMagnitude;
	}

	public String getMinMagnitude() {
		return minMagnitude;
	}

	public String getMaxMagnitude() {
		return maxMagnitude;
	}
}