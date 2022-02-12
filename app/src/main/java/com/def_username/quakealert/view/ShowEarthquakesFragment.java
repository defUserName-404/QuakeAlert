package com.def_username.quakealert.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ParseData;
import com.def_username.quakealert.viewmodel.ResponseProcessing;

import java.util.Calendar;
import java.util.Date;

public class ShowEarthquakesFragment extends Fragment {
	private String latitude, longitude, minMagnitude, maxMagnitude, endDate, startDate;

	private void init() {
		latitude = longitude = "";
		minMagnitude = maxMagnitude = "";

		long time = Calendar.getInstance().getTimeInMillis();

		endDate = ParseData.formatDateForResponse(new Date(time));
		startDate = ParseData.formatDateForResponse(new Date(time - 86400000));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		ResponseProcessing responseProcessing = new ResponseProcessing(rootView);

		init();
		responseProcessing.sendRequest(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate);

		return rootView;
	}
}