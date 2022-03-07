package com.def_username.quakealert.viewmodel;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.def_username.quakealert.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ShowEarthquakeAdapter extends RecyclerView.Adapter<ShowEarthquakeAdapter.EarthquakeListViewHolder> {
	private final Context context;
	private final ArrayList<String> places, times, scales, placesOffset, url;
	private final OnEarthquakeListListener onEarthquakeListListener;

	public ShowEarthquakeAdapter(Context context, ArrayList<String> places, ArrayList<String> placesOffset, ArrayList<String> times,
								 ArrayList<String> scales, ArrayList<String> url, OnEarthquakeListListener onEarthquakeListListener) {
		this.context = context;
		this.places = places;
		this.placesOffset = placesOffset;
		this.times = times;
		this.scales = scales;
		this.url = url;
		this.onEarthquakeListListener = onEarthquakeListListener;
	}

	@NonNull
	@Override
	public EarthquakeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.earthquake_detail, parent, false);

		return new EarthquakeListViewHolder(view, onEarthquakeListListener);
	}

	@Override
	public void onBindViewHolder(@NonNull EarthquakeListViewHolder holder, int position) {
		holder.place.setText(places.get(position));
		holder.placesOffset.setText(placesOffset.get(position));
		holder.time.setText(times.get(position));
		String magnitude = scales.get(position);
		holder.scale.setText(magnitude);
		int color = ParseData.getBGColor(Double.parseDouble(magnitude));
		holder.materialCardView.setStrokeColor(color);
		GradientDrawable bgShape = (GradientDrawable) holder.scale.getBackground();
		bgShape.setColor(color);
	}

	@Override
	public int getItemCount() {
		return places.size();
	}

	public static class EarthquakeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final TextView place, time, scale, placesOffset;
		private final MaterialCardView materialCardView;
		private final OnEarthquakeListListener onEarthquakeListListener;

		public EarthquakeListViewHolder(@NonNull View itemView, OnEarthquakeListListener onEarthquakeListListener) {
			super(itemView);
			place = itemView.findViewById(R.id.place_textview);
			placesOffset = itemView.findViewById(R.id.placeoffset_textview);
			time = itemView.findViewById(R.id.time_textview);
			scale = itemView.findViewById(R.id.scale_textview);
			materialCardView = itemView.findViewById(R.id.materialCardView);

			this.onEarthquakeListListener = onEarthquakeListListener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			onEarthquakeListListener.showEarthquakeDetails(getAdapterPosition());
		}
	}

	public interface OnEarthquakeListListener {
		void showEarthquakeDetails(int position);
	}
}