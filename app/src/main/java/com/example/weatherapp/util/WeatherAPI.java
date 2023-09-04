package com.example.weatherapp.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.view.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherAPI {

    private static String API_KEY = "431a3646932a493897d130047230309";
    private Context context;
    private ArrayList<Weather> weatherArrayList;

    public WeatherAPI(Context context) {
        this.context = context;
    }

    public ArrayList<Weather> getWeekForecast(String city) {
        weatherArrayList = new ArrayList<>();
        String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&days=7";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONObject("forecast").getJSONArray("forecastday");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject forecastDay = jsonArray.getJSONObject(i);
                    JSONObject day = forecastDay.getJSONObject("day");
                    String time = forecastDay.getString("date");
                    String temp = day.getString("avgtemp_c");
                    String img = day.getJSONObject("condition").getString("icon");
                    String cond = day.getJSONObject("condition").getString("text");
                    String wind = day.getString("avgvis_km");
                    weatherArrayList.add(new Weather(time, temp, img, wind, cond));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Toast.makeText(context, "Please enter valid city", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);

        return weatherArrayList;
    }

    public Weather getDayForecast(String city) {
        Weather weather = new Weather();
        String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&days=7";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String temperature = response.getJSONObject("current").getString("temp_c");
                int isDay = response.getJSONObject("current").getInt("is_day");
                String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                String time = response.getJSONObject("location").getString("localtime");
                weather.setCondition(condition);
                weather.setTemperature(temperature);
                weather.setIcon(icon);
                weather.setTime(time);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Toast.makeText(context, "Please enter valid city", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);

        return weather;
    }
}
