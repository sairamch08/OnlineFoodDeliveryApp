package com.food.onlineordering.service;
/*Finding the user by JWT token,
* Finding the user by email
*
* */
import com.food.onlineordering.model.User;


public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
