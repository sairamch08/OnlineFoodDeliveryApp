package com.food.onlineordering.repository;

import com.food.onlineordering.model.Cart;
import com.food.onlineordering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //public Cart findByCustomerId(Long userId);
}
