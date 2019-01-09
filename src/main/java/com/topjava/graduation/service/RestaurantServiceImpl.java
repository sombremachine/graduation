package com.topjava.graduation.service;

import com.topjava.graduation.data.Restaurant;
import com.topjava.graduation.data.RestaurantRepository;
import com.topjava.graduation.service.response.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository repository;

    private final RestaurantMapper mapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, RestaurantMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<RestaurantTo> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
