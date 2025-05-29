package com.cyberbotanic.service;

import com.cyberbotanic.model.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${openweather.api.key}")
    private String API_KEY;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    

    public WeatherResponse getWeatherByLoc(double[] location) {
        if (location == null || location.length != 2) return null;
        double lat = location[0];
        double lon = location[1];
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(json);

            WeatherResponse weather = new WeatherResponse();
            weather.setCity(root.get("name").asText());
            weather.setWeatherMain(root.get("weather").get(0).get("main").asText());
            weather.setWeatherDescription(root.get("weather").get(0).get("description").asText());
            weather.setTemperature(root.get("main").get("temp").asDouble());

            return weather;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class WeatherServiceTestDriver {
    public static void main(String[] args) {
        WeatherService service = new WeatherService();
        WeatherResponse response = service.getWeatherByLoc(new double[]{25.0478, 121.5319}); // Example: Taipei coordinates
        if (response != null) {
            System.out.println("City: " + response.getCity());
            System.out.println("Weather: " + response.getWeatherDescription());
            System.out.println("Temperature: " + response.getTemperature() + "°C");
        } else {
            System.out.println("Failed to fetch weather data.");
        }
    }
}