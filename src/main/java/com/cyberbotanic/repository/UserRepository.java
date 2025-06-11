package com.cyberbotanic.repository;

import com.cyberbotanic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    List<User> findByCity(String city);

}