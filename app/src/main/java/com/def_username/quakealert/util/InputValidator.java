package com.def_username.quakealert.util;

import com.def_username.quakealert.ui.SearchFragment;

public class InputValidator extends SearchFragment {

	public static boolean validateInput() {
		return validateMinMagnitude()
				&& validateMaxMagnitude()
				&& validateIfMaxIfGreaterThanMinMagnitude();
	}

	private static boolean validateMinMagnitude() {
		if (!minMagnitude.equals("")) {
			double min = Double.parseDouble(minMagnitude);
			if (min < 0.0 || min > 10.0) {
				textInputEditTextMinMagnitude.setError("The magnitude must be in 0-10 range");
				return false;
			}
		}

		return true;
	}

	private static boolean validateMaxMagnitude() {
		if (!maxMagnitude.equals("")) {
			double max = Double.parseDouble(maxMagnitude);
			if (max < 0.0 || max > 10.0) {
				textInputEditTextMaxMagnitude.setError("The magnitude must be in 0-10 range");
				return false;
			}
		}

		return true;
	}

	private static boolean validateIfMaxIfGreaterThanMinMagnitude() {
		if (!minMagnitude.equals("") && !maxMagnitude.equals("")) {
			double min = Double.parseDouble(minMagnitude);
			double max = Double.parseDouble(maxMagnitude);

			if (min > max) {
				textInputEditTextMaxMagnitude.setError("Maximum magnitude has to be greater than or equal to minimum magnitude");
				return false;
			}
		}

		return true;
	}
}