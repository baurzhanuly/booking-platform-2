package com.booking.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Ресурс или бронирование не найдены в БД → HTTP 404.
 *
 * Использование:
 *   throw new ResourceNotFoundException("Бронирование", id);
 *   throw new ResourceNotFoundException("Ресурс с типом TABLE", resourceId);
 */
public class ResourceNotFoundException extends BookingException {

    public ResourceNotFoundException(String entityName, Long id) {
        super("%s с ID=%d не найден".formatted(entityName, id), HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
