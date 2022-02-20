package com.def_username.quakealert.model;

public class Earthquake {
	private final double mMagnitude;
	private final String mLocation;
	private final long mTimeInMilliseconds;

	public Earthquake(double magnitude, String location, long timeInMilliseconds) {
		mMagnitude = magnitude;
		mLocation = location;
		mTimeInMilliseconds = timeInMilliseconds;
	}

	public double getMagnitude() {
		return mMagnitude;
	}

	public String getLocation() {
		return mLocation;
	}

	public long getTimeInMilliseconds() {
		return mTimeInMilliseconds;
	}
}