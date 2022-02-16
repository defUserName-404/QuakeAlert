package com.def_username.quakealert.viewmodel;

import android.graphics.Color;
import android.util.Log;

import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ParseData {
	private static JSONObject FINAL_RESPONSE;

	public static void setSampleJsonResponse(JSONObject response) {
		FINAL_RESPONSE = response;
	}

	// To prevent from creating an object of this class
	private ParseData() {
	}

	public static int getRequestStatusCode() {
		int requestStatusCode;

		try {
			JSONObject jsonMetadata = FINAL_RESPONSE.getJSONObject("metadata");
			requestStatusCode = jsonMetadata.getInt("status");
		} catch (JSONException e) {
			requestStatusCode = 404;
		}

		return requestStatusCode;
	}

	public static int getCount() {
		int count;

		try {
			JSONObject jsonMetadata = FINAL_RESPONSE.getJSONObject("metadata");
			count = jsonMetadata.getInt("count");
		} catch (JSONException e) {
			count = 0;
		}

		return count;
	}

	public static ArrayList<Earthquake> extractEarthquakes() {
		ArrayList<Earthquake> earthquakes = new ArrayList<>();

		try {
			JSONArray earthquakeArray = FINAL_RESPONSE.getJSONArray("features");

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

	public static String formatDateForResponse(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		return dateFormat.format(date);
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

		primaryLocation = capitalizeFirstCharacter(primaryLocation);

		return new String[]{primaryLocation, locationOffset};
	}

	private static String capitalizeFirstCharacter(String string) {
		if (string == null || string.isEmpty())
			return string;

		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public static int getBGColor(double magnitude) {
		int magnitudeColorResourceId;
		int magnitudeFloor = (int) Math.floor(magnitude);
		switch (magnitudeFloor) {
			case 0:
			case 1:
				magnitudeColorResourceId = Color.parseColor("#4A7BA7");
				break;
			case 2:
				magnitudeColorResourceId = Color.parseColor("#04B4B3");
				break;
			case 3:
				magnitudeColorResourceId = Color.parseColor("#10CAC9");
				break;
			case 4:
				magnitudeColorResourceId = Color.parseColor("#F5A623");
				break;
			case 5:
				magnitudeColorResourceId = Color.parseColor("#FF7D50");
				break;
			case 6:
				magnitudeColorResourceId = Color.parseColor("#FC6644");
				break;
			case 7:
				magnitudeColorResourceId = Color.parseColor("#E75F40");
				break;
			case 8:
				magnitudeColorResourceId = Color.parseColor("#E13A20");
				break;
			case 9:
				magnitudeColorResourceId = Color.parseColor("#D93218");
				break;
			default:
				magnitudeColorResourceId = Color.parseColor("#C03823");
				break;
		}

		return magnitudeColorResourceId;
	}
}