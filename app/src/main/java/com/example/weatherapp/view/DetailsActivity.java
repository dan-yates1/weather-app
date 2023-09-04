package com.example.weatherapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.Weather;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private TextView tvDay, tvAvgTemp, tvCondition, tvRain, tvHumidity, tvWind;
    private ImageView ivIcon;
    private RecyclerView rvHourly;

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

        Weather weather = (Weather) getIntent().getSerializableExtra("WEATHER_DATA");

        if (weather != null) {
            SimpleDateFormat initial = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("EEEE");
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
    }
}