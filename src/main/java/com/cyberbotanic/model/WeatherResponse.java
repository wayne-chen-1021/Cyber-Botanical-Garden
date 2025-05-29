package com.cyberbotanic.model;

public class WeatherResponse {
    private String city;
    private String weatherMain;
    private String weatherDescription; // 天氣現象
    private double temperature;        // 溫度

    // Getters and Setters
    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getWeatherMain() {return weatherMain;}
    public void setWeatherMain(String weatherMain) {this.weatherMain = weatherMain;}

    public String getWeatherDescription() {return weatherDescription;}
    public void setWeatherDescription(String weatherDescription) {this.weatherDescription = weatherDescription;}

    public double getTemperature() {return temperature;}
    public void setTemperature(double temperature) {this.temperature = temperature;}
}