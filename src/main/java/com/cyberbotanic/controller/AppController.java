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
    /* 註冊 */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        return ResponseEntity.ok(appService.register(userName));
    }
    /* 登入 */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> body) {
        String userName = (String) body.get("userName");
        Double latitude = body.get("latitude") instanceof Number ? ((Number) body.get("latitude")).doubleValue() : null;
        Double longitude = body.get("longitude") instanceof Number ? ((Number) body.get("longitude")).doubleValue() : null;
        return ResponseEntity.ok(appService.login(userName, latitude, longitude));
    }
    /* 種植 */
    @PostMapping("/{userId}/plant")
    public ResponseEntity<String> plantSeed(
            @PathVariable Long userId,
            @RequestBody ActionRequest request) {
        return ResponseEntity.ok(appService.plantSeed(userId, request));
    }
    /* 澆水 */
    @PostMapping("/{userId}/{plantId}/water")
    public ResponseEntity<Map<String, Object>> waterPlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.waterPlant(userId, plantId));
    }
    /* 施肥 */
    @PostMapping("/{userId}/{plantId}/fertilize")
    public ResponseEntity<Map<String, Object>> fertilizePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.fertilizePlant(userId, plantId));
    }
    /* 修剪(未實作) */
    @PostMapping("/{userId}/{plantId}/prune")
    public ResponseEntity<String> prunePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.prunePlant(userId, plantId));
    }
    /* 植物重新命名 */
    @PostMapping("/{userId}/{plantId}/rename")
    public ResponseEntity<String> reNamePlant(
            @PathVariable Long userId,
            @PathVariable Long plantId,
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(appService.reNamePlant(userId, plantId, request.get("newName")));
    }
    /* 獲取使用者植物清單 */
    @GetMapping("/{userId}/plants")
    public ResponseEntity<List<Plant>> getPlants(@PathVariable Long userId) {
        return ResponseEntity.ok(appService.getPlantsByUser(userId));
    }
    /* 獲取使用者好友清單 */
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<Map<String, Object>>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(appService.getFriendsByUserId(userId));
    }
    /* 加入好友 */
    @PostMapping("/{userId}/add-friend")
    public ResponseEntity<String> addFriend(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {

        String friendName = request.get("friendName");
        String result = appService.addFriend(userId, friendName);
        return ResponseEntity.ok(result);
    }
    /* 換盆（目前實作為好友互動） */
    @PostMapping("/{userId}/friend-action/{plantId}")
    public ResponseEntity<Map<String, Object>> changePot(
            @PathVariable Long userId,
            @PathVariable Long plantId) {
        return ResponseEntity.ok(appService.changePot(userId, plantId));
    }
    
}


