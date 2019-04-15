package com.github.drango.microservices.mail.hystrix;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.mail.bean.LetterMailRequest;
import com.github.drango.microservices.mail.bean.VerifyMailRequest;
import com.github.drango.microservices.mail.client.MailApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MailApiHystrix implements MailApi {
    @Override
    public ResultBo<Boolean> sendVerifyMail(VerifyMailRequest verifyMailRequest) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }

    @Override
    public ResultBo<Boolean> sendLetterMail(LetterMailRequest letterMailRequest) {
        return new ResultBo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
    }
}
