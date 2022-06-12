package com.def_username.quakealert.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Coordinate;
import com.def_username.quakealert.model.DateRange;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.MagnitudeRange;
import com.def_username.quakealert.ui.EarthquakeDetailsActivity;
import com.def_username.quakealert.util.DataRequest;
import com.def_username.quakealert.util.ParseData;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResponseProcessing implements ShowEarthquakeAdapter.OnEarthquakeListClickListener {
	private final View root;
	private final StringBuilder URL = new StringBuilder("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson");
	private List<Earthquake> earthquakeList;

	public ResponseProcessing(View view) {
		root = view;
	}

	public void sendRequest(com.def_username.quakealert.model.Request request) {
		processingURL(request);
		LinearProgressIndicator mLinearProgressIndicatorRequestLoading =
				root.findViewById(R.id.networkConnectivity_mProgressIndicator);

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL.toString(), null,
				response -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					onResponseReceived(response);
				},
				error -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					onResponseError(error);
				});

		DataRequest.getInstance(root.getContext()).addToRequestQueue(jsonRequest);
	}

	private void processingURL(com.def_username.quakealert.model.Request request) {
		Coordinate coordinate = request.getCoordinate();
		DateRange dateRange = request.getDateRange();
		MagnitudeRange magnitudeRange = request.getMagnitudeRange();
		String sortBy = request.getSortBy();
		String latitude = coordinate.getLatitude();
		String longitude = coordinate.getLongitude();
		String minMagnitude = magnitudeRange.getMinMagnitude();
		String maxMagnitude  = magnitudeRange.getMaxMagnitude();
		String startDate = dateRange.getStartDate();
		String endDate = dateRange.getEndDate();

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
		if (!sortBy.equals(""))
			URL.append("&orderby=").append(sortBy);

		Log.i("URL", URL.toString());
	}

	private void onResponseReceived(JSONObject response) {
		RecyclerView recyclerView = root.getRootView().findViewById(R.id.earthquakeList_recyclerview);
		ParseData.setSampleJsonResponse(response);

		earthquakeList = ParseData.extractEarthquakes();

		ShowEarthquakeAdapter showEarthquakeAdapter =
				new ShowEarthquakeAdapter(earthquakeList, this);
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
		Earthquake earthquake = earthquakeList.get(position);
		Date dateObject = new Date(earthquake.getTimeInMilliseconds());
		String originalLocation = earthquake.getLocation();
		String formattedDate = ParseData.formatDate(dateObject).toUpperCase(Locale.getDefault());
		String formattedTime = ParseData.formatTime(dateObject).toUpperCase(Locale.getDefault());
		String magnitude = ParseData.formatDecimal(earthquake.getMagnitude());
		String primaryLocation = ParseData.formatLocation(originalLocation)[0];
		String locationOffset = ParseData.formatLocation(originalLocation)[1];
		double[] coordinate = earthquake.getCoordinates();

		bundle.putString("LOCATION_OFFSET", locationOffset);
		bundle.putString("LOCATION_NAME", primaryLocation);
		bundle.putString("SCALE", magnitude);
		bundle.putString("TIME", formattedDate + "\n" + formattedTime);
		bundle.putDoubleArray("COORDINATES", coordinate);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}