package com.def_username.quakealert.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ResponseProcessing;

public class ShowEarthquakesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);
		ResponseProcessing responseProcessing = new ResponseProcessing(root);
		responseProcessing.sendRequest();

		return root;
	}
}