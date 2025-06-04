package com.cyberbotanic.model;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String city;
    private double latitude, longitude;
    private double weatherFactor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

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

    public double[] getLocation() {
        return new double[]{latitude, longitude};
    }
    public void setLocation(double[] location) {
        if (location != null && location.length == 2) {
            latitude = location[0];
            longitude = location[1];
        }
    }

    public String getCity () {return city;}
    public void setCity (String city) {this.city = city;}

    public double getWeatherFactor() {return weatherFactor;}
    public void setWeatherFactor(double weatherFactor) {this.weatherFactor = weatherFactor;}
    
    public List<User> getFriends() {return friends;}
    public void addFriend(User friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.getFriends().add(this); // 雙向綁定
        }
    }
    public void removeFriend(User friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            friend.getFriends().remove(this); // 雙向解除
        }
    }


}
