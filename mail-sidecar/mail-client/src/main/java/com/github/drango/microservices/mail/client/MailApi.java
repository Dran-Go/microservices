package com.github.drango.microservices.mail.client;

import com.github.drango.microservices.common.result.ResultBo;
import com.github.drango.microservices.mail.bean.VerifyMailRequest;
import com.github.drango.microservices.mail.bean.LetterMailRequest;
import com.github.drango.microservices.mail.hystrix.MailApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;

@Primary
@FeignClient(name = "mail-sidecar", fallback = MailApiHystrix.class)
public interface MailApi {
    @PostMapping(value = "/mail-server/api/mail/verify")
    ResultBo<Boolean> sendVerifyMail(VerifyMailRequest verifyMailRequest);

    @PostMapping(value = "/mail-server/api/mail/letter")
    ResultBo<Boolean> sendLetterMail(LetterMailRequest letterMailRequest);
}
