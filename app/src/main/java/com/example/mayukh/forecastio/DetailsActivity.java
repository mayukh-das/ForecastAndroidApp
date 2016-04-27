package com.example.mayukh.forecastio;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbarDetailsActivity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView tvCurrentSummaryMoreDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        toolbarDetailsActivity = (Toolbar) findViewById(R.id.toolbarDetailsActivity);
        setSupportActionBar(toolbarDetailsActivity);
        tvCurrentSummaryMoreDetails = (TextView) findViewById(R.id.tvCurrentSummaryMoreDetails);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle getData = getIntent().getExtras();
        String cityName  = getData.getString("cityName");
        String stateCode = getData.getString("stateCode");
        String forecastData = getData.getString("forecastJsonData");
        String degreeValue = getData.getString("degreeSymbol");
        tvCurrentSummaryMoreDetails.setText("More Details for " + cityName +", " + stateCode);

        NextTwentyFourHours nextTwentyFourHours = new NextTwentyFourHours();
        Bundle bundleNextTwentyFourHours = new Bundle();
        bundleNextTwentyFourHours.putString("forecastData", forecastData);
        bundleNextTwentyFourHours.putString("degreeValue",degreeValue);
        nextTwentyFourHours.setArguments(bundleNextTwentyFourHours);

        NextSevenDays nextSevenDays = new NextSevenDays();
        Bundle bundleNextSevenDays = new Bundle();
        bundleNextSevenDays.putString("forecastData", forecastData);
        bundleNextSevenDays.putString("degreeValue",degreeValue);
        nextSevenDays.setArguments(bundleNextSevenDays);

        adapter.addFragment(nextTwentyFourHours, "NEXT 24 HOURS");
        adapter.addFragment(nextSevenDays, "NEXT 7 DAYS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}