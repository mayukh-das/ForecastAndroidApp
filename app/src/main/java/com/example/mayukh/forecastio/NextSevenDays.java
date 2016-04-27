package com.example.mayukh.forecastio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextSevenDays extends Fragment {

    public NextSevenDays() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.next_seven_days, container, false);

        Bundle getData = getActivity().getIntent().getExtras();
        String degreeValue = getData.getString("degreeSymbol");

        String[] tvSevenDaysDate = new String[8];
        tvSevenDaysDate[7] = "";
        String[] tvMinAndMaxTemp = new String[8];
        tvMinAndMaxTemp[7] = "";
        String day="",month="",minTemp="",maxTemp="",tvSevenMinAndMaxTemp="",date="",sevenDaysImageLocation="";
        String[] imageId = new String[8];
        imageId[7] = "";

        Integer[] bgColorId = {
                R.color.sevenYellow,
                R.color.sevenBlue,
                R.color.sevenPink,
                R.color.sevenGreen,
                R.color.sevenDarkPink,
                R.color.sevenLime,
                R.color.sevenIndigo,
                R.color.primaryWhite
        };

        Bundle bundle = getArguments();
        String forecastData = bundle.getString("forecastData");
        try
        {
            JSONObject jsonObjectResults = new JSONObject(forecastData);
            JSONArray nextSevenDaysWeatherJSONArray = jsonObjectResults.getJSONArray("nextSevenDays");
            for(int i=0;i<nextSevenDaysWeatherJSONArray.length();i++)
            {
                try
                {
                    JSONObject jsonObject = nextSevenDaysWeatherJSONArray.getJSONObject(i);
                    Iterator<String> iterJSONNextSevenDays = jsonObject.keys();
                    while (iterJSONNextSevenDays.hasNext())
                    {
                        String key = iterJSONNextSevenDays.next();
                        try
                        {
                            Object value = jsonObject.get(key);
                            if (key.equals("day")) {
                                day = value.toString();
                                String tvSevenDaysDateIter = day + ", " + month + " " + date;
                                tvSevenDaysDate[i] = tvSevenDaysDateIter;
                            }
                            if (key.equals("month")) {
                                month = value.toString();
                            }
                            if (key.equals("date")) {
                                date = value.toString();
                            }
                            if (key.equals("minTemp")) {
                                minTemp = value.toString();
                            }
                            if (key.equals("maxTemp")) {
                                maxTemp = value.toString();
                                tvSevenMinAndMaxTemp = "Min: " + minTemp + degreeValue +" | " + "Max: " + maxTemp+ degreeValue;
                                tvMinAndMaxTemp[i] = tvSevenMinAndMaxTemp;
                            }
                            if (key.equals("sevenDaysImageLocation")) {
                                sevenDaysImageLocation = value.toString();
                                imageId[i] = sevenDaysImageLocation;
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

        ListAdapter customSevenDaysRow = new CustomSevenDaysRow(getActivity(),tvSevenDaysDate,tvMinAndMaxTemp,imageId,bgColorId);
        ListView listView = (ListView) rootView.findViewById(R.id.lvItemList);
        listView.setAdapter(customSevenDaysRow);
        return rootView;
    }
}