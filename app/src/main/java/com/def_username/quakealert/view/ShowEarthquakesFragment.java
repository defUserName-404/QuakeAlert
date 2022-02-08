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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		ResponseProcessing responseProcessing = new ResponseProcessing(root);

		String latitude = "", longitude = "";
		String minMagnitude = "", maxMagnitude = "";
		String startDate = ParseData.formatDate(new Date(Calendar.getInstance().getTimeInMillis()));
		String endDate = ParseData.formatDate(new Date(Calendar.getInstance().getTimeInMillis() - 86400000));

		responseProcessing.sendRequest(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate);

		return root;
	}
}