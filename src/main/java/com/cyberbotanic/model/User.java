package com.cyberbotanic.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Plant> plants;

    // Constructors
    public User() {}

    public User(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Plant> getPlants() { return plants; }
    public void setPlants(List<Plant> plants) { this.plants = plants; }
}
