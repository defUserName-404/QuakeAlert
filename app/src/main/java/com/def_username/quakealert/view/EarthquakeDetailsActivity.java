package com.def_username.quakealert.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.def_username.quakealert.R;
import com.def_username.quakealert.viewmodel.ParseData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class EarthquakeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
	private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
	private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
	private MapView mMapView;

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
		String locationName = (getText("LOCATION_NAME"));
		double latitude = Double.parseDouble(ParseData.getLocation(this, locationName)[0]);
		double longitude = Double.parseDouble(ParseData.getLocation(this, locationName)[1]);
		LatLng location = new LatLng(latitude, longitude);

		googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_custom_style));
		googleMap.addMarker(new MarkerOptions().position(location));
		googleMap.getUiSettings().setMapToolbarEnabled(false);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
	}

	private void getLocationPermission() {
		String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

		if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			for (int grantResult : grantResults) {
				if (grantResult != PackageManager.PERMISSION_GRANTED) {
					return;
				}
			}
		}
	}

	private void showDataOnDetails() {
		TextView locationOffsetTextView = findViewById(R.id.textView_LocationLabel);
		TextView locationNameTextView = findViewById(R.id.textView_LocationName);
		TextView scaleTextView = findViewById(R.id.textView_Scale);
		TextView dateTextView = findViewById(R.id.textView_Date);
		TextView timeTextView = findViewById(R.id.textView_time);
		locationOffsetTextView.setText(getText("LOCATION_OFFSET"));
		locationNameTextView.setText(getText("LOCATION_NAME"));
		scaleTextView.setText(getText("SCALE"));
		String[] parts = getText("TIME").split("\n");
		dateTextView.setText(parts[0]);
		timeTextView.setText(parts[1]);

		int color = ParseData.getBGColor((Double.parseDouble(scaleTextView.getText().toString())));
		GradientDrawable bgShape = (GradientDrawable) scaleTextView.getBackground();
		bgShape.setColor(color);
	}

	private String getText(String key) {
		return getIntent().getExtras().getString(key);
	}
}