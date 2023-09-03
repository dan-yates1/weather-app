package com.example.weatherapp.view;

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

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.model.Weather;
import com.google.android.material.textfield.TextInputEditText;

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
        city = getCity(location.getLongitude(), location.getLatitude());

        getWeatherData(city);

        ivSearch.setOnClickListener(view -> {
            String city = etCity.getText().toString();
            if (city.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            } else {
                tvCity.setText(city);
                getWeatherData(city);
            }
        });
    }

    private void getWeatherData(String city) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&days=7&aqi=no&alerts=no";

    }

    private String getCity(double longitude, double latitude) {
        String city = "Not found!";
        Geocoder geo = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
           List<Address> addressList = geo.getFromLocation(longitude, latitude, 10);
           for (Address address : addressList) {
               if (address != null) {
                   String s = address.getLocality();
                   if (city != null && !city.equals("")) {
                        city = s;
                   } else {
                       Log.d("TAG", "CITY NOT FOUND");
                       Toast.makeText(this, "User city not found", Toast.LENGTH_SHORT).show();
                   }
               }
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return city;
    }
}