package com.def_username.quakealert.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.model.ParseData;
import com.def_username.quakealert.viewmodel.ShowEarthquakeAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ShowEarthquakesFragment extends Fragment {
	private RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		recyclerView = rootView.findViewById(R.id.earthquakeList_recyclerview);

		ArrayList<Earthquake> earthquakes = ParseData.extractEarthquakes();
		ArrayList<String> places = new ArrayList<>();
		ArrayList<String> times = new ArrayList<>();
		ArrayList<String> scales = new ArrayList<>();

		for (Earthquake earthquake : earthquakes) {
			// Create a new Date object from the time in milliseconds of the earthquake
			Date dateObject = new Date(earthquake.getTimeInMilliseconds());
			// Format the date string (i.e. "Mar 3, 1984")
			String formattedDate = ParseData.formatDate(dateObject);
			String formattedTime = ParseData.formatTime(dateObject);

			places.add(earthquake.getLocation());
			times.add(formattedDate + " " + formattedTime);
			scales.add(Double.toString(earthquake.getMagnitude()));
		}

		ShowEarthquakeAdapter showEarthquakeAdapter =
				new ShowEarthquakeAdapter(rootView.getContext(), places, times, scales);
		recyclerView.setAdapter(showEarthquakeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return rootView;
	}
}