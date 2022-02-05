package com.def_username.quakealert.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.def_username.quakealert.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_QuakeAlert);
		setContentView(R.layout.activity_main);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Recent Earthquakes");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.top_actionbar, menu);
		return true;
	}

	public void onSearchMenuClicked(MenuItem item) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
}