package com.cyberbotanic.service;

public class ActionRequest {
    private Long userId;
    private String plantType;  // 用於入土動作
    private Long plantId;      // 用於後續動作

    // Getters and Setters
    public Long getUserId() {return userId; }
    public void setUserId(Long userId) {this.userId = userId; }

    public String getPlantType() {return plantType; }
    public void setPlantType(String plantType) {this.plantType = plantType; }

    public Long getPlantId() {return plantId; }
    public void setPlantId(Long plantId) {this.plantId = plantId;}
}

