package com.booking.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Бизнес-валидация не прошла → HTTP 400 Bad Request.
 *
 * Отличие от стандартной MethodArgumentNotValidException:
 * та срабатывает на аннотации @Valid,
 * эта — на бизнес-правила в сервисном слое.
 *
 * Примеры:
 *   - startTime >= endTime
 *   - бронирование на прошедшую дату
 *   - превышен лимит одновременных бронирований у пользователя
 */
public class BookingValidationException extends BookingException {

    public BookingValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
