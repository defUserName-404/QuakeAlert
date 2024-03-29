package com.def_username.quakealert.viewmodel;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.def_username.quakealert.R;
import com.def_username.quakealert.model.Earthquake;
import com.def_username.quakealert.util.EarthquakeDataParser;
import com.google.android.material.card.MaterialCardView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowEarthquakeAdapter extends RecyclerView.Adapter<ShowEarthquakeAdapter.EarthquakeListViewHolder> {

	private final List<Earthquake> earthquakeList;
	private final OnEarthquakeListClickListener onEarthquakeListClickListener;

	public ShowEarthquakeAdapter(List<Earthquake> earthquakeList,
								 OnEarthquakeListClickListener onEarthquakeListClickListener) {
		this.earthquakeList = earthquakeList;
		this.onEarthquakeListClickListener = onEarthquakeListClickListener;
	}

	@NonNull
	@Override
	public EarthquakeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.earthquake_detail, parent, false);
		return new EarthquakeListViewHolder(view, onEarthquakeListClickListener);
	}

	@SuppressLint("SetTextI18n")
	@Override
	public void onBindViewHolder(@NonNull EarthquakeListViewHolder holder, int position) {
		Earthquake earthquake = earthquakeList.get(position);
		Date dateObject = new Date(earthquake.getTimeInMilliseconds());
		String originalLocation = earthquake.getLocation();
		String formattedDate = EarthquakeDataParser.formatDate(dateObject).toUpperCase(Locale.getDefault());
		String formattedTime = EarthquakeDataParser.formatTime(dateObject).toUpperCase(Locale.getDefault());
		String magnitude = EarthquakeDataParser.formatDecimal(earthquake.getMagnitude());
		String primaryLocation = EarthquakeDataParser.formatLocation(originalLocation)[0];
		String locationOffset = EarthquakeDataParser.formatLocation(originalLocation)[1];
		holder.textViewPlace.setText(primaryLocation);
		holder.textViewPlacesOffset.setText(locationOffset);
		holder.textViewTime.setText(formattedDate + "\n" + formattedTime);
		holder.textViewScale.setText(magnitude);
		int color = EarthquakeDataParser.getBGColor(Double.parseDouble(magnitude));
		holder.materialCardView.setStrokeColor(color);
		GradientDrawable bgShape = (GradientDrawable) holder.textViewScale.getBackground();
		bgShape.setColor(color);
	}

	@Override
	public int getItemCount() {
		return earthquakeList.size();
	}

	public interface OnEarthquakeListClickListener {

		void showEarthquakeDetails(int position);

	}

	public static class EarthquakeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private final TextView textViewPlace, textViewTime, textViewScale, textViewPlacesOffset;
		private final MaterialCardView materialCardView;
		private final OnEarthquakeListClickListener onEarthquakeListClickListener;

		public EarthquakeListViewHolder(@NonNull View itemView, OnEarthquakeListClickListener onEarthquakeListClickListener) {
			super(itemView);
			textViewPlace = itemView.findViewById(R.id.place_textview);
			textViewPlacesOffset = itemView.findViewById(R.id.placeoffset_textview);
			textViewTime = itemView.findViewById(R.id.time_textview);
			textViewScale = itemView.findViewById(R.id.scale_textview);
			materialCardView = itemView.findViewById(R.id.materialCardView);
			this.onEarthquakeListClickListener = onEarthquakeListClickListener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			onEarthquakeListClickListener.showEarthquakeDetails(getAdapterPosition());
		}

	}

}
