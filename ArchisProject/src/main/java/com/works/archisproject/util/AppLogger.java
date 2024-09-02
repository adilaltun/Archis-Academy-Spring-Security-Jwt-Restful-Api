package com.works.archisproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppLogger {

    private final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logDebug(String message) {
        logger.debug(message);
    }

    public void logWarn(String message) {
        logger.warn(message);
    }

    public void logError(String message) {
        logger.error(message);
    }

    public void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
