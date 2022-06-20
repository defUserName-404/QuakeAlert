package com.def_username.quakealert.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Coordinate;
import com.def_username.quakealert.model.DateRange;
import com.def_username.quakealert.model.MagnitudeRange;
import com.def_username.quakealert.model.Request;
import com.def_username.quakealert.util.EarthquakeDataParser;
import com.def_username.quakealert.viewmodel.RequestProcessor;

import java.util.Calendar;
import java.util.Date;

public class ShowEarthquakesFragment extends Fragment {

	private static boolean IMPLEMENT_SCROLL_LISTENER_FLAG = false;
	private final Coordinate coordinate;
	private final MagnitudeRange magnitudeRange;
	private final DateRange dateRange;
	private final String sortBy;
	private RecyclerView mRecyclerView;

	public ShowEarthquakesFragment() {
		coordinate = new Coordinate("", "");
		magnitudeRange = new MagnitudeRange("", "");
		long time = Calendar.getInstance().getTimeInMillis();
		String endDate = EarthquakeDataParser.formatDateForResponse(new Date(time));
		String startDate = EarthquakeDataParser.formatDateForResponse(new Date(time - 86400000 * 2));
		dateRange = new DateRange(startDate, endDate);
		sortBy = "";
	}

	public ShowEarthquakesFragment(Coordinate coordinate, MagnitudeRange magnitudeRange,
								   DateRange dateRange, String sortBy) {
		this.coordinate = coordinate;
		this.magnitudeRange = magnitudeRange;
		this.dateRange = dateRange;
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

		RequestProcessor requestProcessor = new RequestProcessor(rootView);
		Request request = new Request(
				coordinate,
				magnitudeRange,
				dateRange,
				sortBy
		);
		requestProcessor.sendRequest(request);

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