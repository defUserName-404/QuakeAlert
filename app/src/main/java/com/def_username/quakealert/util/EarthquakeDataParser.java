package com.def_username.quakealert.util;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.def_username.quakealert.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthquakeDataParser {

	private static JSONObject FINAL_RESPONSE;

	// To prevent from creating an object of this class
	private EarthquakeDataParser() {
	}

	public static void setSampleJsonResponse(JSONObject response) {
		FINAL_RESPONSE = response;
	}

	public static List<Earthquake> extractEarthquakes() {
		List<Earthquake> earthquakes = new ArrayList<>();
		try {
			JSONArray earthquakeArray = FINAL_RESPONSE.getJSONArray("features");
			for (int i = 0; i < earthquakeArray.length(); i++) {
				JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
				JSONObject properties = currentEarthquake.getJSONObject("properties");
				String mag = properties.getString("mag");
				if (mag.equals("null"))
					continue;
				double magnitude = Double.parseDouble(mag);
				String location = properties.getString("place");
				long time = properties.getLong("time");
				JSONArray coordinatesJsonArray = currentEarthquake.getJSONObject("geometry")
						.getJSONArray("coordinates");
				double[] coordinates = new double[3];
				for (int j = 0; j < 3; j++)
					coordinates[j] = Double.parseDouble(formatDecimal(coordinatesJsonArray.getDouble(j)));
				Earthquake earthquake = new Earthquake(magnitude, location, time, coordinates);
				earthquakes.add(earthquake);
			}
		} catch (JSONException e) {
			Log.e("Parsing Error", "Problem parsing the earthquake JSON results", e);
		}
		return earthquakes;
	}

	public static String formatDecimal(double decimalNumber) {
		return new DecimalFormat("0.0").format(decimalNumber);
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
		return new String[] {primaryLocation, locationOffset};
	}

	private static String capitalizeFirstCharacter(String string) {
		if (string == null || string.isEmpty())
			return string;
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public static double[] getLatitudeLongitudeFromPlaceName(Context context, String locationName) {
		double latitude = 0, longitude = 0;
		List<Address> addresses;
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		try {
			addresses = geocoder.getFromLocationName(locationName, 10);
			int count = 1;
			if (!addresses.isEmpty()) {
				latitude = addresses.get(0).getLatitude();
				longitude = addresses.get(0).getLongitude();
			}
			while ((latitude == 0 && longitude == 0) && (count < addresses.size())) {
				latitude = addresses.get(count).getLatitude();
				longitude = addresses.get(count).getLongitude();
				count++;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return new double[] {latitude, longitude};
	}

	public static int getBGColor(double magnitude) {
		int magnitudeColorResourceId;
		int magnitudeFloor = (int) Math.floor(magnitude);
		switch (magnitudeFloor) {
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
