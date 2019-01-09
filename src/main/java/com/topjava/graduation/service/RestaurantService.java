package com.topjava.graduation.service;

import com.topjava.graduation.data.Restaurant;
import com.topjava.graduation.service.response.RestaurantTo;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void delete(int id);

    void update(Restaurant restaurant);

    Restaurant get(int id);

    List<RestaurantTo> getAll();
}
