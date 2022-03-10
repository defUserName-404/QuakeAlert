package com.def_username.quakealert.view;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.InputValidator;
import com.def_username.quakealert.viewmodel.ParseData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class SearchFragment extends Fragment {
	private static final short ANIMATION_DURATION = 200;
	protected static boolean IS_INPUT_VALID = true;
	protected static TextInputEditText mLocationTextInput, mDateTextInput, mMinMagnitudeTextInput, mMaxMagnitudeTextInput;
	protected static String latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate;
	private Fragment searchResultsFragment;
	private long startTime = 0L, endTime = 0L;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		MaterialButton mButtonSearchEarthquakes = rootView.findViewById(R.id.mButton_SearchEarthquakes);
		initializeViews(rootView);

		MaterialDatePicker<Pair<Long, Long>> rangedDatePicker = MaterialDatePicker.Builder
				.dateRangePicker()
				.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
				.setTitleText("Select Date Range")
				.build();

		mButtonSearchEarthquakes.setOnClickListener(listener -> {
			extractAndSendSearchRequest();
			IS_INPUT_VALID = true;
			InputValidator.validateInput();

			if (IS_INPUT_VALID) {
				searchResultsFragment = new ShowEarthquakesFragment(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate);

				requireActivity().getSupportFragmentManager()
						.beginTransaction()
						.hide(SearchActivity.searchContainerFragment)
						.add(R.id.fragmentContainer, searchResultsFragment)
						.setReorderingAllowed(true)
						.commit();

				setSearchAgainButtonVisibility(View.VISIBLE);

				SearchActivity.extendedSearchAgainFloatingActionButton.setOnClickListener(lst -> {
					setSearchAgainButtonVisibility(View.GONE);

					requireActivity().getSupportFragmentManager()
							.beginTransaction()
							.remove(searchResultsFragment)
							.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
							.show(SearchActivity.searchContainerFragment)
							.setReorderingAllowed(true)
							.commit();
				});
			}
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
		double[] latitudeAndLongitude = ParseData.getLatitudeLongitudeFromPlaceName(requireActivity().getApplicationContext(), mLocationTextInput.getText().toString());
		latitude = (latitudeAndLongitude[0] == 0) ? "" : String.valueOf(latitudeAndLongitude[0]);
		longitude = (latitudeAndLongitude[1] == 0) ? "" : String.valueOf(latitudeAndLongitude[1]);

		minMagnitude = mMinMagnitudeTextInput.getText().toString();
		maxMagnitude = mMaxMagnitudeTextInput.getText().toString();

		startDate = (startTime == 0) ? "" : ParseData.formatDateForResponse(new Date(startTime));
		endDate = (endTime == 0) ? "" : ParseData.formatDateForResponse(new Date(endTime));
	}

	private void setSearchAgainButtonVisibility(int visibility) {
		SearchActivity.extendedSearchAgainFloatingActionButton.postDelayed(() ->
				SearchActivity.extendedSearchAgainFloatingActionButton.setVisibility(visibility), ANIMATION_DURATION);
	}
}