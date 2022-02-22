package com.def_username.quakealert.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.def_username.quakealert.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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
		LatLng sydney = new LatLng(-33.852, 151.211);
		googleMap.addMarker(new MarkerOptions()
				.position(sydney)
				.title("Marker in Sydney"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
}