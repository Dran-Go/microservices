package com.github.drango.microservices.user.dao;

import com.github.drango.microservices.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findByUsername(String username);
}
