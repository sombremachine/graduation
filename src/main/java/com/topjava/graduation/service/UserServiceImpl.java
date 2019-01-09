package com.topjava.graduation.service;

import com.topjava.graduation.data.*;
import com.topjava.graduation.service.request.RequestUserTo;
import com.topjava.graduation.service.response.UserTo;
import com.topjava.graduation.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.topjava.graduation.util.ValidationUtil.checkNotFound;
import static com.topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;
    private final UserMapper userMapper;
    private final VoteRepository voteRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RestaurantRepository restaurantRepository, UserMapper userMapper, VoteRepository voteRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restaurantRepository = restaurantRepository;
        this.userMapper = userMapper;
        this.voteRepository = voteRepository;
        this.em = em;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    @Override
    public UserTo create(RequestUserTo requestUserTo) {
        Assert.notNull(requestUserTo, "user must not be null");
        User user = userMapper.toUser(requestUserTo);
        return userMapper.toUserTo(userRepository.save(prepareToSave(user, passwordEncoder)));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserTo get(int id) {
        return userMapper.toUserTo(checkNotFoundWithId(userRepository.findById(id).get(), id));
    }

    @Override
    public UserTo getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return userMapper.toUserTo(checkNotFound(userRepository.getByEmail(email), "email=" + email));
    }

    @Override
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(prepareToSave(user, passwordEncoder)), user.getId());
    }

    @Override
    public void update(RequestUserTo requestUserTo) {
        User user = userRepository.getOne(SecurityUtil.authUserId());
        userMapper.toUser(user, requestUserTo);
        userRepository.save(prepareToSave(user, passwordEncoder));
    }

//    @Override
//    public void update(UserTo userTo) {
//        User user = userMapper.toUser(userTo);//updateFromTo(get(userTo.getId()), userTo);
//        userRepository.save(prepareToSave(user, passwordEncoder));
//    }

    @Override
    public List<UserTo> getAll() {
        return userMapper.toUserTo(userRepository.findAll());
    }

    @Override
    public void vote(int restaurantId) {
        User user = em.getReference(User.class,SecurityUtil.authUserId());
        Restaurant restaurant = em.getReference(Restaurant.class,restaurantId);
        Vote vote = new Vote(null,"asdasd",user,restaurant,LocalDateTime.now());
        voteRepository.save(vote);
    }
}
