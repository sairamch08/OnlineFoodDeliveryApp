package com.food.onlineordering.controller;

import com.food.onlineordering.config.JwtProvider;
import com.food.onlineordering.model.Cart;
import com.food.onlineordering.model.USER_ROLE;
import com.food.onlineordering.model.User;
import com.food.onlineordering.repository.CartRepository;
import com.food.onlineordering.repository.UserRepository;
import com.food.onlineordering.request.LoginRequest;
import com.food.onlineordering.response.AuthResponse;
import com.food.onlineordering.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth") // what ever the endpoint here has auth at the start
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        // if email is in db then we will throw exceptio
        if (isEmailExist != null) {
            throw new Exception("Email is already used with another account");

        }
        // if user is not in the email then we will create a new user

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

// now user has created and we are creating the cart for that user and
        Cart cart = new Cart();
        // new cart created ,and we are setting that cart to that user
        cart.setCustomer(savedUser);

        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // this will create token

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    // this method is for the login
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {

        String userName = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(userName, password); // validates  the username and password
        // is username and password are validated then we need to login the user
        // but before login we also need to get the jwt token.

        // first time when the user is login there will not be any jwt token that is at registration time
        // but once the user has register then there will be jwt token and we need to validate that too
        // so we are setting up that jwt token

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");


        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String passwoed) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
        // if userDetails not found then
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid userName...");
        }

        if (!passwordEncoder.matches(passwoed, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password..");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }

}
