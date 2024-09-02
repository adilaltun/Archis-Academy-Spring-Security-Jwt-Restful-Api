package com.works.archisproject.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse(
        String message,
        LocalDateTime timeStamp,
        String path,
        String errorCode
) {

    public ExceptionResponse {

    }

}
