package com.def_username.quakealert.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.def_username.quakealert.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
	public static ExtendedFloatingActionButton extendedSearchAgainFloatingActionButton;
	public static Fragment searchContainerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Search Earthquakes");
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);

		searchContainerFragment = new SearchFragment();

		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.fragmentContainer, searchContainerFragment)
				.setReorderingAllowed(true)
				.commit();

		extendedSearchAgainFloatingActionButton = findViewById(R.id.extendedFloatingActionButton_SearchAgain);
	}
}