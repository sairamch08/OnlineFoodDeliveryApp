package com.food.onlineordering.service;


import com.food.onlineordering.model.Category;
import com.food.onlineordering.model.Food;
import com.food.onlineordering.model.Restaurant;
import com.food.onlineordering.repository.FoodRepository;
import com.food.onlineordering.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImplementation implements FoodService {

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest createFoodRequest, Category category,
                           Restaurant restaurant) {
        Food food = new Food();
        food.setCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(createFoodRequest.getDescription());
        food.setImages(createFoodRequest.getImages());
        food.setName(createFoodRequest.getName());
        food.setPrice(createFoodRequest.getPrice());
        food.setIngredientsItem(createFoodRequest.getIngredients());
        food.setSeasonal(createFoodRequest.isSeasional());
        food.setVegetarian(createFoodRequest.isVegetarin());


        Food savedFood = foodRepository.save(food);

        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restauantId,
                                         boolean isVegitarin,
                                         boolean isNonveg,
                                         boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restauantId);

        if (isVegitarin) {
            foods = filterByVegetarian(foods, isVegitarin);
        }
        if (isNonveg) {
            foods = filterByNonVeg(foods, isNonveg);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {

        return foods.stream().filter(food -> {
            if (food.getCategory() != null) {
                return food.getCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {

        return foods.stream().filter(food ->
                        food.isVegetarian() == food.isSeasonal()) // , true manes seasonal
                .collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food ->
                        food.isVegetarian() == food.isVegetarian() == false) // false means non veg , true manes veg
                .collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegitarin) {
        return foods.stream().filter(food ->
                        food.isVegetarian() == isVegitarin)
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("Food not exits...");
        }

        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);

        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
