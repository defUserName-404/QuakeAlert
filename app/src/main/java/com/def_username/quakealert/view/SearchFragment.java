package com.def_username.quakealert.view;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ResponseProcessing;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);
		TextInputEditText mTextDate = rootView.findViewById(R.id.mTextInputEditText_Date);

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
			responseProcessing.sendRequest();
		});
		
		mTextDate.setOnClickListener(listener -> {
			MaterialDatePicker<Pair<Long, Long>> rangedDatePicker = MaterialDatePicker.Builder
					.dateRangePicker()
					.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
					.build();
			rangedDatePicker.show(getActivity().getSupportFragmentManager(), "Material Ranged Date Picker");
			rangedDatePicker.addOnPositiveButtonClickListener(l -> {
				mTextDate.setText(rangedDatePicker.getHeaderText());
			});
		});

		return rootView;
	}
}