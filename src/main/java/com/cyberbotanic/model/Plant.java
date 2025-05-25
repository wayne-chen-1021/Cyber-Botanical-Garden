package com.cyberbotanic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String type; // e.g., "cactus", "rose"
    private String pot = "default";  // 預設樣式
    private String nickname;

    private int growthStage = 0;

    private int water = 0;
    private int nutrition = 0;

    private String status = "healthy";

    private LocalDateTime plantedAt = LocalDateTime.now();

    // Constructors
    public Plant() {}

    public Plant(User user, String type, String nickname) {
        this.user = user;
        this.type = type;
        this.nickname = nickname;
    }

    public Plant(User user, String type) {
        this.user = user;
        this.type = type;
        this.nickname = "Demo";
    }

    // Getters & Setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public int getGrowthStage() { return growthStage; }
    public void setGrowthStage(int growthStage) { this.growthStage = growthStage; }

    public int getWater() { return water; }
    public void setWater(int water) { this.water = water; }

    public int getNutrition() { return nutrition; }
    public void setNutrition(int nutrition) { this.nutrition = nutrition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getPlantedAt() { return plantedAt; }
    public void setPlantedAt(LocalDateTime plantedAt) { this.plantedAt = plantedAt; }

    public String getPot() { return pot; }
    public void setPot(String pot) { this.pot = pot; }

    public void growthStage () {
        if (nutrition >= 100 && water >= 100) {
            if (growthStage < 3) {
                growthStage++;
                water = 0; // 重置水分
                nutrition = 0; // 重置營養
            }
        }
    }
}
