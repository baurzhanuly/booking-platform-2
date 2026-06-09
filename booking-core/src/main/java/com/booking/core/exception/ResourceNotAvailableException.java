package com.booking.core.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Ресурс занят на запрошенное время → HTTP 409 Conflict.
 *
 * Бросается в camunda-workers воркером CheckAvailabilityWorker,
 * когда resource-service возвращает isAvailable=false.
 */
public class ResourceNotAvailableException extends BookingException {

    public ResourceNotAvailableException(Long resourceId, LocalDateTime start, LocalDateTime end) {
        super(
                "Ресурс ID=%d недоступен с %s по %s".formatted(resourceId, start, end),
                HttpStatus.CONFLICT
        );
    }

    public ResourceNotAvailableException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
