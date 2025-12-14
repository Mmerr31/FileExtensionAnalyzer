package com.fileanalyzer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Утилитарный класс для логирования.
 * Обеспечивает централизованное управление логами приложения.
 */
public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    /**
     * Логирует информационное сообщение.
     *
     * @param message сообщение для логирования
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Логирует сообщение об ошибке.
     *
     * @param message сообщение об ошибке
     * @param throwable исключение (может быть null)
     */
    public static void logError(String message, Throwable throwable) {
        if (throwable != null) {
            logger.error(message, throwable);
        } else {
            logger.error(message);
        }
    }

    /**
     * Логирует предупреждение.
     *
     * @param message предупреждающее сообщение
     */
    public static void logWarning(String message) {
        logger.warn(message);
    }

    /**
     * Логирует отладочное сообщение.
     *
     * @param message отладочное сообщение
     */
    public static void logDebug(String message) {
        logger.debug(message);
    }
}