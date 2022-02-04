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
import com.android.volley.toolbox.StringRequest;
import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.ParseData;
import com.def_username.quakealert.model.SingletonRequestData;
import com.def_username.quakealert.viewmodel.ShowEarthquakeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShowEarthquakesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		RecyclerView recyclerView = rootView.findViewById(R.id.earthquakeList_recyclerview);

		sendRequest();

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
				new ShowEarthquakeAdapter(rootView.getContext(), places, placesOffset, times, scales);
		recyclerView.setAdapter(showEarthquakeAdapter);

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return rootView;
	}

	public static int getMagnitudeBGColor(double magnitude) {
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

	private void sendRequest() {
		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&minmagnitude=4";

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				ParseData::setSampleJsonResponse,
				error -> Log.e("Network Error", "Can't access URL"));

		SingletonRequestData.getInstance(this.getContext()).addToRequestQueue(stringRequest);
	}
}