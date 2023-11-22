package com.mycourse.Costumer.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_MODIFIED)
public class NoChangesException extends RuntimeException{
    public NoChangesException(String msg) {
        super(msg);
    }
}
