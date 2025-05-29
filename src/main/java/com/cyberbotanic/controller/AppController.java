package com.cyberbotanic.controller;

import com.cyberbotanic.model.Plant;
import com.cyberbotanic.service.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        return ResponseEntity.ok(appService.register(userName));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> body) {
        String userName = (String) body.get("userName");
        Double latitude = body.get("latitude") instanceof Number ? ((Number) body.get("latitude")).doubleValue() : null;
        Double longitude = body.get("longitude") instanceof Number ? ((Number) body.get("longitude")).doubleValue() : null;
        return ResponseEntity.ok(appService.login(userName, latitude, longitude));
    }

    @PostMapping("/{userId}/plant")
    public ResponseEntity<String> plantSeed(
            @PathVariable Long userId,
            @RequestBody ActionRequest request) {
        return ResponseEntity.ok(appService.plantSeed(userId, request));
    }

    @PostMapping("/{userId}/{plantId}/water")
    public ResponseEntity<Map<String, Object>> waterPlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.waterPlant(userId, plantId));
    }

    @PostMapping("/{userId}/{plantId}/fertilize")
    public ResponseEntity<Map<String, Object>> fertilizePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.fertilizePlant(userId, plantId));
    }

    @PostMapping("/{userId}/{plantId}/prune")
    public ResponseEntity<String> prunePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.prunePlant(userId, plantId));
    }

    @PostMapping("/{userId}/{plantId}/rename")
    public ResponseEntity<String> reNamePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId,
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(appService.reNamePlant(userId, plantId, request.get("newName")));
    }

    @GetMapping("/{userId}/plants")
    public ResponseEntity<List<Plant>> getPlants(@PathVariable Long userId) {
        return ResponseEntity.ok(appService.getPlantsByUser(userId));
    }

}


