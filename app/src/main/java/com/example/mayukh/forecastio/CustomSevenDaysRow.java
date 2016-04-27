package com.example.mayukh.forecastio;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mayukh on 06-12-2015.
 */
public class CustomSevenDaysRow extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] date;
    private final String[] sevenDaysMinAndMaxTemp;
    private final String[] sevenDaysImageId;
    private final Integer[] sevenDaysbgColorId;

    CustomSevenDaysRow(Activity context,String[] date,String[] sevenDaysMinAndMaxTemp,String[] sevenDaysImageId,Integer[] sevenDaysbgColorId)
    {
        super(context,R.layout.custom_sevendays_row,date);
        this.context = context;
        this.date = date;
        this.sevenDaysMinAndMaxTemp = sevenDaysMinAndMaxTemp;
        this.sevenDaysImageId = sevenDaysImageId;
        this.sevenDaysbgColorId = sevenDaysbgColorId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater nextSevenDays = LayoutInflater.from(getContext());
        View customView = nextSevenDays.inflate(R.layout.custom_sevendays_row, parent, false);

        LinearLayout linearLayout = (LinearLayout) customView.findViewById(R.id.llSevenDays);
        TextView tvSevenDaysDate = (TextView) customView.findViewById(R.id.tvSevenDaysDate);
        ImageView ivSevenDaysPic = (ImageView) customView.findViewById(R.id.ivSevenDaysPic);
        TextView tvSevenDaysMinAndMaxTemp = (TextView) customView.findViewById(R.id.tvSevenDaysMinAndMax);

        linearLayout.setBackgroundColor(context.getResources().getColor(sevenDaysbgColorId[position]));
        tvSevenDaysDate.setText(date[position]);

        if(position<7)
        {
            String weatherPic = sevenDaysImageId[position].toString().substring(5,sevenDaysImageId[position].toString().length()-4);
            String picURI = "@drawable/"+weatherPic;
            int imageResource = getContext().getResources().getIdentifier(picURI, null, getContext().getPackageName());
            Drawable currentWeatherPic = getContext().getResources().getDrawable(imageResource);
            ivSevenDaysPic.setImageDrawable(currentWeatherPic);
        }
        tvSevenDaysMinAndMaxTemp.setText(sevenDaysMinAndMaxTemp[position]);

        return customView;
    }
}