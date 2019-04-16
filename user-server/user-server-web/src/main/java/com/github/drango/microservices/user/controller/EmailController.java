package com.github.drango.microservices.user.controller;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.user.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("api/user/email/verify")
    public ResultBo<String> createEmailVerification(@RequestHeader(name = "userId") Integer userId) {
        String verifyUri = null;
        try {
            verifyUri = emailService.createEmailVerifyUri(userId);
        } catch (BusinessException e) {
            LOG.error("create email code failed, userId:{}, error:{}", userId, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyUri != null ? new ResultBo<>(verifyUri) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }


    @GetMapping("api/user/email/verify")
    public ResultBo<Boolean> checkEmailVerification(@RequestParam(name = "code") String code) {
        Boolean success = false;
        try {
            success = emailService.checkCode(code);
        } catch (BusinessException e) {
            LOG.error("verify email code failed, code:{}, error:{}", code, e.getMessage());
            return new ResultBo<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success != null ? new ResultBo<>(success) :
                new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
