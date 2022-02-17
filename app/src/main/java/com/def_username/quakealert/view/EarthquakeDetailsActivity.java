package com.def_username.quakealert.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.def_username.quakealert.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Objects;

public class EarthquakeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
	private MapView mMapView;
	private GoogleMap map;
	private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_earthquake_details);
		mMapView = findViewById(R.id.mapView);
		initGoogleMap(savedInstanceState);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Details");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
	}

	/*
	 * Go back to the last activity by pressing up button at the top
	 */
	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return true;
	}

	private void initGoogleMap(Bundle savedInstanceState) {
		// *** IMPORTANT ***
		// MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
		// objects or sub-Bundles.

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
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}

		map = googleMap;
		map.setMyLocationEnabled(true);
	}
}