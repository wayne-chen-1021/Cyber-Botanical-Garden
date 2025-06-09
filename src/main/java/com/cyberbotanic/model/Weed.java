package com.cyberbotanic.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("weed")
public class Weed extends Plant {

    public Weed() {
        super("Weed");
    }
    public Weed(String name) {
        super("Weed", name);
    }

    @Override
    public void isGrown() {
        if (this.getGrowthStage() == -1 || this.getGrowthStage() >= 5) {
            return; // Already dead or fully grown
        }
        if (this.waterLevel == 100 && this.nutrientLevel == 100 && this.growthStage < 5) {
            this.setGrowthStage(getGrowthStage() + 1);
            this.setWaterLevel(70);
            this.setNutrientLevel(70);
        }
    }

    @Override
    public void isDead() {
        if (this.healthStatus <= 0) {
            this.setHealthStatus(0);
            this.setGrowthStage(-1);
            this.setWaterLevel(0);
            this.setNutrientLevel(0);
        }
    }

}
