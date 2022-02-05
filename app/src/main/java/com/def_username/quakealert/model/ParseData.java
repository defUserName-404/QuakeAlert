package com.def_username.quakealert.model;

import android.util.Log;

import com.def_username.quakealert.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ParseData {
	private static String SAMPLE_RESPONSE = "";

	public static void setSampleJsonResponse(String response) {
		SAMPLE_RESPONSE = response;
	}

	// To prevent from creating an object of this class
	private ParseData() {
	}

	public static ArrayList<Earthquake> extractEarthquakes() {
		ArrayList<Earthquake> earthquakes = new ArrayList<>();

		try {
			JSONObject baseObject = new JSONObject(SAMPLE_RESPONSE);
			JSONArray earthquakeArray = baseObject.getJSONArray("features");

			for (int i = 0; i < earthquakeArray.length(); i++) {
				JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
				JSONObject properties = currentEarthquake.getJSONObject("properties");
				double magnitude = properties.getDouble("mag");
				String location = properties.getString("place");
				long time = properties.getLong("time");
				String url = properties.getString("url");

				Earthquake earthquake = new Earthquake(magnitude, location, time, url);
				earthquakes.add(earthquake);
			}
		} catch (JSONException e) {
			Log.e("Parsing Error", "Problem parsing the earthquake JSON results", e);
		}

		return earthquakes;
	}

	public static String formatDate(Date dateObject) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd yyyy", Locale.getDefault());
		return dateFormat.format(dateObject);
	}

	public static String formatTime(Date dateObject) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
		return timeFormat.format(dateObject);
	}

	public static String formatMagnitude(double magnitude) {
		return new DecimalFormat("0.0").format(magnitude);
	}

	public static String[] formatLocation(String originalLocation) {
		final String LOCATION_SEPARATOR = " of ";
		String primaryLocation, locationOffset;

		if (originalLocation.contains(LOCATION_SEPARATOR)) {
			String[] parts = originalLocation.split(LOCATION_SEPARATOR);
			locationOffset = parts[0] + LOCATION_SEPARATOR;
			primaryLocation = parts[1];
		} else {
			locationOffset = "Near";
			primaryLocation = originalLocation;
		}

		return new String[]{primaryLocation, locationOffset};
	}

	public static int setMagnitudeBGColor(double magnitude) {
		int magnitudeColorResourceId;
		int magnitudeFloor = (int) Math.floor(magnitude);
		switch (magnitudeFloor) {
			case 0:
			case 1:
				magnitudeColorResourceId = R.color.magnitude1;
				break;
			case 2:
				magnitudeColorResourceId = R.color.magnitude2;
				break;
			case 3:
				magnitudeColorResourceId = R.color.magnitude3;
				break;
			case 4:
				magnitudeColorResourceId = R.color.magnitude4;
				break;
			case 5:
				magnitudeColorResourceId = R.color.magnitude5;
				break;
			case 6:
				magnitudeColorResourceId = R.color.magnitude6;
				break;
			case 7:
				magnitudeColorResourceId = R.color.magnitude7;
				break;
			case 8:
				magnitudeColorResourceId = R.color.magnitude8;
				break;
			case 9:
				magnitudeColorResourceId = R.color.magnitude9;
				break;
			default:
				magnitudeColorResourceId = R.color.magnitude10plus;
				break;
		}

		return magnitudeColorResourceId;
	}
}