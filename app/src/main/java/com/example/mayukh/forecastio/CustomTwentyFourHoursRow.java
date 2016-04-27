package com.example.mayukh.forecastio;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Mayukh on 07-12-2015.
 */
public class CustomTwentyFourHoursRow extends ArrayAdapter<String> {

    private final Activity context;
    private final Integer[] bgTwentyFourColorId;
    private final String[] time;
    private final String[] temp;
    private final String[] summaryPic;
    private final int flag;

    View customView;

    CustomTwentyFourHoursRow(Activity context,Integer[] bgTwentyFourColorId,String[] time,String[] temp,String[] summaryPic,int flag)
    {
        super(context,R.layout.custom_twentyfourhours_row,time);
        this.context = context;
        this.bgTwentyFourColorId = bgTwentyFourColorId;
        this.time = time;
        this.temp = temp;
        this.summaryPic = summaryPic;
        this.flag = flag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater nextTwentyFourHours = LayoutInflater.from(getContext());
        customView = nextTwentyFourHours.inflate(R.layout.custom_twentyfourhours_row, parent, false);

        if(flag == 0)
        {
            RelativeLayout llTwentyFourHours = (RelativeLayout) customView.findViewById(R.id.llTwentyFourHours);
            TextView tvTwentyFourHoursTime = (TextView) customView.findViewById(R.id.tvTwentyFourHoursTime);
            ImageView ivTwentyFourHoursPic = (ImageView) customView.findViewById(R.id.ivTwentyFourHoursPic);
            TextView tvTwentyFourHoursTemp = (TextView) customView.findViewById(R.id.tvTwentyFourHoursTemp);

            llTwentyFourHours.setBackgroundColor(context.getResources().getColor(bgTwentyFourColorId[position % 2]));
            tvTwentyFourHoursTemp.setText(temp[position]);
            tvTwentyFourHoursTime.setText(time[position]);

            if (position < 24)
            {
                String weatherPic = summaryPic[position].toString().substring(5, summaryPic[position].toString().length() - 4);
                String picURI = "@drawable/" + weatherPic;
                int imageResource = getContext().getResources().getIdentifier(picURI, null, getContext().getPackageName());
                Drawable currentWeatherPic = getContext().getResources().getDrawable(imageResource);
                ivTwentyFourHoursPic.setImageDrawable(currentWeatherPic);
            }
        }
        else
        {
            RelativeLayout llTwentyFourHours = (RelativeLayout) customView.findViewById(R.id.llTwentyFourHours);
            TextView tvTwentyFourHoursTime = (TextView) customView.findViewById(R.id.tvTwentyFourHoursTime);
            ImageView ivTwentyFourHoursPic = (ImageView) customView.findViewById(R.id.ivTwentyFourHoursPic);
            TextView tvTwentyFourHoursTemp = (TextView) customView.findViewById(R.id.tvTwentyFourHoursTemp);

            llTwentyFourHours.setBackgroundColor(context.getResources().getColor(bgTwentyFourColorId[position % 2]));
            tvTwentyFourHoursTemp.setText(temp[position]);
            tvTwentyFourHoursTime.setText(time[position]);

            if ( position < 48)
            {
                String weatherPic = summaryPic[position].toString().substring(5, summaryPic[position].toString().length() - 4);
                String picURI = "@drawable/" + weatherPic;
                int imageResource = getContext().getResources().getIdentifier(picURI, null, getContext().getPackageName());
                Drawable currentWeatherPic = getContext().getResources().getDrawable(imageResource);
                ivTwentyFourHoursPic.setImageDrawable(currentWeatherPic);
            }
        }
        return customView;
    }
}