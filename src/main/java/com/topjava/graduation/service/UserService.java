package com.topjava.graduation.service;


import com.topjava.graduation.data.User;
import com.topjava.graduation.service.request.RequestUserTo;
import com.topjava.graduation.service.response.UserTo;

import java.util.List;

public interface UserService {

//    List<User> test();

    UserTo create(RequestUserTo requestUserTo);

    void delete(int id);// throws NotFoundException;

    UserTo get(int id);// throws NotFoundException;

    UserTo getByEmail(String email);// throws NotFoundException;

    void update(User user);

    void update(RequestUserTo user);

    List<UserTo> getAll();

    void vote(int restaurantId);

//    void enable(int id, boolean enable);

//    User getWithMeals(int id);
}