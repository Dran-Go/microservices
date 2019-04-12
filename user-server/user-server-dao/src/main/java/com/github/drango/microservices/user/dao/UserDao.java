package com.github.drango.microservices.user.dao;

import com.github.drango.microservices.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findByUsername(String username);

    User findById(Integer id);

    boolean addUser(User user);

    int updateUser(User user);
}
