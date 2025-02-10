package com.food.onlineordering.service;
/*
* Here we are handling both ingredientItems and IngredientCategory
* */
import com.food.onlineordering.model.IngredientCategory;
import com.food.onlineordering.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);


}
