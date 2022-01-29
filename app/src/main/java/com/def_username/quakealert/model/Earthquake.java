package com.def_username.quakealert.model;

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 */
public class Earthquake {
	private final double mMagnitude;
	private final String mLocation;
	private final long mTimeInMilliseconds;
	private final String mUrl;

	/**
	 * Constructs a new {@link Earthquake} object.
	 *
	 * @param magnitude is the magnitude (size) of the earthquake
	 * @param location is the location where the earthquake happened
	 * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
	 *                           earthquake happened
	 * @param url is the website URL to find more details about the earthquake
	 */
	public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
		mMagnitude = magnitude;
		mLocation = location;
		mTimeInMilliseconds = timeInMilliseconds;
		mUrl = url;
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

	public String getUrl() {
		return mUrl;
	}
}