package com.github.drango.microservices.user.dao;

import org.apache.ibatis.annotations.Mapper;
import com.github.drango.microservices.user.domain.UserEmailVerification;

import java.util.List;

@Mapper
public interface UserEmailVerificationDao {
    UserEmailVerification findByCode(String code);

    List<UserEmailVerification> findByUserId(Integer userId);

    boolean add(UserEmailVerification emailVerification);

    int update(UserEmailVerification emailVerification);
}
