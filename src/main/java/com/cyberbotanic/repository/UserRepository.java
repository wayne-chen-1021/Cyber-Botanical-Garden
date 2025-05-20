package com.cyberbotanic.repository;

import com.cyberbotanic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
