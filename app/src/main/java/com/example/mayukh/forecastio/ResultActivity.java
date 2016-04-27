package com.example.mayukh.forecastio;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    ImageView ivCurrentWeatherPic,ivFBSharePic;
    TextView tvCurrentTemperatureValue,tvCurrentSummary,tvDegreeSymbol,tvLowestAndHighestValue,tvPrecipitationValue,
             tvChanceOfRainValue,tvWindSpeedValue,tvDewValue,tvHumidityValue,tvVisibilityValue,
             tvSunriseValue,tvSunsetValue;
    Button btnMoreDetails,btnViewMap;
    String degreeSymbol,cityName,stateCode,highestValue,lowestValue,weatherSummary,
           currentTemperatureValue,weatherPic,latitude,longitude,forecastJSONData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        Toolbar toolbarResultActivity = (Toolbar) findViewById(R.id.toolbarResultActivity);
        setSupportActionBar(toolbarResultActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        init();

        Bundle getData = getIntent().getExtras();
        //TextView tvResults = (TextView) findViewById(R.id.tvResults);
        forecastJSONData = getData.getString("forecastResults");
        degreeSymbol     = getData.getString("degreeSymbol");
        cityName         = getData.getString("cityName");
        stateCode        = getData.getString("stateCode");
        //tvResults.setText(forecastJSONData);

        try
        {
            JSONObject jsonObjectResults = new JSONObject(forecastJSONData);

            JSONObject currentWeatherJSONObject = jsonObjectResults.getJSONObject("currentWeather");
            //JSONObject currentWeatherJSONObjectValues = currentWeatherJSONObject.getJSONObject
            //Log.i("currentWeather.length()", String.valueOf(currentWeatherJSONObject.length()));
            Iterator<String> iterJSONCurrentWeather = currentWeatherJSONObject.keys();
            while(iterJSONCurrentWeather.hasNext())
            {
                String key = iterJSONCurrentWeather.next();
                try
                {
                    Object value = currentWeatherJSONObject.get(key);
                    if(key.equals("currentImage"))
                    {
                        weatherPic = value.toString().substring(5,value.toString().length()-4);
                        String picURI = "@drawable/"+weatherPic;
                        int imageResource = getResources().getIdentifier(picURI,null,getPackageName());
                        Drawable currentWeatherPic = getResources().getDrawable(imageResource);
                        ivCurrentWeatherPic.setImageDrawable(currentWeatherPic);
                    }

                    if(key.equals("Latitude"))
                    {
                        latitude = value.toString();
                    }

                    if(key.equals("Longitude"))
                    {
                        longitude = value.toString();
                    }

                    if(key.equals("weatherSummary"))
                    {
                        weatherSummary = value.toString();
                        tvCurrentSummary.setText(weatherSummary + " in " + cityName +", " + stateCode);
                    }

                    if(key.equals("currentTemperature"))
                    {
                        currentTemperatureValue = value.toString();
                        tvCurrentTemperatureValue.setText(value.toString());
                        tvDegreeSymbol.setText("º" + degreeSymbol);
                    }

                    if(key.equals("highestTemperature"))
                    {
                        highestValue = " | H:"+value.toString();
                    }

                    if(key.equals("lowestTemperature"))
                    {
                        lowestValue = "L:"+value.toString();
                        tvLowestAndHighestValue.setText(lowestValue+highestValue);
                    }

                    if(key.equals("Precipitation"))
                    {
                        tvPrecipitationValue.setText(value.toString());
                    }

                    if(key.equals("Chance of Rain"))
                    {
                        tvChanceOfRainValue.setText(value.toString());
                    }

                    if(key.equals("Dew Point"))
                    {
                        tvDewValue.setText(value.toString());
                    }

                    if(key.equals("Wind Speed"))
                    {
                        tvWindSpeedValue.setText(value.toString());
                    }

                    if(key.equals("Humidity"))
                    {
                        tvHumidityValue.setText(value.toString());
                    }

                    if(key.equals("Visibility"))
                    {
                        tvVisibilityValue.setText(value.toString());
                    }

                    if(key.equals("Sunrise"))
                    {
                        tvSunriseValue.setText(value.toString());
                    }

                    if(key.equals("Sunset"))
                    {
                        tvSunsetValue.setText(value.toString());
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            /*for(int i = 0 ; i<currentWeatherJSONObject.length() ; i++ )
            {

            }*/


            /*JSONObject data = json.getJSONObject("data");
            JSONArray items = data.getJSONArray("items");
            for(int i = 0 ; i<items.length() ; i++ )
            {
                JSONObject video = items.getJSONObject(i);
                videoArrayList.add(video.getString("title"));
            }*/
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void init()
    {
        ivCurrentWeatherPic = (ImageView) findViewById(R.id.ivCurrentWeatherPic);
        ivFBSharePic = (ImageView) findViewById(R.id.ivFBSharePic);
        ivFBSharePic.setOnClickListener(this);

        tvCurrentSummary          = (TextView) findViewById(R.id.tvCurrentSummary);
        tvLowestAndHighestValue   = (TextView) findViewById(R.id.tvLowestAndHighest);
        tvDegreeSymbol            = (TextView) findViewById(R.id.tvDegreeSymbol);
        tvPrecipitationValue      = (TextView) findViewById(R.id.tvPrecipitationValue);
        tvChanceOfRainValue       = (TextView) findViewById(R.id.tvChanceOfRainValue);
        tvWindSpeedValue          = (TextView) findViewById(R.id.tvWindSpeedValue);
        tvDewValue                = (TextView) findViewById(R.id.tvDewPointValue);
        tvHumidityValue           = (TextView) findViewById(R.id.tvHumidityValue);
        tvVisibilityValue         = (TextView) findViewById(R.id.tvVisibilityValue);
        tvSunriseValue            = (TextView) findViewById(R.id.tvSunriseValue);
        tvSunsetValue             = (TextView) findViewById(R.id.tvSunsetValue);
        tvCurrentTemperatureValue = (TextView) findViewById(R.id.tvCurrentTemperatureValue);

        btnMoreDetails = (Button) findViewById(R.id.btnMoreDetails);
        btnViewMap     = (Button) findViewById(R.id.btnViewMap);
        btnMoreDetails.setOnClickListener(this);
        btnViewMap.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnMoreDetails:
                Bundle bundleMoreDetails = new Bundle();
                bundleMoreDetails.putString("forecastJsonData",forecastJSONData);
                bundleMoreDetails.putString("degreeSymbol",degreeSymbol);
                bundleMoreDetails.putString("cityName",cityName);
                bundleMoreDetails.putString("stateCode",stateCode);
                Intent detailsActivity = new Intent(this,DetailsActivity.class);
                detailsActivity.putExtras(bundleMoreDetails);
                startActivity(detailsActivity);
                break;
            case R.id.btnViewMap:
                Bundle bundle = new Bundle();
                bundle.putString("latitude", latitude);
                bundle.putString("longitude", longitude);
                Intent mapActivity = new Intent(this,MapActivity.class);
                mapActivity.putExtras(bundle);
                startActivity(mapActivity);
                break;
            case R.id.ivFBSharePic:
                fbShare();
                break;
        }
    }

    private void fbShare() {

        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "Facebook Post Successful" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Post Cancelled" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Post Cancelled" , Toast.LENGTH_SHORT).show();
            }
        });

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Current Weather in " + cityName +", " + stateCode)
                    .setContentDescription(weatherSummary + ", " + currentTemperatureValue + tvDegreeSymbol.getText().toString())
                    .setImageUrl(Uri.parse("http://cs-server.usc.edu:21703/hwEight/pics/"+weatherPic+".png"))
                    .setContentUrl(Uri.parse("http://forecast.io"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}