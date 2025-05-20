package com.cyberbotanic.repository;

import com.cyberbotanic.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByUserId(Long userId); // 根據使用者查詢植物清單
}
