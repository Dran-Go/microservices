package com.github.drango.microservices.user.dao;

import com.github.drango.microservices.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    User findById(Integer id);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByEmailValid(Boolean emailValid);

    boolean add(User user);

    int update(User user);
}
