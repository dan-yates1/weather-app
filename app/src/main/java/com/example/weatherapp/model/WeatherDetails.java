package com.example.weatherapp.model;

public class WeatherDetails {

    private String maxTemp, minTemp, condition, icon, hour;

    public WeatherDetails(String maxTemp, String minTemp, String condition, String icon, String hour) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.icon = icon;
        this.hour = hour;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
