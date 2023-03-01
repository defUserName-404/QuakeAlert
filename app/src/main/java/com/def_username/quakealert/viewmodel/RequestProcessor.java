package com.def_username.quakealert.viewmodel;

import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Coordinate;
import com.def_username.quakealert.model.DateRange;
import com.def_username.quakealert.model.MagnitudeRange;
import com.def_username.quakealert.util.DataRequest;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
				}) {
			@Override
			protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
				try {
					Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
					if (cacheEntry == null)
						cacheEntry = new Cache.Entry();
					final long cacheHitButRefreshed = 5 * 60 * 1000; // in 5 minutes cache will be hit, but also refreshed on background
					final long cacheExpired = 6 * 60 * 60 * 1000; // in 6 hours this cache entry expires completely
					long now = System.currentTimeMillis();
					final long softExpire = now + cacheHitButRefreshed;
					final long ttl = now + cacheExpired;
					cacheEntry.data = response.data;
					cacheEntry.softTtl = softExpire;
					cacheEntry.ttl = ttl;
					String headerValue;
					assert response.headers != null;
					headerValue = response.headers.get("Date");
					if (headerValue != null)
						cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
					headerValue = response.headers.get("Last-Modified");
					if (headerValue != null)
						cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
					cacheEntry.responseHeaders = response.headers;
					final String jsonString = new String(response.data,
							HttpHeaderParser.parseCharset(response.headers));
					return Response.success(new JSONObject(jsonString), cacheEntry);
				} catch (UnsupportedEncodingException | JSONException e) {
					return Response.error(new ParseError(e));
				}
			}
		};
		DataRequest.getInstance(root.getContext())
				.addToRequestQueue(jsonRequest);
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
