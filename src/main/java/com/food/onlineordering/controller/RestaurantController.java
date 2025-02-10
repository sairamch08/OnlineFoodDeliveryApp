package com.food.onlineordering.controller;

import com.food.onlineordering.model.Restaurant;
import com.food.onlineordering.model.RestuarantDto;
import com.food.onlineordering.model.User;
import com.food.onlineordering.request.CreateRestaurantRequest;
import com.food.onlineordering.service.RestaurantService;
import com.food.onlineordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants") // for Admin this "/api/admin/restaurants" is used
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
                                                             @RequestParam String keyWord) throws Exception {


        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyWord);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt) throws Exception {


        List<Restaurant> restaurant = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long id) throws Exception {


        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestuarantDto> addToFavorites(@RequestHeader("Authorization") String jwt,
                                                        @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwtToken(jwt);


        RestuarantDto restuarantDto = restaurantService.addToFavorites(id, user);

        return new ResponseEntity<>(restuarantDto, HttpStatus.OK);

    }
}
