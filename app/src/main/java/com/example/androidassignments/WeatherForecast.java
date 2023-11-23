package com.example.androidassignments;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected static final String urlBegin = "https://api.openweathermap.org/data/2.5/weather?q=";
    protected static String urlStartCity = "toronto";
    protected static final String urlEnding = ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    ImageView weatherPicture;
    TextView currentTemp;
    TextView minimumTemp;
    TextView maximumTemp;
    ProgressBar progressBar;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        weatherPicture = findViewById(R.id.weatherIcon);
        currentTemp = findViewById(R.id.currentTemp);
        minimumTemp = findViewById(R.id.minTemp);
        maximumTemp = findViewById(R.id.maxTemp);
        progressBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
        urlStartCity= choice;
        ForecastQuery f = new ForecastQuery();
        f.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min, max, curr;
        Bitmap weatherIcon;

        @Override
        protected String doInBackground(String... strings) {
            try {
                Log.i(ACTIVITY_NAME, "trying ForecastQuery");
                URL url = new URL(urlBegin + urlStartCity + urlEnding);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        Log.i(ACTIVITY_NAME, "while looping until end of document");
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            //Look for temperature
                            if (parser.getName().equals("temperature")) {
                                Log.i(ACTIVITY_NAME, "Looking for temperatures");
                                curr = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")){
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for: " + fileName);
                                File file = getBaseContext().getFileStreamPath(fileName);
                                if (file.exists()) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found successfully");
                                    weatherIcon = BitmapFactory.decodeStream(fis);
                                    String iconURL = "https://openweathermap.org/img/w/" + fileName;
                                    weatherIcon = getImage(new URL(iconURL));
                                } else {
                                    String iconURL = "https://openweathermap.org/img/w" + fileName;
                                    weatherIcon = getImage(new URL(iconURL));
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    weatherIcon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }

                        }
                        parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public Bitmap getImage (URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else {
                    return null;
                }

            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            weatherPicture.setImageBitmap(weatherIcon);
            currentTemp.setText(curr + "C\u00b0");
            minimumTemp.setText(min + "C\u00b0");
            maximumTemp.setText(max + "C\u00b0");

        }
    }
}