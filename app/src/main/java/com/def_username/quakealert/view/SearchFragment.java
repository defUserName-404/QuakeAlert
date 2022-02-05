package com.def_username.quakealert.view;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ResponseProcessing;
import com.google.android.material.button.MaterialButton;

public class SearchFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
			responseProcessing.sendRequest();
		});

		return rootView;
	}
}