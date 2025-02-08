package com.food.onlineordering.repository;

import com.food.onlineordering.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String userName);
}
