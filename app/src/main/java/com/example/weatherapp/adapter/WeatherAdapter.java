package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.Weather;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Weather> weatherArrayList;

    public WeatherAdapter(Context context, ArrayList<Weather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Weather weather = weatherArrayList.get(position);
        Picasso.get().load("http:".concat(weather.getIcon())).into(holder.ivCondition);
        holder.tvTemperature.setText(weather.getTemperature() + "°");
        holder.tvWind.setText(weather.getWind() + "km/h");
        SimpleDateFormat initial = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            Date date = initial.parse(weather.getTime());
            holder.tvTime.setText(output.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTemperature, tvTime, tvWind;
        private ImageView ivCondition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTemperature = itemView.findViewById(R.id.tvTemp);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvWind = itemView.findViewById(R.id.tvWind);
            ivCondition = itemView.findViewById(R.id.ivCondition);
        }
    }
}
