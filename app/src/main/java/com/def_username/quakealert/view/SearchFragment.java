package com.def_username.quakealert.view;

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
import com.def_username.quakealert.viewmodel.ResponseProcessing;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment {
	private TextInputEditText mLocationTextInput, mDateTextInput, mMinMagnitudeTextInput, mMaxMagnitudeTextInput;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);
		initializeViews(rootView);

		ConstraintLayout searchContainer = rootView.findViewById(R.id.searchContainer);
		ExtendedFloatingActionButton extendedSearchAgainFloatingActionButton = rootView.findViewById(R.id.extendedFloatingActionButton_SearchAgain);

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
			extractAndSendSearchRequest();

			createAnimation(searchContainer, extendedSearchAgainFloatingActionButton, View.GONE);

			extendedSearchAgainFloatingActionButton.setOnClickListener(lst -> {
				createAnimation(searchContainer, extendedSearchAgainFloatingActionButton, View.VISIBLE);
			});

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