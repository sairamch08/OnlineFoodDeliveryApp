package com.food.onlineordering.repository;

import com.food.onlineordering.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
// this is by search criteria
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%'))" +
            " OR lower(r.cuisineType) LIke lower(concat('%',:query,'%')")
    List<Restaurant> findBySearchQuery(String query);

    // this is by ownerID
    Restaurant findByOwnerId(Long userID);


}
