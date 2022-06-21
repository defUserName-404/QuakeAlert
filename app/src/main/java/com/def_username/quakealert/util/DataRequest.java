package com.def_username.quakealert.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DataRequest {
	@SuppressLint("StaticFieldLeak")
	private static DataRequest instance;
	@SuppressLint("StaticFieldLeak")
	private static Context context;
	private RequestQueue requestQueue;

	private DataRequest(Context ctx) {
		context = ctx;
		requestQueue = getRequestQueue();
	}

	public static synchronized DataRequest getInstance(Context context) {
		if (instance == null)
			instance = new DataRequest(context);

		return instance;
	}

	private RequestQueue getRequestQueue() {
		if (requestQueue == null)
			requestQueue = Volley.newRequestQueue(context.getApplicationContext());

		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> request) {
		getRequestQueue().add(request);
	}
}