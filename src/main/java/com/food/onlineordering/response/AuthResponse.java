package com.food.onlineordering.response;

import com.food.onlineordering.model.USER_ROLE;
import com.food.onlineordering.model.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
