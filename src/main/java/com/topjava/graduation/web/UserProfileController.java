package com.topjava.graduation.web;

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

import static com.topjava.graduation.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(UserProfileController.REST_URL)
public class UserProfileController {
    public static final String REST_URL = "/rest/profile";

    private final UserService service;

    @Autowired
    public UserProfileController(UserService service) {
        this.service = service;
    }

    @Secured("ROLE_USER")
    @GetMapping
    public UserTo get() {
        return service.get(authUserId());
    }

    @Secured("ROLE_USER")
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        service.delete(authUserId());
    }

    @PostMapping("register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserTo> register(@Valid @RequestBody RequestUserTo requestUserTo) {
        UserTo created = service.create(requestUserTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured("ROLE_USER")
    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RequestUserTo requestUserTo) {
        requestUserTo.setId(authUserId());
        service.update(requestUserTo);
    }
}
