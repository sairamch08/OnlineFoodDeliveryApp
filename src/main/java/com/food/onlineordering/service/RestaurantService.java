package com.food.onlineordering.service;

import com.food.onlineordering.model.Restaurant;
import com.food.onlineordering.model.RestuarantDto;
import com.food.onlineordering.model.User;
import com.food.onlineordering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    // for upadeting also we need the same fields insteaad of duplicate object used same object.
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    // only admin can get all the restuarnts
    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyWord);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long restaurantId) throws Exception;

    public RestuarantDto addToFavorites(Long restaurantId, User user) throws Exception;

    // update the restaurant timing by owner
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
