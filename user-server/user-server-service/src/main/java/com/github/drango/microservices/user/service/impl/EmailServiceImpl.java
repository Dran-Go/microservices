package com.github.drango.microservices.user.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.user.dao.UserDao;
import com.github.drango.microservices.user.dao.UserEmailVerificationDao;
import com.github.drango.microservices.user.domain.User;
import com.github.drango.microservices.user.domain.UserEmailVerification;
import com.github.drango.microservices.user.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    UserEmailVerificationDao emailVerificationDao;
    @Autowired
    UserDao userDao;

    @Override
    public String createEmailVerifyUri(Integer userId) throws BusinessException {
        List<UserEmailVerification> listEmailVerification = emailVerificationDao.findByUserId(userId);
        if (listEmailVerification != null) {
            listEmailVerification.forEach(emailVerification -> emailVerificationDao.update(emailVerification));
        }
        UserEmailVerification emailVerification = new UserEmailVerification();
        emailVerification.setUserId(userId);
        String verifyCode = UUID.randomUUID().toString();
        emailVerification.setCode(verifyCode);
        emailVerification.setValid(true);
        if (!emailVerificationDao.add(emailVerification)) {
            return null;
        }
        return verifyCode;
    }

    @Override
    public Boolean checkCode(String code) throws BusinessException {
        UserEmailVerification emailVerification = emailVerificationDao.findByCode(code);
        if (emailVerification == null || !emailVerification.getValid()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "验证信息错误或已失效");
        }
        User user = userDao.findById(emailVerification.getUserId());
        user.setEmailValid(true);
        if (userDao.update(user) != 1) {
            return false;
        }
        emailVerificationDao.update(emailVerification);
        return true;
    }
}
