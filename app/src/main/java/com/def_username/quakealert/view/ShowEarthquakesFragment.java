package com.def_username.quakealert.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ShowEarthquakeAdapter;

public class ShowEarthquakesFragment extends Fragment {
	private RecyclerView recyclerView;
	private String[] places, times, scales;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		recyclerView = rootView.findViewById(R.id.earthquakeList_recyclerview);
		places = getResources().getStringArray(R.array.place);
		times = getResources().getStringArray(R.array.time);
		scales = getResources().getStringArray(R.array.scale);

		ShowEarthquakeAdapter showEarthquakeAdapter = new ShowEarthquakeAdapter(rootView.getContext(), places, times, scales);
		recyclerView.setAdapter(showEarthquakeAdapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return rootView;
	}
}