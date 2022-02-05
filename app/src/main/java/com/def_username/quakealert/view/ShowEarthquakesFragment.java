package com.def_username.quakealert.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.ParseData;
import com.def_username.quakealert.model.SingletonRequestData;
import com.def_username.quakealert.viewmodel.ShowEarthquakeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShowEarthquakesFragment extends Fragment {
	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		sendRequest();

		return root;
	}

	private void sendRequest() {
		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2022-02-04&endtime=2022-02-05&minmagnitude=1&orderby=time-asc";

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				response -> {
					ParseData.setSampleJsonResponse(response);
					onResponseReceived();
				},
				error -> Log.e("Network Error", "Can't access URL"));

		SingletonRequestData.getInstance(this.getContext()).addToRequestQueue(jsonRequest);
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
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}
}