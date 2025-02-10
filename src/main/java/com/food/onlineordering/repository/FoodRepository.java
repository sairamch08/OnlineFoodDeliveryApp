package com.food.onlineordering.repository;

import com.food.onlineordering.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {

    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId")
    List<Food> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    /*@Query("SELECT f FROM FOOD f WHERE f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword") String keyword);*/

/*    @Query("SELECT f FROM Food f " +
            "WHERE lower(f.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(f.category.name) LIKE lower(concat('%', :keyword, '%'))")
    List<Food> searchFood(@Param("keyword") String keyword);*/

    @Query("SELECT f FROM Food f " +
            "WHERE lower(f.name) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(f.category.name) LIKE lower(concat('%', :keyword, '%'))")
    List<Food> searchFood(@Param("keyword") String keyword);

}
