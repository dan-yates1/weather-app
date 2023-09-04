package com.example.weatherapp.adapter;

import com.example.weatherapp.model.Weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.model.WeatherDetails;
import com.example.weatherapp.view.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private ArrayList<WeatherDetails> weatherArrayList;
    private Context context;

    public DetailsAdapter(ArrayList<WeatherDetails> dataList, Context context) {
        this.weatherArrayList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherDetails weather = weatherArrayList.get(position);
        holder.tvCondition.setText(weather.getCondition());
        holder.tvMaxTemp.setText(weather.getMaxTemp() + "°F");
        holder.tvMinTemp.setText(weather.getMinTemp() + "°C");
        holder.tvHour.setText(weather.getMaxTemp());
        Picasso.get().load("http:".concat(weather.getIcon())).into(holder.ivCondition);
        SimpleDateFormat initial = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");
        try {
            Date date = initial.parse(weather.getHour());
            holder.tvHour.setText(output.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMaxTemp, tvMinTemp, tvHour, tvCondition;
        private ImageView ivCondition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaxTemp = itemView.findViewById(R.id.tvTempHigh);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvMinTemp = itemView.findViewById(R.id.tvTempLow);
            ivCondition = itemView.findViewById(R.id.ivCond);
            tvCondition = itemView.findViewById(R.id.tvCondition2);
        }
    }
}

