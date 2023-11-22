package com.mycourse.Costumer.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String msg) {
        super(msg);
    }
}
