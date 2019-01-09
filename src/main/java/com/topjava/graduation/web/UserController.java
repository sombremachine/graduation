package com.topjava.graduation.web;

import com.topjava.graduation.data.User;
import com.topjava.graduation.service.UserService;
import com.topjava.graduation.service.request.RequestUserTo;
import com.topjava.graduation.service.response.UserTo;
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
@Secured("ROLE_ROOT")
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/user";

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserTo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserTo get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<UserTo> createWithLocation(@Valid @RequestBody RequestUserTo requestUserTo) {
        UserTo created = service.create(requestUserTo);
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
    public void update(@Valid @RequestBody User user, @PathVariable("id") int id) {
//        assureIdConsistent(user, id); //{TODO:}
        service.update(user);
    }

    @Secured("ROLE_USER")
    @PostMapping("/vote/{id}")
    public void userVote(@PathVariable("id") int restaurantId) {
        service.vote(restaurantId);
    }
}
