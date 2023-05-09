package com.def_username.quakealert.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.def_username.quakealert.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_QuakeAlert);
		setContentView(R.layout.activity_main);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Recent Earthquakes");
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.fragmentContainer, new ShowEarthquakesFragment())
				.setReorderingAllowed(true)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_actionbar_main, menu);
		return true;
	}

	public void onSearchMenuClicked(MenuItem item) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

}
