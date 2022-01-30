package com.def_username.quakealert.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.def_username.quakealert.R;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_QuakeAlert);
		setContentView(R.layout.activity_main);
	}
}