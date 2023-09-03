package com.example.weatherapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout rlHome;
    private ProgressBar pbLoading;
    private TextView tvCity, tvTemperature, tvCondition;
    private TextInputEditText etCity;
    private ImageView ivBg, ivIcon, ivSearch;
    private RecyclerView rvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}