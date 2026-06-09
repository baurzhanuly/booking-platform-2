package com.booking.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Базовый класс для всех бизнес-исключений платформы.
 *
 * Паттерн: каждое исключение знает свой HTTP-статус.
 * GlobalExceptionHandler в каждом сервисе ловит эту иерархию
 * и возвращает ErrorResponse с нужным статусом.
 *
 * Используем RuntimeException (unchecked) — не засоряем сигнатуры методов.
 */
public abstract class BookingException extends RuntimeException {

    private final HttpStatus status;

    protected BookingException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    protected BookingException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
