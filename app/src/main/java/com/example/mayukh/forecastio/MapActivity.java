package com.example.mayukh.forecastio;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MapActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);


        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        Bundle getData = getIntent().getExtras();
        float latitude = Float.parseFloat(getData.getString("latitude"));
        float longitude = Float.parseFloat(getData.getString("longitude"));

        MapFragment mapFragment = new MapFragment();

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putFloat("latitude",latitude);
        fragmentBundle.putFloat("longitude",longitude);

        mapFragment.setArguments(fragmentBundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,mapFragment).commit();
    }
}
