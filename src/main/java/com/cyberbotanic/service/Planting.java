package com.cyberbotanic.service;

import com.cyberbotanic.dto.PlantSave;
import com.cyberbotanic.model.Plant;
import com.cyberbotanic.model.User;
import com.cyberbotanic.repository.PlantRepository;
import com.cyberbotanic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Planting {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    public String plant(PlantSave plantSave) {
        User user = userRepository.findById(plantSave.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("找不到使用者 ID：" + plantSave.getUserId()));
        Plant plant = new Plant();
        plant.setUser(user);
        plant.setType(plantSave.getType());
        if (plantSave.getNickname() != null && !plantSave.getNickname().isBlank()) {
            plant.setNickname(plantSave.getNickname());
        }
        plant.setPot(
            (plantSave.getPot() != null && !plantSave.getPot().isBlank())
                ? plantSave.getPot()
                : "default"
        );
        plant.setGrowthStage(0);
        plant.setWater(0);
        plant.setNutrition(0);
        plant.setStatus("healthy");
        plantRepository.save(plant);
        return "植物已成功種下！";
    }

    public String water(PlantSave plantSave) {
        Plant plant = plantRepository.findById(plantSave.getPlantId())
            .orElseThrow(() -> new IllegalArgumentException("找不到植物 ID：" + plantSave.getPlantId()));
        plant.setWater(Math.min(plant.getWater() + 20, 100));
        plant.growthStage();
        plantRepository.save(plant);
        return "澆水成功！";
    }

    public String fertilize(PlantSave plantSave) {
        Plant plant = plantRepository.findById(plantSave.getPlantId())
            .orElseThrow(() -> new IllegalArgumentException("找不到植物 ID：" + plantSave.getPlantId()));
        plant.setNutrition(Math.min(plant.getNutrition() + 20, 100));
        plant.growthStage();
        plantRepository.save(plant);
        return "施肥成功！";
    }
}
