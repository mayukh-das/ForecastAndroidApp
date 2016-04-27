package com.example.mayukh.forecastio;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    Button btnAbout,btnSearch,btnClear;
    EditText etStreet,etCity;
    Spinner spinStates;
    TextView tvPoweredBy,tvForecastIO,tvErrorMessage;
    RadioGroup radioGroupTemperature;
    RadioButton radioButtonTemperature;
    ImageView ivForecastIOLink;

    String degreeSymbol = "";
    String cityAddress = "";
    String spinState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize()
    {
        btnAbout  = (Button) findViewById(R.id.btnAbout);
        btnClear  = (Button) findViewById(R.id.btnClear);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        etCity   = (EditText) findViewById(R.id.etCity);
        etStreet = (EditText) findViewById(R.id.etStreet);

        spinStates = (Spinner) findViewById(R.id.spinStates);

        tvPoweredBy    = (TextView) findViewById(R.id.tvPoweredByText);
        //tvForecastIO   = (TextView) findViewById(R.id.tvForecastIOLink);
        tvErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);

        ivForecastIOLink = (ImageView) findViewById(R.id.ivForecastIOLink);

        radioGroupTemperature = (RadioGroup) findViewById(R.id.radioGroupTemperature);
        int selectedID = radioGroupTemperature.getCheckedRadioButtonId();
        radioButtonTemperature = (RadioButton) findViewById(selectedID);

        btnAbout.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        tvPoweredBy.setOnClickListener(this);
        ivForecastIOLink.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnAbout:
                Intent aboutActivity = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(aboutActivity);
                break;

            case R.id.btnClear:
                clearInput();
                break;

            case R.id.btnSearch:
                validateAndSubmitInput();
                break;

            case R.id.tvPoweredByText:
            case R.id.ivForecastIOLink:
                visitForecastIOLink();
                break;
        }
    }

    private void clearInput()
    {
        etStreet.setText("");
        etCity.setText("");
        spinStates.setSelection(0);
        radioGroupTemperature.check(R.id.radioFahrenheit);
        tvErrorMessage.setText("");
    }

    private void visitForecastIOLink()
    {
        Uri uri = Uri.parse("http://forecast.io");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }

    private void validateAndSubmitInput()
    {
        boolean errCode = false;
        //TODO: UNCOMMENT THESE
        String streetAddress = etStreet.getText().toString();
        cityAddress = etCity.getText().toString();
        spinState = spinStates.getSelectedItem().toString();
        int spinStatePosition = spinStates.getSelectedItemPosition();

        if(streetAddress == null || streetAddress.isEmpty())
        {
            errCode = true;
            tvErrorMessage.setText("Please enter a Street Address.");
        }
        else if(cityAddress == null || cityAddress.isEmpty())
        {
            errCode = true;
            tvErrorMessage.setText("Please enter a City.");
        }
        else if(spinState.equals("Select"))
        {
            errCode = true;
            tvErrorMessage.setText("Please enter a State.");
        }

        if(!errCode)
        {
            int selectedID = radioGroupTemperature.getCheckedRadioButtonId();
            radioButtonTemperature = (RadioButton) findViewById(selectedID);
            tvErrorMessage.setText("");

            if(radioButtonTemperature.getText().toString().equals("Fahrenheit"))
            {
                degreeSymbol="F";
            }
            else
            {
                degreeSymbol="C";
            }
            try
            {

                String getQuery =   "street="       + URLEncoder.encode(streetAddress, "UTF-8")
                                  + "&city="        + URLEncoder.encode(cityAddress,"UTF-8")
                                  + "&state="       + URLEncoder.encode(spinState,"UTF-8")
                                  + "&degreeValue=" + URLEncoder.encode(radioButtonTemperature.getText().toString(),"UTF-8");

                String forecastURL = "http://csciwebapp-env.elasticbeanstalk.com/?" + getQuery;

                //String forecastURL = "http://csciwebapp-env.elasticbeanstalk.com/?street=2707+Portland+Street&city=Los+Angeles&state=CA&degreeValue=Fahrenheit";
                GetWeatherDetails getWeatherDetails = new GetWeatherDetails();
                //Log.i("URL: ",forecastURL);
                getWeatherDetails.execute(forecastURL);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }


    public class GetWeatherDetails extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(params[0]);

            try
            {
                HttpResponse response = httpClient.execute(getRequest);
                StatusLine statusline = response.getStatusLine();
                int statusCode = statusline.getStatusCode();

                if(statusCode != 200)
                {
                    return null;
                }

                InputStream jsonStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                String jsonData = builder.toString();

                Bundle resultsBundle = new Bundle();
                resultsBundle.putString("forecastResults", jsonData);

                Bundle degreeSymbolBundle = new Bundle();
                degreeSymbolBundle.putString("degreeSymbol", degreeSymbol);

                Bundle cityNameBundle = new Bundle();
                degreeSymbolBundle.putString("cityName", cityAddress);

                Bundle spinStateBundle = new Bundle();
                degreeSymbolBundle.putString("stateCode", spinState);

                Intent resultsIntent = new Intent(MainActivity.this,ResultActivity.class);
                resultsIntent.putExtras(resultsBundle);
                resultsIntent.putExtras(degreeSymbolBundle);
                resultsIntent.putExtras(cityNameBundle);
                resultsIntent.putExtras(spinStateBundle);
                startActivity(resultsIntent);
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}