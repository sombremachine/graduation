package com.topjava.graduation.web;


import com.topjava.graduation.data.Restaurant;
import com.topjava.graduation.service.RestaurantService;
import com.topjava.graduation.service.response.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    static final String REST_URL = "/restaurant";

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @Secured("ROLE_USER")
    @GetMapping
    public List<RestaurantTo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
//        assureIdConsistent(user, id); //{TODO:}
        service.update(restaurant);
    }
}
