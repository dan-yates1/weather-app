package com.example.weatherapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.DetailsAdapter;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.model.WeatherDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private TextView tvDay, tvAvgTemp, tvCondition, tvRain, tvHumidity, tvWind;
    private ImageView ivIcon;
    private RecyclerView rvHourly;
    private ArrayList<WeatherDetails> weatherDetailsArrayList;
    private DetailsAdapter detailsAdapter;
    private Weather current;
    private RelativeLayout rlHome;
    private ProgressBar pbLoading;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(view -> {
            finish();
        });

        tvDay = findViewById(R.id.tvDay);
        tvAvgTemp = findViewById(R.id.tvAvgTemp);
        tvCondition = findViewById(R.id.tvCondition);
        tvRain = findViewById(R.id.tvRain);
        tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        ivIcon = findViewById(R.id.ivCondition2);
        rlHome = findViewById(R.id.rlHome);
        pbLoading = findViewById(R.id.pbLoading);

        current = new Weather();

        Weather weather = (Weather) getIntent().getSerializableExtra("WEATHER_DATA");

        if (weather != null) {
            SimpleDateFormat initial = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("EEEE");
            current = weather;
            try {
                Date date = initial.parse(weather.getTime());
                tvDay.setText(output.format(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            tvAvgTemp.setText(weather.getTemperature() + "°");
            tvWind.setText(weather.getWind() + "km/h");
            tvCondition.setText(weather.getCondition());
            Picasso.get().load("http:".concat(weather.getIcon())).into(ivIcon);
        }

        rvHourly = findViewById(R.id.rvHourly);
        rvHourly.setLayoutManager(new LinearLayoutManager(this));
        weatherDetailsArrayList = new ArrayList<>();
        detailsAdapter = new DetailsAdapter(weatherDetailsArrayList, this);
        rvHourly.setAdapter(detailsAdapter);

        getHourlyForecast(weather.getCity());
    }

    private void getHourlyForecast(String city) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=431a3646932a493897d130047230309&q=" + city + "&dt=" + current.getTime();
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            weatherDetailsArrayList.clear();
            try {
                pbLoading.setVisibility(View.VISIBLE);
                rlHome.setVisibility(View.GONE);
                JSONObject forecastObject = response.getJSONObject("forecast");
                JSONObject forecastDayObject = forecastObject.getJSONArray("forecastday").getJSONObject(0);
                JSONArray hourArray = forecastDayObject.getJSONArray("hour");

                for (int i = 0; i < hourArray.length(); i++) {
                    JSONObject hourObj = hourArray.getJSONObject(i);
                    String maxTemp = hourObj.getString("temp_f");
                    String minTemp = hourObj.getString("temp_c");
                    String condition = hourObj.getJSONObject("condition").getString("text");
                    String icon = hourObj.getJSONObject("condition").getString("icon");
                    String hour = hourObj.getString("time");
                    String chanceOfRain = hourObj.getString("chance_of_rain");
                    tvRain.setText(chanceOfRain + "%");
                    String humidity = hourObj.getString("humidity");
                    tvHumidity.setText(humidity + "%");
                    weatherDetailsArrayList.add(new WeatherDetails(maxTemp, minTemp, condition, icon, hour));
                }
                detailsAdapter.notifyDataSetChanged();
                pbLoading.setVisibility(View.GONE);
                rlHome.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Toast.makeText(DetailsActivity.this, "Please enter valid city", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }
}