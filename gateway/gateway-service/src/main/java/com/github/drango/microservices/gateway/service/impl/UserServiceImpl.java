package com.github.drango.microservices.gateway.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.gateway.bean.request.UserRequest;
import com.github.drango.microservices.gateway.helper.ResponseHelper;
import com.github.drango.microservices.gateway.helper.UserHelper;
import com.github.drango.microservices.gateway.service.UserService;
import com.github.drango.microservices.mail.bean.VerifyMailRequest;
import com.github.drango.microservices.mail.client.MailApi;
import com.github.drango.microservices.user.client.api.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserApi userApi;
    @Autowired
    private MailApi mailApi;

    @Autowired
    private ResponseHelper responseHelper;
    @Autowired
    private UserHelper userHelper;

    @Value("${host}")
    private String host;

    @Override
    public Boolean createUser(UserRequest userRequest) throws BusinessException {
        ResultBo<String> userResponse = userApi.createUser(userHelper.convert(userRequest));
        responseHelper.checkResponse(userResponse);
        String verifyUrl = String.format("%s%s", host, userResponse.getData());
        LOG.debug("create user success, try to send email.");

        VerifyMailRequest mailRequest = new VerifyMailRequest();
        mailRequest.setEmail(userRequest.getEmail());
        mailRequest.setUsername(userRequest.getUsername());
        mailRequest.setVerifyUrl(verifyUrl);
        
        ResultBo<Boolean> mailResponse = mailApi.sendVerifyMail(mailRequest);
        responseHelper.checkResponse(mailResponse);
        return true;
    }
}
