package com.def_username.quakealert.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.def_username.quakealert.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
	
	public static ExtendedFloatingActionButton extendedSearchAgainFloatingActionButton;
	public static Fragment searchContainerFragment;
	private static String sortByCategory = "time", sortByOrder = "";

	public static String getSortMethod() {
		return (sortByOrder.equals("") ? sortByCategory : sortByCategory + "-" + sortByOrder);
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_actionbar_search, menu);
		return true;
	}

	public void onSortByTimeClicked(MenuItem item) {
		item.setChecked(true);
		sortByCategory = "time";
	}

	public void onSortByMagnitudeClicked(MenuItem item) {
		item.setChecked(true);
		sortByCategory = "magnitude";
	}

	public void onAscendingOrderClicked(MenuItem item) {
		item.setChecked(true);
		sortByOrder = "asc";
	}

	public void onDescendingOrderClicked(MenuItem item) {
		item.setChecked(true);
	}

}
