package com.cyberbotanic.service;

import com.cyberbotanic.repository.*;
import com.cyberbotanic.model.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.Map;

@Service
public class AppService {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    private WeatherService weatherService;

    public AppService(UserRepository userRepository,
                  PlantRepository plantRepository,
                  WeatherService weatherService) {
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
        this.weatherService = weatherService;
    }
    /* 註冊 */
    public Map<String, Object> register(String userName) {
        Optional<User> existing = userRepository.findByUserName(userName);
        if (existing.isPresent()) {
            return Map.of("userId", existing.get().getId(), "message", "使用者已存在");
        }
        User user = new User(userName);
        userRepository.save(user);
        return Map.of("userId", user.getId(), "message", "註冊成功");
    }
    /* 登入 */
    public Map<String, Object> login(String userName, Double latitude, Double longitude) {
        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (latitude != null && longitude != null) {
                user.setLocation(new double[]{latitude, longitude});
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
            }
            System.out.println("[LoginService]使用者登入：" + user.getUserName() + "，位置：" + 
                                user.getLocation()[0] + ", " + user.getLocation()[1]);
            return Map.of("userId", user.getId(), "message", "登入成功");
        } else {
            return Map.of("userId", -1, "message", "使用者不存在");
        }
    }
    /* 種植 */
    public String plantSeed(Long userId, ActionRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return "使用者不存在";

        User user = userOpt.get();

        Plant plant;
        // 模擬依照前端傳入的類型來建立對應植物（目前只有 Weed）
        if ("weed".equalsIgnoreCase(request.getPlantType())) {
            plant = new Weed();
        } else {
            return "未知植物類型：" + request.getPlantType();
        }

        user.addPlant(plant);
        userRepository.save(user);

        return "種植成功：" + plant.getType();
    }
    /* 澆水 */
    public Map<String, Object> waterPlant(Long userId, Long plantId) {
        Optional<Plant> plantOpt = plantRepository.findById(plantId);
        if (plantOpt.isEmpty()) return Map.of("plantId", -1, "message", "該植物不存在");

        Plant plant = plantOpt.get();
        if (!plant.getUser().getId().equals(userId)) {
            return Map.of("plantId", plantId, "message", "這不是你的植物");
        }

        // 檢查植物是否已死亡
        if (plant.getGrowthStage() == -1) {
            return Map.of("plantId", plantId, "message", "這個植物已經死亡，無法澆水");
        }
        plant.setWaterLevel(plant.getWaterLevel() + 50);
        plant.isGrown(); // 檢查植物是否成長
        plantRepository.save(plant);

        return Map.of("plantId", plantId, "message", "澆水成功", "grownthStage", plant.getGrowthStage());
    }
    /* 施肥 */
    public Map<String, Object> fertilizePlant(Long userId, Long plantId) {
        Optional<Plant> plantOpt = plantRepository.findById(plantId);
        if (plantOpt.isEmpty()) return Map.of("plantId", -1, "message", "該植物不存在");

        Plant plant = plantOpt.get();
        if (!plant.getUser().getId().equals(userId)) {
            return Map.of("plantId", plantId, "message", "這不是你的植物");
        }

        // 檢查植物是否已死亡
        if (plant.getGrowthStage() == -1) {
            return Map.of("plantId", plantId, "message", "這個植物已經死亡，無法施肥");
        }
        plant.setNutrientLevel(plant.getNutrientLevel() + 50);
        plant.isGrown(); // 檢查植物是否成長
        plantRepository.save(plant);

        return Map.of("plantId", plantId, "message", "施肥成功", "growthStage", plant.getGrowthStage());
    }
    /* 修剪(尚未實作) */
    public String prunePlant(Long userId, Long plantId) {
        Optional<Plant> plantOpt = plantRepository.findById(plantId);
        if (plantOpt.isEmpty()) return "植物不存在";

        Plant plant = plantOpt.get();
        if (!plant.getUser().getId().equals(userId)) {
            return "這不是你的植物";
        }

        //plant.setPruned(true);
        //plantRepository.save(plant);

        return "修剪成功";
    }
    /* 重新命名 */
    public String reNamePlant(Long userId, Long plantId, String newName) {
        Optional<Plant> plantOpt = plantRepository.findById(plantId);
        if (plantOpt.isEmpty()) return "植物不存在";

        Plant plant = plantOpt.get();
        if (!plant.getUser().getId().equals(userId)) {
            return "這不是你的植物";
        }

        plant.setName(newName);
        plantRepository.save(plant);

        return "重新命名成功：" + newName;
    }
    /* 可獲取使用者已種植之清單 */
    public List<Plant> getPlantsByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getPlants).orElse(List.of());
    }

}


