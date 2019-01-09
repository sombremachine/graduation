package com.topjava.graduation.service;

import com.topjava.graduation.data.Restaurant;
import com.topjava.graduation.data.RestaurantRepository;
import com.topjava.graduation.service.response.RestaurantTo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {
    protected abstract RestaurantTo toDto(Restaurant restaurant);

    @AfterMapping
    protected void countVotes(Restaurant restaurant, @MappingTarget RestaurantTo restaurantTo) {
        restaurantTo.setNumVotes(restaurant.getVotes().size());
    }
}
