package com.works.archisproject.exception.skillException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SkillAlreadyExistsException extends RuntimeException {

    public SkillAlreadyExistsException(String message) {
        super(message);
    }
}
