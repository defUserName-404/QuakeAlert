package com.def_username.quakealert.view;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ResponseProcessing;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment {
	private TextInputEditText mLocationTextInput, mDateTextInput, mMinMagnitudeTextInput, mMaxMagnitudeTextInput;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);
		initializeViews(rootView);

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			LinearLayout linearLayout = rootView.findViewById(R.id.LinearLayout_SearchOptions);
			MaterialButton mButtonDropdownArrow = rootView.findViewById(R.id.mButton_DropDownArrow);
			linearLayout.setVisibility(View.GONE);
			mButtonDropdownArrow.setVisibility(View.VISIBLE);
			mButtonDropdownArrow.setOnClickListener(lst -> {
				linearLayout.setVisibility(View.VISIBLE);
				mButtonDropdownArrow.setVisibility(View.GONE);
			});

			ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
			extractAndSendSearchRequest();
			responseProcessing.sendRequest();
		});

		mDateTextInput.setOnClickListener(listener -> {
			MaterialDatePicker<Pair<Long, Long>> rangedDatePicker = MaterialDatePicker.Builder
					.dateRangePicker()
					.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
					.setTitleText("Select Date Range")
					.build();
			rangedDatePicker.show(requireActivity().getSupportFragmentManager(), "Material Ranged Date Picker");
			rangedDatePicker.addOnPositiveButtonClickListener(l -> mDateTextInput.setText(rangedDatePicker.getHeaderText()));
		});

		return rootView;
	}

	private void initializeViews(View rootView) {
		mLocationTextInput = rootView.findViewById(R.id.mTextInputEditText_Location);
		mMinMagnitudeTextInput = rootView.findViewById(R.id.mTextInputEditText_MinimumMagnitude);
		mMaxMagnitudeTextInput = rootView.findViewById(R.id.mTextInputEditText_MaximumMagnitude);
		mDateTextInput = rootView.findViewById(R.id.mTextInputEditText_Date);
	}

	private void extractAndSendSearchRequest() {
		String location = mLocationTextInput.getText().toString();
		String minMagnitude = mMinMagnitudeTextInput.getText().toString();
		String maxMagnitude = mMaxMagnitudeTextInput.getText().toString();
		String date = mDateTextInput.getText().toString();
	}
}