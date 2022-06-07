package com.def_username.quakealert.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

	public static class SingletonRequestData {
		@SuppressLint("StaticFieldLeak")
		private static SingletonRequestData instance;
		private RequestQueue requestQueue;
		@SuppressLint("StaticFieldLeak")
		private static Context context;

		private SingletonRequestData(Context ctx) {
			context = ctx;
			requestQueue = getRequestQueue();
		}

		public static synchronized SingletonRequestData getInstance(Context context) {
			if (instance == null)
				instance = new SingletonRequestData(context);

			return instance;
		}

		public RequestQueue getRequestQueue() {
			if (requestQueue == null)
				requestQueue = Volley.newRequestQueue(context.getApplicationContext());

			return requestQueue;
		}

		public <T> void addToRequestQueue(Request<T> request) {
			getRequestQueue().add(request);
		}
	}
}
