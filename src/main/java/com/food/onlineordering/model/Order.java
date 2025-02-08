package com.food.onlineordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // for many orders there will be same user
    private User customer;

    @JsonIgnore
    @ManyToOne // one restuarnt have multiple order ,
    // multiple order have to be in same restuarnt
    private Restaurant restaurant;

    private Long totalAmount;
    private String orderStatus;

    private Date createdAt;

    @ManyToOne // one address can have multiple orders  , one order will have one address
    private Address deliveryAddress;

    @OneToMany // many items have same order , one order have many items
    private List<OrderItem> items;

    private int totalItem;
    private int totalPrice;
    //private Payment payment;



}
