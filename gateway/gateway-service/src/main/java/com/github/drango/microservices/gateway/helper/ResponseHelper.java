package com.github.drango.microservices.gateway.helper;

import com.github.drango.microservices.common.exception.BusinessException;
import com.github.drango.microservices.common.result.ResultBo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {
    public void  checkResponse(ResultBo<?> response) throws BusinessException {
        if (response == null) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统繁忙");
        } else if (response.getCode() != HttpStatus.OK.value()) {
            throw new BusinessException(response.getCode(), response.getMessage());
        }
    }
}
