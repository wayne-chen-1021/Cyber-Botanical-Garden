package com.cyberbotanic.model;

import java.util.List;
import jakarta.persistence.*;

@Entity 
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants;

    public User() {}

    public User(String userName) {
        this.userName = userName;
        this.plants = new java.util.ArrayList<>(); // Initialize the plants list
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public List<Plant> getPlants() {return plants;}
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
    public void addPlant(Plant plant) {
        if (plants != null) {
            plants.add(plant);
            plant.setUser(this); // Set the user for the plant
        } 
    }

    public void showPlants() {
        if (plants != null && !plants.isEmpty()) {
            for (Plant plant : plants) {
                System.out.println("Plant: " + plant.toString());
            }
        } else {
            System.out.println("No plants found for user: " + userName);
        }
    }
}
