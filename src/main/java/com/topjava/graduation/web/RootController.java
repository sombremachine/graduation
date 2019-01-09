package com.topjava.graduation.web;

import com.topjava.graduation.data.*;

import com.topjava.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(RootController.REST_URL)
public class RootController {
    static final String REST_URL = "/";

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserService service;

    @GetMapping("userlist")
    @Secured("ROLE_USER")
    public Restaurant test_get_user(){
        return new Restaurant(1,"Restaurant");
    }

    @GetMapping("restr")
    public List<Restaurant> test2(){
        return restaurantRepository.findAll();
    }

    @GetMapping("adminlist")
    @Secured("ROLE_ADMIN")
    public Restaurant test_get_admin(){
        return new Restaurant(2,"admin Restaurant");
    }
}
