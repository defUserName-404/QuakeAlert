package com.def_username.quakealert.viewmodel;

import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Coordinate;
import com.def_username.quakealert.model.DateRange;
import com.def_username.quakealert.model.MagnitudeRange;
import com.def_username.quakealert.util.DataRequest;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class RequestProcessor {

	private final View root;
	private final StringBuilder url =
			new StringBuilder("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson");

	public RequestProcessor(View root) {
		this.root = root;
	}

	public void sendRequest(com.def_username.quakealert.model.Request request) {
		processingURL(request);
		LinearProgressIndicator mLinearProgressIndicatorRequestLoading =
				root.findViewById(R.id.ProgressIndicator_NetworkConnectivity);

		ResponseProcessor responseProcessor = new ResponseProcessor(root);

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
				response -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					responseProcessor.onResponseReceived(response);
				},
				error -> {
					mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);
					responseProcessor.onResponseError(error);
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
		String maxMagnitude = magnitudeRange.getMaxMagnitude();
		String startDate = dateRange.getStartDate();
		String endDate = dateRange.getEndDate();

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
		if (!sortBy.equals(""))
			url.append("&orderby=").append(sortBy);

		Log.i("URL", url.toString());
	}
}