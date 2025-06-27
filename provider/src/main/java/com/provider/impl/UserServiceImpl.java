package com.provider.impl;

import com.common.model.User;
import com.common.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        String name = user.getName();
        System.out.println("提供者实现："+ name);
        return user;
    }
}
