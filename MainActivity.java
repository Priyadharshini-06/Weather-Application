package com.example.weatherupdate;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etCity, etCountry;
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);
    }

    public void getWeatherDetails(View view) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=f769babef2879c9a173005ce6b6ccab0";
        String apikey = "f769babef2879c9a173005ce6b6ccab0";
        String city = etCity.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Extract main weather data
                    JSONObject mainObject = response.getJSONObject("main");
                    String temperature = mainObject.getString("temp");
                    Double temp = Double.parseDouble(temperature) - 273.15;
                    String humidity = mainObject.getString("humidity");
                    String feelsLike = mainObject.getString("feels_like");
                    String pressure = mainObject.getString("pressure");

                    // Extract coordinates data
                    JSONObject coordObject = response.getJSONObject("coord");
                    String latitude = coordObject.getString("lat");
                    String longitude = coordObject.getString("lon");

                    // Extract wind data
                    JSONObject windObject = response.getJSONObject("wind");
                    String windSpeed = windObject.getString("speed");

                    // Create a formatted string to display all the information
                    String weatherInfo = "Temperature: " + temp.toString().substring(0, 5) + "°C\n" +
                            "Humidity: " + humidity + "%\n" +
                            "Feels Like: " + feelsLike + "°C\n" +
                            "Pressure: " + pressure + " hPa\n" +
                            "Latitude: " + latitude + "\n" +
                            "Longitude: " + longitude + "\n" +
                            "Wind Speed: " + windSpeed + " m/s";

                    tvResult.setText(weatherInfo);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error.toString()", Toast.LENGTH_SHORT).show();

            }

    });
        queue.add(request);
    }
}
