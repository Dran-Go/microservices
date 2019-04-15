package com.github.drango.microservices.user.service;

import com.github.drango.microservices.common.exception.BusinessException;

public interface EmailService {
    String createEmailVerification(Integer userId, String email) throws BusinessException;
}
