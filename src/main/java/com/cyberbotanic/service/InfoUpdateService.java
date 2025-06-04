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

    @Scheduled(fixedRate = 30 * 60 * 1000) // 每30秒執行一次更新植物狀態 (demo)
    public void updatePlantWaterLevels() {
        List<Plant> plants = plantRepository.findAllWithUser();
        for (Plant plant : plants) {
            User owner = plant.getUser();
            if (owner == null) continue;

            if (plant.getGrowthStage() == -1) {
                // 植物已死亡，跳過更新
                //System.out.println("[PlantUpdateService] " + plant.getName() + " in " + owner.getCity() + " is dead");
                continue;
            }

            // 檢查植物是否有水分與營養
            if (plant.getWaterLevel() <= 0 || plant.getNutrientLevel() <= 0) {
                plant.setHealthStatus(plant.getHealthStatus() - 10);
                plant.isDead();
                plantRepository.save(plant);
            }

            int updatedWater = Math.round(plant.getWaterLevel() + (float)owner.getWeatherFactor());
            plant.setWaterLevel(updatedWater);

            int updateNutrient = -10;
            plant.setNutrientLevel(plant.getNutrientLevel() + updateNutrient);
            
            plantRepository.save(plant);

            System.out.println("[PlantUpdateService] " + plant.getName() + " in " + owner.getCity() +
                    " weather factor: " + owner.getWeatherFactor() + ", new water level: " + plant.getWaterLevel() +
                    ", nutrient level: " + plant.getNutrientLevel() + ", health status: " + plant.getHealthStatus());
        }
    }

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000) // 每6小執行一次更新使用者天氣資訊 (demo)
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