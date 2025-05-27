package com.cyberbotanic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "plants")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "plant_type")
public abstract class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private final String type;
    private String name;
    private String pot;

    protected int waterLevel;
    protected int nutrientLevel;
    protected int growthStage;
    protected int healthStatus;

    public Plant(String type) {
        this(type, "NULL");
    }

    public Plant(String type, String name) {
        this.type = type;
        this.name = name;
        this.pot = "Original"; // Default pot type
        this.waterLevel = 0; // Default water level
        this.nutrientLevel = 0; // Default nutrient level
        this.growthStage = 0; // Initial growth stage
        this.healthStatus = 100; // Initial health status
    }

    public abstract void isGrown();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public String getType() {return type; }

    public String getName() {return name; }
    public void setName(String name) {this.name = name;}

    public String getPot() {return pot;}
    public void setPot(String pot) {
        if (pot != null && !pot.isEmpty()) {
            this.pot = pot;
        }
    }

    public int getWaterLevel() {return waterLevel;}
    public void setWaterLevel(int waterLevel) {this.waterLevel = waterLevel;}

    public int getNutrientLevel() {return nutrientLevel;}
    public void setNutrientLevel(int nutrientLevel) {this.nutrientLevel = nutrientLevel;} 

    public int getGrowthStage() {return growthStage;}
    public void setGrowthStage(int growthStage) {this.growthStage = growthStage;
    }
    public int getHealthStatus() {return healthStatus;}
    public void setHealthStatus(int healthStatus) {this.healthStatus = healthStatus;}

    @Override
    public String toString() {
        return String.format(
            "{name='%s', type='%s', water=%d, nutrient=%d, health=%d, growthStage=%d, pot='%s'}",
            name, type, waterLevel, nutrientLevel, healthStatus, growthStage, pot
        );
    }

}
