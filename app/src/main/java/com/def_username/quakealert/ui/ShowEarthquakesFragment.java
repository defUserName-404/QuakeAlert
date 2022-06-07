package com.def_username.quakealert.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.def_username.quakealert.R;
import com.def_username.quakealert.util.ParseData;
import com.def_username.quakealert.viewmodel.ResponseProcessing;

import java.util.Calendar;
import java.util.Date;

public class ShowEarthquakesFragment extends Fragment {
	private final String latitude, longitude, minMagnitude, maxMagnitude, endDate, startDate, sortBy;
	private RecyclerView mRecyclerView;
	private static boolean IMPLEMENT_SCROLL_LISTENER_FLAG = false;

	public ShowEarthquakesFragment() {
		latitude = longitude = "";
		minMagnitude = maxMagnitude = "";

		long time = Calendar.getInstance().getTimeInMillis();

		endDate = ParseData.formatDateForResponse(new Date(time));
		startDate = ParseData.formatDateForResponse(new Date(time - 86400000 * 2));
		sortBy = "";
	}

	public ShowEarthquakesFragment(String latitude, String longitude, String minMagnitude, String maxMagnitude,
								   String startDate, String endDate, String sortBy) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.maxMagnitude = maxMagnitude;
		this.minMagnitude = minMagnitude;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sortBy = sortBy;

		IMPLEMENT_SCROLL_LISTENER_FLAG = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_show_earthquakes, container, false);

		if (IMPLEMENT_SCROLL_LISTENER_FLAG) {
			mRecyclerView = rootView.findViewById(R.id.earthquakeList_recyclerview);
			disableSearchAgainButtonWhileScrolling();
		}

		ResponseProcessing responseProcessing = new ResponseProcessing(rootView);
		responseProcessing.sendRequest(latitude, longitude, minMagnitude, maxMagnitude, startDate, endDate, sortBy);

		return rootView;
	}

	private void disableSearchAgainButtonWhileScrolling() {
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				if (dy > 0 || dy < 0 && SearchActivity.extendedSearchAgainFloatingActionButton.isShown())
					SearchActivity.extendedSearchAgainFloatingActionButton.hide();
			}

			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				if (newState == RecyclerView.SCROLL_STATE_IDLE)
					SearchActivity.extendedSearchAgainFloatingActionButton.show();

				super.onScrollStateChanged(recyclerView, newState);
			}
		});
	}
}