package com.github.drango.microservices.gateway.service;

import com.github.drango.microservices.gateway.bean.request.LetterMailRequest;

public interface MailService {
    Boolean sendMail(String username, LetterMailRequest letterMailRequest);
}
