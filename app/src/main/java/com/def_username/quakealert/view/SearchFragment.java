package com.def_username.quakealert.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ParseData;
import com.def_username.quakealert.viewmodel.ResponseProcessing;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {
	private TextInputEditText mLocationTextInput, mDateTextInput, mMinMagnitudeTextInput, mMaxMagnitudeTextInput;
	private String latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate;
	private long startTime = 0L, endTime = 0L;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);
		initializeViews(rootView);

		ConstraintLayout searchContainer = rootView.findViewById(R.id.searchContainer);
		ExtendedFloatingActionButton extendedSearchAgainFloatingActionButton = rootView.findViewById(R.id.extendedFloatingActionButton_SearchAgain);
		MaterialDatePicker<Pair<Long, Long>> rangedDatePicker = MaterialDatePicker.Builder
				.dateRangePicker()
				.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
				.setTitleText("Select Date Range")
				.build();

		LinearProgressIndicator mLinearProgressIndicatorRequestLoading = rootView.findViewById(R.id.networkConnectivity_mProgressIndicator);
		mLinearProgressIndicatorRequestLoading.setVisibility(View.GONE);

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			mLinearProgressIndicatorRequestLoading.setVisibility(View.VISIBLE);
			ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
			extractAndSendSearchRequest();

			createAnimation(searchContainer, extendedSearchAgainFloatingActionButton, View.GONE);

			extendedSearchAgainFloatingActionButton.setOnClickListener(lst ->
					createAnimation(searchContainer, extendedSearchAgainFloatingActionButton, View.VISIBLE));

			responseProcessing.sendRequest(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate);
		});

		mDateTextInput.setOnClickListener(listener -> {
			rangedDatePicker.show(requireActivity().getSupportFragmentManager(), "Material Ranged Date Picker");
			rangedDatePicker.addOnPositiveButtonClickListener(selection -> {
				startTime = selection.first;
				endTime = selection.second;

				mDateTextInput.setText(rangedDatePicker.getHeaderText());
			});
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
		List<Address> addresses;
		Geocoder geocoder = new Geocoder(requireActivity().getApplicationContext(), Locale.getDefault());
		latitude = "";
		longitude = "";

		try {
			addresses = geocoder.getFromLocationName(mLocationTextInput.getText().toString(), 1);
			longitude = Double.toString(addresses.get(0).getLongitude());
			latitude = Double.toString(addresses.get(0).getLatitude());
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
		}

		minMagnitude = mMinMagnitudeTextInput.getText().toString();
		maxMagnitude = mMaxMagnitudeTextInput.getText().toString();

		startDate = (startTime == 0) ? "" : ParseData.formatDateForResponse(new Date(startTime));
		endDate = (endTime == 0) ? "" : ParseData.formatDateForResponse(new Date(endTime));
	}

	private void createAnimation(ConstraintLayout searchContainer, ExtendedFloatingActionButton extendedSearchAgainFloatingActionButton, int visibility) {
		final int ANIMATION_DURATION = 200;

		Transition transition;
		transition = (visibility == View.VISIBLE) ? new Slide(Gravity.BOTTOM) : new Slide(Gravity.TOP);
		transition.setDuration(ANIMATION_DURATION);
		transition.addTarget(searchContainer);

		TransitionManager.beginDelayedTransition(searchContainer, transition);
		searchContainer.setVisibility(visibility);

		extendedSearchAgainFloatingActionButton.postDelayed(() ->
				extendedSearchAgainFloatingActionButton.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE), ANIMATION_DURATION);
	}
}