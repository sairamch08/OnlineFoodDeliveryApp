package com.food.onlineordering.repository;

import com.food.onlineordering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
