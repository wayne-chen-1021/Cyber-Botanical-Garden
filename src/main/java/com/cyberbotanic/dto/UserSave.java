package com.cyberbotanic.dto;

import jakarta.validation.constraints.NotBlank;

public class UserSave {

    @NotBlank(message = "名稱不可為空")
    private String name;

    public UserSave() {}

    public UserSave(String name) {
        this.name = name;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
