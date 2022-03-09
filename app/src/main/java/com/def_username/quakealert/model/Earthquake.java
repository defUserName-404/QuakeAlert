package com.def_username.quakealert.model;

public class Earthquake {
	private final double mMagnitude;
	private final String mLocation;
	private final long mTimeInMilliseconds;
	private final double[] coordinates;


	public Earthquake(double magnitude, String location, long timeInMilliseconds, double[] coordinates) {
		mMagnitude = magnitude;
		mLocation = location;
		mTimeInMilliseconds = timeInMilliseconds;
		this.coordinates = coordinates;
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

	public double[] getCoordinates() {
		return coordinates;
	}
}