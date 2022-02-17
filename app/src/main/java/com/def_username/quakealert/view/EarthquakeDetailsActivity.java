package com.def_username.quakealert.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.def_username.quakealert.R;

import java.util.Objects;

public class EarthquakeDetailsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_earthquake_details);

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
}