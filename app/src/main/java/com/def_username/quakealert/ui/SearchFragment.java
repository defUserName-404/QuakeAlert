package com.def_username.quakealert.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Coordinate;
import com.def_username.quakealert.model.DateRange;
import com.def_username.quakealert.model.MagnitudeRange;
import com.def_username.quakealert.util.InputValidator;
import com.def_username.quakealert.util.ParseData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.Objects;

public class SearchFragment extends Fragment {
	private static final short ANIMATION_DURATION = 200;
	protected static TextInputEditText textInputEditTextLocation,
			textInputEditTextDate, textInputEditTextMinMagnitude, textInputEditTextMaxMagnitude;
	protected static String latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate, sortBy;
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

			if (InputValidator.validateInput()) {
				sortBy = SearchActivity.getSortMethod();
				searchResultsFragment = new ShowEarthquakesFragment(
						new Coordinate(latitude, longitude),
						new MagnitudeRange(minMagnitude, maxMagnitude),
						new DateRange(startDate, endDate),
						sortBy
				);

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

		textInputEditTextDate.setOnClickListener(listener -> {
			rangedDatePicker.show(requireActivity().getSupportFragmentManager(), "Material Ranged Date Picker");
			rangedDatePicker.addOnPositiveButtonClickListener(selection -> {
				startTime = selection.first;
				endTime = selection.second;

				textInputEditTextDate.setText(rangedDatePicker.getHeaderText());
			});
		});

		return rootView;
	}

	private void initializeViews(View rootView) {
		textInputEditTextLocation = rootView.findViewById(R.id.mTextInputEditText_Location);
		textInputEditTextMinMagnitude = rootView.findViewById(R.id.mTextInputEditText_MinimumMagnitude);
		textInputEditTextMaxMagnitude = rootView.findViewById(R.id.mTextInputEditText_MaximumMagnitude);
		textInputEditTextDate = rootView.findViewById(R.id.mTextInputEditText_Date);
	}

	private void extractAndSendSearchRequest() {
		double[] latitudeAndLongitude = ParseData.getLatitudeLongitudeFromPlaceName(requireActivity().getApplicationContext(), textInputEditTextLocation.getText().toString());
		latitude = (latitudeAndLongitude[0] == 0) ? "" : String.valueOf(latitudeAndLongitude[0]);
		longitude = (latitudeAndLongitude[1] == 0) ? "" : String.valueOf(latitudeAndLongitude[1]);
		minMagnitude = Objects.requireNonNull(textInputEditTextMinMagnitude.getText()).toString();
		maxMagnitude = Objects.requireNonNull(textInputEditTextMaxMagnitude.getText()).toString();
		startDate = (startTime == 0) ? "" : ParseData.formatDateForResponse(new Date(startTime));
		endDate = (endTime == 0) ? "" : ParseData.formatDateForResponse(new Date(endTime));
	}

	private void setSearchAgainButtonVisibility(int visibility) {
		SearchActivity.extendedSearchAgainFloatingActionButton.postDelayed(() ->
				SearchActivity.extendedSearchAgainFloatingActionButton.setVisibility(visibility), ANIMATION_DURATION);
	}
}