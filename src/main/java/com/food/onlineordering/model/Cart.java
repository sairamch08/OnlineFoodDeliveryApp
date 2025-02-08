package com.food.onlineordering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne // 1 cart for user
    private User customer;

    private Long total;


    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true) // in 1 cart many items
    private List<CartItem> item = new ArrayList<>();


}

