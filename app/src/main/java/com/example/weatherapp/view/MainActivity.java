package com.example.weatherapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.model.Weather;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout rlHome;
    private ProgressBar pbLoading;
    private TextView tvCity, tvTemperature, tvCondition;
    private TextInputEditText etCity;
    private ImageView ivBg, ivIcon, ivSearch;
    private RecyclerView rvWeather;
    private ArrayList<Weather> weatherArrayList;
    private WeatherAdapter weatherAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);
        rlHome = findViewById(R.id.rlHome);
        pbLoading = findViewById(R.id.pbLoading);
        tvCity = findViewById(R.id.tvCity);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvCondition = findViewById(R.id.tvCondition);
        etCity = findViewById(R.id.etCity);
        ivBg = findViewById(R.id.ivBg);
        ivIcon = findViewById(R.id.ivIcon);
        ivSearch = findViewById(R.id.ivSearch);
        rvWeather = findViewById(R.id.rvWeather);
        weatherArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherArrayList);
        rvWeather.setAdapter(weatherAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //city = getCity(location.getLongitude(), location.getLatitude());
        if (location != null){city = getCity(location.getLongitude(),location.getLatitude());
            getWeatherData(city);
        } else {
            city = "London";
            getWeatherData(city);
        }
        //tvCity.setText(city);

        ivSearch.setOnClickListener(view -> {
            String city = etCity.getText().toString();
            if (city.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            } else {
                tvCity.setText(city);
                pbLoading.setVisibility(View.VISIBLE);
                rlHome.setVisibility(View.GONE);
                getWeatherData(city);
                pbLoading.setVisibility(View.GONE);
                rlHome.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if  (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enable permissions", Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }

    private void getWeatherData(String city) {
        //String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&days=7&aqi=no&alerts=no";
        String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&days=7";
        tvCity.setText(city);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            pbLoading.setVisibility(View.GONE);
            rlHome.setVisibility(View.VISIBLE);
            weatherArrayList.clear();

            try {
                String temperature = response.getJSONObject("current").getString("temp_c");
                tvTemperature.setText(temperature + "°");
                int isDay = response.getJSONObject("current").getInt("is_day");
                if (isDay == 1) {
                    Picasso.get().load("https://img.freepik.com/free-photo/beautiful-shining-stars-night-sky_181624-622.jpg?w=1480&t=st=1693777503~exp=1693778103~hmac=5406f56fd41143bf34a36e932c07ca3a1f673e8f3dbe1dd1644da9af971e793e").into(ivBg);
                } else {
                    Picasso.get().load("https://static.vecteezy.com/system/resources/previews/027/224/534/non_2x/the-breathtaking-view-in-nakhornsrithammarat-thailand-as-seen-from-the-point-of-view-of-a-tourist-with-the-hill-being-surrounded-by-fog-and-a-golden-sky-in-the-background-free-photo.jpg").into(ivBg);
                }
                String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                tvCondition.setText(condition);
                String icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                Picasso.get().load("http:".concat(icon)).into(ivIcon);

                JSONObject forecastObject = response.getJSONObject("forecast");
                JSONObject forecastDayObject = forecastObject.getJSONArray("forecastday").getJSONObject(0);
                JSONArray forecastArray = forecastObject.getJSONArray("forecastday");
                JSONArray hourArray = forecastDayObject.getJSONArray("hour");
                JSONObject dayObject = forecastArray.getJSONObject(0);
                JSONObject day2Object = dayObject.getJSONObject("day");

                //String time = forecastDayObject.getString("date");

                /*for (int i = 0; i < hourArray.length(); i++) {
                    JSONObject hourObject = hourArray.getJSONObject(i);
                    String time = hourObject.getString("time");
                    String temp = hourObject.getString("temp_c");
                    String img = hourObject.getJSONObject("condition").getString("icon");
                    String cond = hourObject.getJSONObject("condition").getString("text");
                    String wind = hourObject.getString("wind_kph");
                    weatherArrayList.add(new Weather(time, temp, img, wind, cond));
                }*/

                for (int i = 0; i < forecastArray.length(); i++) {
                    dayObject = forecastArray.getJSONObject(i);
                    day2Object = dayObject.getJSONObject("day");
                    String time = dayObject.getString("date");
                    String temp = day2Object.getString("avgtemp_c");
                    String img = day2Object.getJSONObject("condition").getString("icon");
                    String cond = day2Object.getJSONObject("condition").getString("text");
                    String wind = day2Object.getString("avgvis_km");
                    weatherArrayList.add(new Weather(time, temp, img, wind, cond));
                }
                weatherAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Toast.makeText(MainActivity.this, "Please enter valid city", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    private String getCity(double longitude, double latitude) {
        String cityName = "City not found";
        Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
           List<Address> addressList = geo.getFromLocation(latitude, longitude, 10);
           for (Address address : addressList) {
               if (address != null) {
                   String city = address.getLocality();
                   if (city != null && !city.equals("")) {
                        cityName = city;
                   } else {
                       Log.d("TAG", "CITY NOT FOUND");
                       //Toast.makeText(this, "User city not found", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cityName;
    }
}