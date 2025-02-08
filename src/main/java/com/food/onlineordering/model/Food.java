package com.food.onlineordering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long price;

    @ManyToOne // multiple type of catogories, in pizza veg, non-veg
    private Category category;

    @Column(length = 1000)
    @ElementCollection // creates a separate table
    private List<String> images;

    private boolean available;

    @ManyToOne // in one restaurant there will be multiple
    // food, for multiple 1 restuarant
    private Restaurant restaurant; // which resturant provides this food

    private boolean isVegetarian;

    private boolean isSeasonal;

    @ManyToMany // in one food there will be may ingredients
    // many food have many ingredients
    private List<IngredientsItem> ingredientsItem = new ArrayList<>();


    private Date creationDate;
}
