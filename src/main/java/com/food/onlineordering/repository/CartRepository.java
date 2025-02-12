package com.food.onlineordering.repository;

import com.food.onlineordering.model.Cart;
import com.food.onlineordering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByCustomerId(Long userId);

}
