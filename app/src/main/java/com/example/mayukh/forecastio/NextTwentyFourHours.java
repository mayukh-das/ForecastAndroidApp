package com.example.mayukh.forecastio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextTwentyFourHours extends Fragment implements View.OnClickListener{

    public NextTwentyFourHours() {
        // Required empty public constructor
    }

    JSONObject jsonObjectResults;
    JSONArray nextTwentyFourHoursWeatherJSONArray;
    View rootView;

    String[] time2 = new String[48];
    String[] temp2 = new String[48];
    String[] summaryPic2 = new String[48];

    String[] time1 = new String[24];
    String[] temp1 = new String[24];
    String[] summaryPic1 = new String[24];

    String degreeValue;

    Integer[] bgTwentyFourHours = {
            R.color.twentyGrayFourHoursRow,
            R.color.primaryWhite
    };

    ListView listView;

    Button btnLoadMore,btnLoadMore2;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.next_twenty_four_hours,container,false);

        Bundle bundle = getArguments();
        String forecastData = bundle.getString("forecastData");

        try
        {
            jsonObjectResults = new JSONObject(forecastData);
            nextTwentyFourHoursWeatherJSONArray = jsonObjectResults.getJSONArray("nextTwentyFourHours");
            for(int i = 0 ; i<24 ; i++)
            {
                try
                {
                    JSONObject jsonObject = nextTwentyFourHoursWeatherJSONArray.getJSONObject(i);
                    Iterator<String> iterJSONNextTwentyFourHours = jsonObject.keys();
                    while (iterJSONNextTwentyFourHours.hasNext())
                    {
                        String key = iterJSONNextTwentyFourHours.next();
                        try
                        {
                            Object value = jsonObject.get(key);
                            if (key.equals("Time")) {
                                time1[i] = value.toString();
                                time2[i] = value.toString();
                            }
                            if (key.equals("Summary")) {
                                summaryPic1[i] = value.toString();
                                summaryPic2[i] = value.toString();
                            }
                            if (key.equals("Temp")) {
                                /*temp1[i] = value.toString().substring(0,2);
                                temp2[i] = value.toString().substring(0,2);*/
                                temp1[i] = String.valueOf(new Double(Double.parseDouble(value.toString())).intValue());
                                temp2[i] = String.valueOf(new Double(Double.parseDouble(value.toString())).intValue());
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Bundle getData = getActivity().getIntent().getExtras();
        degreeValue = getData.getString("degreeSymbol");

        TextView tvNextTwentyFourHoursTempHeader = (TextView) rootView.findViewById(R.id.tvNextTwentyFourHoursTempHeader);
        tvNextTwentyFourHoursTempHeader.setText("Temp(º" + degreeValue + ")");

        ListAdapter customTwentyFourHoursRow = new CustomTwentyFourHoursRow(getActivity(),bgTwentyFourHours,time1,temp1,summaryPic1,0);
        listView = (ListView) rootView.findViewById(R.id.lvTwentyFourHourList);

        btnLoadMore2 = new Button(getContext());

        btnLoadMore = new Button(getContext());
        btnLoadMore.setText("+");
        btnLoadMore.setX(300);

        btnLoadMore.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                for(int i = 24 ; i<nextTwentyFourHoursWeatherJSONArray.length() ; i++)
                {
                    try
                    {
                        JSONObject jsonObject = nextTwentyFourHoursWeatherJSONArray.getJSONObject(i);
                        Iterator<String> iterJSONNextTwentyFourHours = jsonObject.keys();
                        while (iterJSONNextTwentyFourHours.hasNext())
                        {
                            String key = iterJSONNextTwentyFourHours.next();
                            try
                            {
                                Object value = jsonObject.get(key);
                                if (key.equals("Time")) {
                                    time2[i] = value.toString();
                                }
                                if (key.equals("Summary")) {
                                    summaryPic2[i] = value.toString();
                                }
                                if (key.equals("Temp")) {
                                    //temp2[i] = value.toString().substring(0,2);
                                    temp2[i] = String.valueOf(new Double(Double.parseDouble(value.toString())).intValue());
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                TextView tvNextTwentyFourHoursTempHeader = (TextView) rootView.findViewById(R.id.tvNextTwentyFourHoursTempHeader);
                tvNextTwentyFourHoursTempHeader.setText("Temp(º" + degreeValue + ")");

                int currentPosition = listView.getFirstVisiblePosition();
                CustomTwentyFourHoursRow customTwentyFourHoursRow = new CustomTwentyFourHoursRow(getActivity(),bgTwentyFourHours,time2,temp2,summaryPic2,1);
                listView.setAdapter(customTwentyFourHoursRow);
                listView.setSelectionFromTop(currentPosition + 1, 0);

                btnLoadMore.setVisibility(View.GONE);
                btnLoadMore2.setVisibility(View.GONE);

            }
        });
        listView.addFooterView(btnLoadMore);

        btnLoadMore2.setText("Load More");
        btnLoadMore2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hey There 2", Toast.LENGTH_SHORT).show();
            }
        });
        listView.addFooterView(btnLoadMore2);

        listView.setAdapter(customTwentyFourHoursRow);
        return rootView;
    }

    @Override
    public void onClick(View v) {}
}