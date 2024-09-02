package com.works.archisproject.exception.applicationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationAlreadyExistsException extends RuntimeException{

    public ApplicationAlreadyExistsException(String message) {
        super(message);
    }
}
