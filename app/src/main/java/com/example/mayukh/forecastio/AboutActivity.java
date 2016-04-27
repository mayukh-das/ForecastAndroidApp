package com.example.mayukh.forecastio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        Toolbar toolbarAboutActivity = (Toolbar) findViewById(R.id.toolbarAboutActivity);
        setSupportActionBar(toolbarAboutActivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}