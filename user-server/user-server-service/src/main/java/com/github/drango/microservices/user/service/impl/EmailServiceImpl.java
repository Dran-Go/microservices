package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.dao.UserEmailVerificationDao;
import com.github.drango.microservices.user.domain.UserEmailVerification;
import com.github.drango.microservices.user.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    UserEmailVerificationDao emailVerificationDao;

    @Override
    public String createEmailVerification(Integer userId, String email) throws BusinessException {
        UserEmailVerification emailVerification = new UserEmailVerification();
        emailVerification.setUserId(userId);
        emailVerification.setEmail(email);
        String verifyCode = UUID.randomUUID().toString();
        emailVerification.setCode(verifyCode);
        emailVerification.setValid(true);

        if (emailVerificationDao.add(emailVerification)) {
            return verifyCode;
        }
        return null;
    }
}
