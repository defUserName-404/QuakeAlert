package com.def_username.quakealert.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.SingletonRequestData;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ResponseProcessing {
	private final View root;

	public ResponseProcessing(View view) {
		root = view;
	}

	public void sendRequest(String latitude, String longitude, String minMagnitude, String maxMagnitude, String startDate, String endDate) {
		StringBuilder url = new StringBuilder("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time");

		if (!latitude.equals(""))
			url.append("&latitude=").append(latitude);
		if (!longitude.equals(""))
			url.append("&longitude=").append(longitude).append("&maxradiuskm=1000");
		if (!minMagnitude.equals(""))
			url.append("&minmagnitude=").append(minMagnitude);
		if (!maxMagnitude.equals(""))
			url.append("&maxmagnitude=").append(maxMagnitude);
		if (!startDate.equals(""))
			url.append("&starttime=").append(startDate);
		if (!endDate.equals(""))
			url.append("&endtime=").append(endDate);

		Log.e("Message 2", url.toString());
		LinearProgressIndicator mLinearProgressIndicatorRequestLoading = root.findViewById(R.id.networkConnectivity_mProgressIndicator);

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
				response -> {
					ParseData.setSampleJsonResponse(response);
					if (mLinearProgressIndicatorRequestLoading != null)
						mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					onResponseReceived();
				},
				error -> Log.e("Network Error", "Can't access URL"));

		SingletonRequestData.getInstance(root.getContext()).addToRequestQueue(jsonRequest);
	}

	private void onResponseReceived() {
		RecyclerView recyclerView = root.findViewById(R.id.earthquakeList_recyclerview);

		ArrayList<Earthquake> earthquakes = ParseData.extractEarthquakes();
		ArrayList<String> places = new ArrayList<>();
		ArrayList<String> placesOffset = new ArrayList<>();
		ArrayList<String> times = new ArrayList<>();
		ArrayList<String> scales = new ArrayList<>();

		for (Earthquake earthquake : earthquakes) {
			Date dateObject = new Date(earthquake.getTimeInMilliseconds());
			String originalLocation = earthquake.getLocation();
			String formattedDate = ParseData.formatDate(dateObject).toUpperCase(Locale.getDefault());
			String formattedTime = ParseData.formatTime(dateObject).toUpperCase(Locale.getDefault());
			String magnitude = ParseData.formatMagnitude(earthquake.getMagnitude());
			String primaryLocation = ParseData.formatLocation(originalLocation)[0];
			String locationOffset = ParseData.formatLocation(originalLocation)[1];

			places.add(primaryLocation);
			placesOffset.add(locationOffset);
			times.add(formattedDate + "\n" + formattedTime);
			scales.add(magnitude);
		}

		ShowEarthquakeAdapter showEarthquakeAdapter =
				new ShowEarthquakeAdapter(root.getContext(), places, placesOffset, times, scales);
		recyclerView.setAdapter(showEarthquakeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
	}
}