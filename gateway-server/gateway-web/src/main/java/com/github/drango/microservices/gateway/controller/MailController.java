package com.github.drango.microservices.gateway.controller;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultVo;
import com.github.drango.microservices.gateway.bean.request.LetterMailRequest;
import com.github.drango.microservices.gateway.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/api/mail/letter")
    ResultVo<Boolean> sendLetterMail(@RequestHeader(name = "username") String username,
                                     LetterMailRequest letterMailRequest) {
        Boolean success = false;
        try {
            success = mailService.sendMail(username, letterMailRequest);
        } catch (BusinessException e) {
            LOG.error("send mail failed, request:{}, error:{}", letterMailRequest.toString(), e.getMessage());
            return new ResultVo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("send mail failed, request:{}", letterMailRequest.toString());
        }
        return success != null ? new ResultVo<>(success) :
                new ResultVo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
