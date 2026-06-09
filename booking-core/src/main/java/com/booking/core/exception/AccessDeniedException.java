package com.booking.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Пользователь не имеет прав на операцию → HTTP 403 Forbidden.
 *
 * Пример: USER пытается отменить чужое бронирование.
 * ADMIN может отменять любые.
 */
public class AccessDeniedException extends BookingException {

    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException() {
        super("Доступ запрещён", HttpStatus.FORBIDDEN);
    }
}
