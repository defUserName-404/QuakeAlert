package com.def_username.quakealert;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class Util {
	public static Activity getActivity(Context ctx) {
		Context context = ctx;
		while (context instanceof ContextWrapper) {
			if (context instanceof Activity) {
				return (Activity) context;
			}
			context = ((ContextWrapper) context).getBaseContext();
		}
		return null;
	}
}