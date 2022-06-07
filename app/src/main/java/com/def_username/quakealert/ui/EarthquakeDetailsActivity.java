package com.def_username.quakealert.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.def_username.quakealert.R;
import com.def_username.quakealert.util.ParseData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class EarthquakeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
	private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
	private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
	private MapView mMapView;
	private TextView locationOffsetTextView, locationNameTextView, scaleTextView, dateTextView, timeTextView, depthTextView, feltTextView;
	private MaterialCardView materialCardView;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_earthquake_details);

		mMapView = findViewById(R.id.mapView);
		getLocationPermission();
		initGoogleMap(savedInstanceState);
		showDataOnDetails();

		Objects.requireNonNull(getSupportActionBar()).setTitle("Details");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return true;
	}

	private void initGoogleMap(Bundle savedInstanceState) {
		mMapView.onCreate(savedInstanceState);
		mMapView.getMapAsync(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		mMapView.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
		mMapView.onStop();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {
		String locationName = getText("LOCATION_NAME");
		double[] latitudeAndLongitude = ParseData.getLatitudeLongitudeFromPlaceName(this, locationName);
		LatLng location = new LatLng(latitudeAndLongitude[0], latitudeAndLongitude[1]);

		googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_custom_style));
		googleMap.addMarker(new MarkerOptions().position(location));
		googleMap.getUiSettings().setMapToolbarEnabled(false);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
	}

	private void getLocationPermission() {
		String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

		if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE)
			for (int grantResult : grantResults)
				if (grantResult != PackageManager.PERMISSION_GRANTED)
					return;
	}

	private void initViews() {
		materialCardView = findViewById(R.id.materialCardView);
		locationOffsetTextView = findViewById(R.id.textView_LocationLabel);
		locationNameTextView = findViewById(R.id.textView_LocationName);
		scaleTextView = findViewById(R.id.textView_Scale);
		dateTextView = findViewById(R.id.textView_Date);
		timeTextView = findViewById(R.id.textView_time);
		depthTextView = findViewById(R.id.textView_Depth);
		feltTextView = findViewById(R.id.textView_Felt);
	}

	private void showDataOnDetails() {
		initViews();
		locationOffsetTextView.setText(getText("LOCATION_OFFSET"));
		locationNameTextView.setText(getText("LOCATION_NAME"));
		String scale = getText("SCALE");
		scaleTextView.setText(scale);
		String[] parts = getText("TIME").split("\n");
		dateTextView.setText(parts[0]);
		timeTextView.setText(parts[1]);
		depthTextView.setText(String.format("Source is %s%s", getCoordinates()[2], getString(R.string.depth_text)));
		feltTextView.setText(getFeltTextView(scale));
		setShapeBGColor();
		setCardViewBGColor();
	}

	private String getText(String key) {
		return getIntent().getExtras().getString(key);
	}

	private String[] getCoordinates() {
		double latitude = getIntent().getDoubleArrayExtra("COORDINATES")[0];
		double longitude = getIntent().getDoubleArrayExtra("COORDINATES")[1];
		double depth = getIntent().getDoubleArrayExtra("COORDINATES")[2];

		if (depth < 0)
			depth *= -1;
		
		return new String[]{String.valueOf(latitude), String.valueOf(longitude), String.valueOf(depth)};
	}

	private String getFeltTextView(String scaleText) {
		double magnitude = Double.parseDouble(scaleText);
		String text;
		if (magnitude <= 1.0)
			text = "Can't be felt by humans";
		else if (magnitude <= 2.5)
			text = "Felt by only sensitive people";
		else if (magnitude <= 5.4)
			text = "Felt by most people, but causes minor damage";
		else if (magnitude <= 6.0)
			text = "May cause damage in densely populated areas";
		else if (magnitude <= 7.9)
			text = "Major earthquake, causes a lot of damage";
		else
			text = "Can totally destroy communities nearby epicenter";
		return text;
	}

	private int getSystemColorTheme() {
		return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK);
	}

	private void setShapeBGColor() {
		int color = ParseData.getBGColor((Double.parseDouble(scaleTextView.getText().toString())));
		GradientDrawable bgShape = (GradientDrawable) scaleTextView.getBackground();
		bgShape.setColor(color);
	}

	private void setCardViewBGColor() {
		if (getSystemColorTheme() == Configuration.UI_MODE_NIGHT_NO)
			materialCardView.setCardBackgroundColor(getResources().getColor(R.color.blue_700));
		else
			materialCardView.setCardBackgroundColor(getResources().getColor(R.color.primary_material_dark));
	}
}