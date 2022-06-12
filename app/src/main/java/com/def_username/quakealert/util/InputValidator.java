package com.def_username.quakealert.util;

import com.def_username.quakealert.ui.SearchFragment;

public class InputValidator extends SearchFragment {

	public static void validateInput() {
		validateMinMagnitude();
		validateMaxMagnitude();
		validateIfMaxIfGreaterThanMinMagnitude();
	}

	private static void validateMinMagnitude() {
		if (!minMagnitude.equals("")) {
			double min = Double.parseDouble(minMagnitude);
			if (min < 0.0 || min > 10.0) {
				IS_INPUT_VALID = false;
				mMinMagnitudeTextInput.setError("The magnitude must be in 0-10 range");
			}
		}
	}

	private static void validateMaxMagnitude() {
		if (!maxMagnitude.equals("")) {
			double max = Double.parseDouble(maxMagnitude);
			if (max < 0.0 || max > 10.0) {
				IS_INPUT_VALID = false;
				mMaxMagnitudeTextInput.setError("The magnitude must be in 0-10 range");
			}
		}
	}

	private static void validateIfMaxIfGreaterThanMinMagnitude() {
		if (!minMagnitude.equals("") && !maxMagnitude.equals("")) {
			double min = Double.parseDouble(minMagnitude);
			double max = Double.parseDouble(maxMagnitude);

			if (min > max) {
				IS_INPUT_VALID = false;
				mMaxMagnitudeTextInput.setError("Maximum magnitude has to be greater than or equal to minimum magnitude");
			}
		}
	}
}