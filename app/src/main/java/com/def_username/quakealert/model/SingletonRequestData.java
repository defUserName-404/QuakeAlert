package com.def_username.quakealert.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestData {
	private static SingletonRequestData instance;
	private RequestQueue requestQueue;
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