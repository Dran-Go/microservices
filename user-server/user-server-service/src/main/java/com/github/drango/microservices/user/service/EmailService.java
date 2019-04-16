package com.github.drango.microservices.user.service;

import com.github.drango.microservices.common.exception.BusinessException;

public interface EmailService {
    String createEmailVerifyUri(Integer userId) throws BusinessException;

    Boolean checkCode(String code) throws BusinessException;
}
