package com.cyberbotanic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlantSave {

    @NotNull(message = "使用者 ID 不可為空")
    private Long userId;

    @NotBlank(message = "植物種類不可為空")
    private String type;

    private String pot;
    private String nickname;

    public PlantSave() {}

    public PlantSave(Long userId, String type, String nickname) {
        this.userId = userId;
        this.type = type;
        this.nickname = nickname;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPot() { return pot; }
    public void setPot(String pot) { this.pot = pot; }
}