package com.def_username.quakealert.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.SingletonRequestData;
import com.def_username.quakealert.view.EarthquakeDetailsActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ResponseProcessing implements ShowEarthquakeAdapter.OnEarthquakeListListener {
	private final View root;
	private final StringBuilder URL = new StringBuilder("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time");
	private ArrayList<String> places, placesOffset, times, scales;
	private ArrayList<double[]> coordinates;

	public ResponseProcessing(View view) {
		root = view;
	}

	public void sendRequest(String latitude, String longitude, String minMagnitude, String maxMagnitude, String startDate, String endDate) {
		processingURL(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate);
		LinearProgressIndicator mLinearProgressIndicatorRequestLoading = root.findViewById(R.id.networkConnectivity_mProgressIndicator);

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL.toString(), null,
				response -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					onResponseReceived(response);
				},
				error -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					onResponseError(error);
				});

		SingletonRequestData.getInstance(root.getContext()).addToRequestQueue(jsonRequest);
	}

	private void processingURL(String latitude, String longitude, String minMagnitude, String maxMagnitude, String startDate, String endDate) {
		if (!latitude.equals(""))
			URL.append("&latitude=").append(latitude);
		if (!longitude.equals(""))
			URL.append("&longitude=").append(longitude).append("&maxradiuskm=1000");
		if (!minMagnitude.equals(""))
			URL.append("&minmagnitude=").append(minMagnitude);
		if (!maxMagnitude.equals(""))
			URL.append("&maxmagnitude=").append(maxMagnitude);
		if (!startDate.equals(""))
			URL.append("&starttime=").append(startDate);
		if (!endDate.equals(""))
			URL.append("&endtime=").append(endDate);
	}

	private void onResponseReceived(JSONObject response) {
		RecyclerView recyclerView = root.getRootView().findViewById(R.id.earthquakeList_recyclerview);
		ParseData.setSampleJsonResponse(response);

		ArrayList<Earthquake> earthquakes = ParseData.extractEarthquakes();
		places = new ArrayList<>();
		placesOffset = new ArrayList<>();
		times = new ArrayList<>();
		scales = new ArrayList<>();
		coordinates = new ArrayList<>();

		for (Earthquake earthquake : earthquakes) {
			Date dateObject = new Date(earthquake.getTimeInMilliseconds());
			String originalLocation = earthquake.getLocation();
			String formattedDate = ParseData.formatDate(dateObject).toUpperCase(Locale.getDefault());
			String formattedTime = ParseData.formatTime(dateObject).toUpperCase(Locale.getDefault());
			String magnitude = ParseData.formatDecimal(earthquake.getMagnitude());
			String primaryLocation = ParseData.formatLocation(originalLocation)[0];
			String locationOffset = ParseData.formatLocation(originalLocation)[1];
			double[] coordinate = earthquake.getCoordinates();

			if (earthquake.getMagnitude() < 0)
				continue;

			places.add(primaryLocation);
			placesOffset.add(locationOffset);
			times.add(formattedDate + "\n" + formattedTime);
			scales.add(magnitude);
			coordinates.add(coordinate);
		}

		ShowEarthquakeAdapter showEarthquakeAdapter =
				new ShowEarthquakeAdapter(root.getContext(), places, placesOffset, times, scales, coordinates, this);
		recyclerView.setAdapter(showEarthquakeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

		int totalResults = ParseData.getCount();
		if (totalResults == 0) {
			root.findViewById(R.id.imageView_NothingFound).setVisibility(View.VISIBLE);
			root.findViewById(R.id.textView_NothingFound).setVisibility(View.VISIBLE);
		}
	}

	private void onResponseError(VolleyError error) {
		if (error.networkResponse == null) {
			root.findViewById(R.id.imageView_NetworkError).setVisibility(View.VISIBLE);
			root.findViewById(R.id.textView_NetworkError).setVisibility(View.VISIBLE);
			return;
		}
		int statusCode = error.networkResponse.statusCode;
		if (statusCode == 400) {
			root.findViewById(R.id.imageView_NothingFound).setVisibility(View.VISIBLE);
			root.findViewById(R.id.textView_NothingFound).setVisibility(View.VISIBLE);
		} else if (statusCode == 408) {
			root.findViewById(R.id.imageView_NetworkError).setVisibility(View.VISIBLE);
			root.findViewById(R.id.textView_NetworkError).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void showEarthquakeDetails(int position) {
		Context context = root.getContext();
		Intent intent = new Intent(context, EarthquakeDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("LOCATION_OFFSET", placesOffset.get(position));
		bundle.putString("LOCATION_NAME", places.get(position));
		bundle.putString("SCALE", scales.get(position));
		bundle.putString("TIME", times.get(position));
		bundle.putDoubleArray("COORDINATES", coordinates.get(position));
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}