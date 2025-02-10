package com.food.onlineordering.service;

import com.food.onlineordering.model.Address;
import com.food.onlineordering.model.Restaurant;
import com.food.onlineordering.model.RestuarantDto;
import com.food.onlineordering.model.User;
import com.food.onlineordering.repository.AddressRepository;
import com.food.onlineordering.repository.RestaurantRepository;
import com.food.onlineordering.repository.UserRepository;
import com.food.onlineordering.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImplementations implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();

        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());

        }

        if (restaurant.getDescription() != null) {
            restaurant.setCuisineType(updateRestaurant.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setCuisineType(updateRestaurant.getName());
        }
// can update all the details but now here we have updated only few details


        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
// here we are not adding the check to see ,
// if restaurant is not present then findRestaurantById() will throw an exception
        restaurantRepository.delete(restaurant);


    }

    @Override
    public List<Restaurant> getAllRestaurant() {

        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyWord) {
        return restaurantRepository.findBySearchQuery(keyWord);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);

        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + restaurantId);

        }
// this will get the restaurant
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(restaurantId);

        if (restaurant == null) {
            throw new Exception("Resturant not found with owner id " + restaurantId);
        }
        return restaurant;
    }

    @Override
    public RestuarantDto addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        RestuarantDto restuarantDto = new RestuarantDto();
        restuarantDto.setDescription(restaurant.getDescription());
        restuarantDto.setImages(restaurant.getImages());
        restuarantDto.setTitle(restaurant.getName());

        restuarantDto.setId(restaurantId);
        // if a user has a favorite restaurant list then remove that and add recently added list
        if (user.getFavorites().contains(restuarantDto)) {
            user.getFavorites().remove(restuarantDto);
        } else {
            user.getFavorites().add(restuarantDto);
        }
        userRepository.save(user);


        return restuarantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
