package com.cyberbotanic.controller;

import com.cyberbotanic.data.PlantVisuals;
import com.cyberbotanic.dto.PlantSave;
import com.cyberbotanic.dto.UserSave;
import com.cyberbotanic.model.Plant;
import com.cyberbotanic.model.User;
import com.cyberbotanic.repository.PlantRepository;
import com.cyberbotanic.repository.UserRepository;
import com.cyberbotanic.service.Planting;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/app")
public class AppController {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final Planting plantingService;

    public AppController(UserRepository userRepository, PlantRepository plantRepository, Planting plantingService) {
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
        this.plantingService = plantingService;
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

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid UserSave userSave) {
        String name = userSave.getName();

        Optional<User> userOpt = userRepository.findByName(name);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get().getId());
        } else {
            return ResponseEntity
                .badRequest()
                .body("找不到此使用者，請先註冊。");
        }
    }

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserSave userSave) {
        String name = userSave.getName();

        if (userRepository.findByName(name).isPresent()) {
            return ResponseEntity.badRequest().body("此名稱已被使用，請選擇其他名稱");
        }

        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return ResponseEntity.ok("使用者註冊成功");
    }


    @PostMapping("/plants/action/{action}")
    public ResponseEntity<String> plantAction(
        @PathVariable String action,
        @RequestBody @Valid PlantSave plantSave
    ) {
        try {
            return switch (action) {
                case "plant" -> ResponseEntity.ok(plantingService.plant(plantSave));
                case "water" -> ResponseEntity.ok(plantingService.water(plantSave));
                case "fertilize" -> ResponseEntity.ok(plantingService.fertilize(plantSave));
                default -> ResponseEntity.badRequest().body("未知的 action：" + action);
            };
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("/plants/user/{userId}")
    public ResponseEntity<?> getPlantsByUserId(@PathVariable Long userId) {
        List<Plant> plants = plantRepository.findByUserId(userId);
        if (plants.isEmpty()) {
            return ResponseEntity.ok("此使用者尚未種植任何植物。");
        }
        return ResponseEntity.ok(plants);
    }

    // 工具方法：將數值階段轉換為名稱
    private String getStageName(int stage) {
        return switch (stage) {
            case 0 -> "seed";
            case 1 -> "sprout";
            case 2 -> "grown";
            case 3 -> "blooming";
            case 4 -> "bloomed";
            default -> "seed";
        };
    }

}
