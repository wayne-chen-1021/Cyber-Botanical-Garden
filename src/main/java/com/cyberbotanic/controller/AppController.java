package com.cyberbotanic.controller;

import com.cyberbotanic.data.PlantVisuals;
import com.cyberbotanic.dto.PlantSave;
import com.cyberbotanic.dto.UserSave;
import com.cyberbotanic.model.Plant;
import com.cyberbotanic.model.User;
import com.cyberbotanic.repository.PlantRepository;
import com.cyberbotanic.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app")
public class AppController {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    public AppController(UserRepository userRepository, PlantRepository plantRepository) {
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
    }

    /**
     * 建立新使用者
     */
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserSave userSave) {
        User user = new User(userSave.getName());
        userRepository.save(user);
        return ResponseEntity.ok("使用者已建立成功");
    }

    @PostMapping("/plants")
    public ResponseEntity<String> createPlant(@RequestBody @Valid PlantSave plantSave) {
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
        plant.setWater(100);
        plant.setNutrition(100);
        plant.setStatus("healthy");

        plantRepository.save(plant);
        return ResponseEntity.ok("植物已成功種下！");
    }

    @GetMapping("/plant-image/{plantId}")
    public ResponseEntity<String> getPlantAsciiFromDb(@PathVariable Long plantId) {
        try {
            Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("找不到植物 ID：" + plantId));

            String ascii = PlantVisuals.render(
                plant.getType(),
                getStageName(plant.getGrowthStage()),
                plant.getPot()
            );
            return ResponseEntity.ok(ascii);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 回傳 400 錯誤與訊息
        }
    }


    // 工具方法：將數值階段轉換為名稱
    private String getStageName(int stage) {
        return switch (stage) {
            case 0 -> "seed";
            case 1 -> "sprout";
            case 2 -> "grown";
            case 3 -> "bloom";
            default -> "seed";
        };
    }

}
