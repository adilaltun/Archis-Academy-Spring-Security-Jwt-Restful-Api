package com.works.archisproject.exception.skillException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException{

    public SkillNotFoundException(String message) {
        super(message);
    }

}
