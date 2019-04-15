package com.github.drango.microservices.user.dao;

import org.apache.ibatis.annotations.Mapper;
import com.github.drango.microservices.user.domain.UserEmailVerification;

@Mapper
public interface UserEmailVerificationDao {
    UserEmailVerification findByCode(String code);

    boolean add(UserEmailVerification emailVerification);
}
