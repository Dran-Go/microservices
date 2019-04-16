package com.github.drango.microservices.gateway.service.impl;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.gateway.bean.request.LetterMailRequest;
import com.github.drango.microservices.gateway.helper.ResponseHelper;
import com.github.drango.microservices.gateway.service.MailService;
import com.github.drango.microservices.mail.client.MailApi;
import com.github.drango.microservices.user.client.api.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private MailApi mailApi;
    @Autowired
    private UserApi userApi;

    @Autowired
    private ResponseHelper responseHelper;

    @Override
    public Boolean sendMail(String username, LetterMailRequest request) throws BusinessException {
        ResultBo<String> toUserResponse = userApi.getUserEmail(request.getToUsername());
        responseHelper.checkResponse(toUserResponse);
        String toEmail = toUserResponse.getData();

        com.github.drango.microservices.mail.bean.LetterMailRequest letterRequest =
                new com.github.drango.microservices.mail.bean.LetterMailRequest();
        letterRequest.setEmail(toEmail);
        letterRequest.setFromUsername(username);
        letterRequest.setText(request.getText());

        ResultBo<Boolean> mailResponse = mailApi.sendLetterMail(letterRequest);
        responseHelper.checkResponse(mailResponse);

        return mailResponse.getData();
    }
}
