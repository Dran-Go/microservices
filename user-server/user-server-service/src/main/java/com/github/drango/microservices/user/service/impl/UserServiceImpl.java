package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.user.dao.UserDao;
import com.github.drango.microservices.user.domain.User;
import com.github.drango.microservices.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public String queryUser(String username, String password) {
        User user = null;
        user = userDao.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return "True";
        }
        return "False";
    }

}
