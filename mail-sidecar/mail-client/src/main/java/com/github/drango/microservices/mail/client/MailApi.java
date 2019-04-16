package com.github.drango.microservices.mail.client;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.mail.bean.VerifyMailRequest;
import com.github.drango.microservices.mail.bean.LetterMailRequest;
import com.github.drango.microservices.mail.hystrix.MailApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@FeignClient(name = "mail-server", fallback = MailApiHystrix.class)
public interface MailApi {
    @PostMapping(value = "api/mail/verify")
    ResultBo<Boolean> sendVerifyMail(@RequestBody VerifyMailRequest verifyMailRequest);

    @PostMapping(value = "api/mail/letter")
    ResultBo<Boolean> sendLetterMail(@RequestBody LetterMailRequest letterMailRequest);
}
