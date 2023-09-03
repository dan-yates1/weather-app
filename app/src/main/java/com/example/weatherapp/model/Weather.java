package com.example.weatherapp.model;

public class Weather {

    private String time;
    private String temperature;
    private String icon;
    private String wind;
    private String condition;

    public Weather(String time, String temperature, String icon, String wind, String condition) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
        this.wind = wind;
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
