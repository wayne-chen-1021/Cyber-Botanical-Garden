package com.cyberbotanic.service;

import com.cyberbotanic.model.Plant;
import com.cyberbotanic.model.User;
import com.cyberbotanic.repository.*;
import com.cyberbotanic.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoUpdateService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @Scheduled(fixedRate = 15 * 1000) // 每15秒執行一次更新植物狀態 (demo)
    public void updatePlantWaterLevels() {
        List<Plant> plants = plantRepository.findAllWithUser();
        for (Plant plant : plants) {
            User owner = plant.getUser();
            if (owner == null) continue;

            int updatedWater = Math.max(0, Math.round(plant.getWaterLevel() + (float)owner.getWeatherFactor()));
            plant.setWaterLevel(updatedWater);
            plantRepository.save(plant);

            System.out.println("[PlantUpdateService] " + plant.getName() + " in " + owner.getCity() +
                    " affected by weather factor: " + owner.getWeatherFactor() + ", new water level: " + plant.getWaterLevel());
        }
    }

    @Scheduled(fixedRate = 60 * 1000) // 每1分鐘執行一次更新使用者天氣資訊 (demo)
    public void updateUserWeather() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getLocation() == null || user.getLocation().length < 2) continue;

            WeatherResponse weather = weatherService.getWeatherByLoc(user.getLocation());
            double factor = switch (weather.getWeatherMain()) {
                case "Clear" -> -5.0;
                case "Clouds" -> -2.5;
                case "Drizzle", "Rain" -> +1.5;
                case "Thunderstorm" -> +3.0;
                default -> -3.0;
            };
            user.setWeatherFactor(factor);
            user.setCity(weather.getCity());
            userRepository.save(user);

            System.out.println("[UserUpdateService] Updated weather for user: " + user.getUserName() +
                    ", city: " + user.getCity() + ", factor: " + factor);
        }
    }
}