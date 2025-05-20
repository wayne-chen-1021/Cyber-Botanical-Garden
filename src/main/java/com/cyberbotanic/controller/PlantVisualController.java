package com.cyberbotanic.controller;

import com.cyberbotanic.data.PlantVisuals;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plant-visuals")
public class PlantVisualController {

    /**
     * 取得植物 ASCII 圖（植物 + 盆栽合併圖）
     * 範例呼叫：GET /api/plant-visuals/ascii?type=cactus&stage=grown&pot=default
     */
    @GetMapping("/ascii")
    public ResponseEntity<String> getPlantAscii(
            @RequestParam String type,
            @RequestParam String stage,
            @RequestParam(defaultValue = "default") String pot
    ) {
        String ascii = PlantVisuals.render(type, stage, pot);
        return ResponseEntity.ok(ascii);
    }
}
