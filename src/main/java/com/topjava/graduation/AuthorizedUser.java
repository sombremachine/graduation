package com.topjava.graduation;


import com.topjava.graduation.data.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User userTo;

    public AuthorizedUser(User user) {
//        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.userTo = user;//UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(User newTo) {
        userTo = newTo;
    }

    public User getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}