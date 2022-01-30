package com.def_username.quakealert.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.def_username.quakealert.R;
import com.def_username.quakealert.view.ShowEarthquakesFragment;

import java.util.ArrayList;

public class ShowEarthquakeAdapter extends RecyclerView.Adapter<ShowEarthquakeAdapter.EarthquakeListViewHolder> {
	private final Context context;
	private final ArrayList<String> places, times, scales, placesOffset;

	public ShowEarthquakeAdapter(Context context, ArrayList<String> places, ArrayList<String> placesOffset,
								 ArrayList<String> times, ArrayList<String> scales) {
		this.context = context;
		this.places = places;
		this.placesOffset = placesOffset;
		this.times = times;
		this.scales = scales;
	}

	@NonNull
	@Override
	public EarthquakeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.earthquake_detail, parent, false);

		return new EarthquakeListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull EarthquakeListViewHolder holder, int position) {
		holder.place.setText(places.get(position));
		holder.placesOffset.setText(placesOffset.get(position));
		holder.time.setText(times.get(position));
		String magnitude = scales.get(position);
		holder.scale.setText(magnitude);
		holder.scale.setBackgroundResource(ShowEarthquakesFragment.getMagnitudeBGColor(Double.parseDouble(magnitude)));
	}

	@Override
	public int getItemCount() {
		return places.size();
	}

	public static class EarthquakeListViewHolder extends RecyclerView.ViewHolder {
		private final TextView place, time, scale, placesOffset;

		public EarthquakeListViewHolder(@NonNull View itemView) {
			super(itemView);
			place = itemView.findViewById(R.id.place_textview);
			placesOffset = itemView.findViewById(R.id.placeoffset_textview);
			time = itemView.findViewById(R.id.time_textview);
			scale = itemView.findViewById(R.id.scale_textview);
		}
	}
}