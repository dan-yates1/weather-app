package com.example.weatherapp.model;

import java.io.Serializable;

public class Weather implements Serializable {

    private String time;
    private String temperature;
    private String icon;
    private String wind;
    private String condition;
    private String city;

    public Weather(String time, String temperature, String icon, String wind, String condition, String city) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
        this.wind = wind;
        this.condition = condition;
        this.city = city;
    }

    public Weather() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
