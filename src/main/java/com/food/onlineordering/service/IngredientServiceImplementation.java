package com.food.onlineordering.service;

import com.food.onlineordering.model.IngredientCategory;
import com.food.onlineordering.model.IngredientsItem;
import com.food.onlineordering.model.Restaurant;
import com.food.onlineordering.repository.IngredientCategoryRepository;
import com.food.onlineordering.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImplementation implements IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;


    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> optional = ingredientCategoryRepository.findById(id);

        if (optional.isEmpty()) {
            throw new Exception("Ingredient category not found..");
        }

        return optional.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id); // if not found this will throw exception

        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {


        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = findIngredientCategoryById(categoryId);
        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredientsItems().add(ingredient);

        return ingredient;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optional = ingredientItemRepository.findById(id);

        if (optional.isEmpty()) {
            throw new Exception("Ingredient not found");
        }

        IngredientsItem ingredientsItem = optional.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());// if in stock deleted, if empty add// like once add, second delete in testing

        return ingredientItemRepository.save(ingredientsItem);
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {

        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }
}
