package com.works.archisproject.exception;

import com.works.archisproject.dto.response.ExceptionResponse;
import com.works.archisproject.exception.applicationException.ApplicationAlreadyExistsException;
import com.works.archisproject.exception.applicationException.ApplicationNotFoundException;
import com.works.archisproject.exception.emailException.EmailAlreadyRegisteredException;
import com.works.archisproject.exception.emailException.EmailNotFoundException;
import com.works.archisproject.exception.skillException.SkillAlreadyExistsException;
import com.works.archisproject.exception.skillException.SkillNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> handleEmailAlreadyExceptionHandler(EmailAlreadyRegisteredException exception,
                                                                                WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "EMAIL_ALREADY_REGISTERED_EXCEPTION"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEmailNotFoundException(EmailNotFoundException exception,
                                                                          WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "EMAIL_NOT_FOUND_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleTokenExpiredException(TokenExpiredException exception,
                                                                         WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "TOKEN_EXPIRED_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSkillNotFoundException(SkillNotFoundException exception,
                                                                          WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "SKILL_NOT_FOUND_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SkillAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleSkillAlreadyExistsException(SkillAlreadyExistsException exception,
                                                                          WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "SKILL_ALREADY_EXISTS_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(
                errorMessages,
                LocalDateTime.now(),
                request.getDescription(false),
                "VALIDATION_ERROR"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationNotFoundException(ApplicationNotFoundException exception,
                                                                               WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "APPLICATION_NOT_FOUND_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationAlreadyExistsException(ApplicationAlreadyExistsException exception,
                                                                               WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "APPLICATION_ALREADY_EXISTS_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception,
                                                                                WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false),
                "USER_NOT_FOUND_EXCEPTION"
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


}
