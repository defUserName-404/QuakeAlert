package com.def_username.quakealert.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.ui.EarthquakeDetailsActivity;
import com.def_username.quakealert.util.EarthquakeDataParser;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResponseProcessor implements ShowEarthquakeAdapter.OnEarthquakeListClickListener {

	private final View root;
	private List<Earthquake> earthquakeList;

	public ResponseProcessor(View root) {
		this.root = root;
	}

	public void onResponseReceived(JSONObject response) {
		RecyclerView recyclerView = root.getRootView().findViewById(R.id.earthquakeList_recyclerview);
		EarthquakeDataParser.setSampleJsonResponse(response);

		earthquakeList = EarthquakeDataParser.extractEarthquakes();

		ShowEarthquakeAdapter showEarthquakeAdapter =
				new ShowEarthquakeAdapter(earthquakeList, this);
		recyclerView.setAdapter(showEarthquakeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

		if (earthquakeList.size() == 0) {
			root.findViewById(R.id.imageView_NothingFound).setVisibility(View.VISIBLE);
			root.findViewById(R.id.textView_NothingFound).setVisibility(View.VISIBLE);
		}
	}

	public void onResponseError(VolleyError error) {
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
		String formattedDate = EarthquakeDataParser.formatDate(dateObject).toUpperCase(Locale.getDefault());
		String formattedTime = EarthquakeDataParser.formatTime(dateObject).toUpperCase(Locale.getDefault());
		String magnitude = EarthquakeDataParser.formatDecimal(earthquake.getMagnitude());
		String primaryLocation = EarthquakeDataParser.formatLocation(originalLocation)[0];
		String locationOffset = EarthquakeDataParser.formatLocation(originalLocation)[1];
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