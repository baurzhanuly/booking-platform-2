package com.booking.core.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Единый формат ошибки для всех сервисов.
 *
 * Пример ответа при ошибке валидации (400):
 * {
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Ошибка валидации",
 *   "path": "/api/v1/bookings",
 *   "timestamp": "2024-01-15T10:30:00",
 *   "fieldErrors": [
 *     { "field": "startTime", "message": "Время начала должно быть в будущем" }
 *   ]
 * }
 */
@Builder
public record ErrorResponse(

        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,

        /** Список ошибок по конкретным полям (при валидации) */
        List<FieldError> fieldErrors

) {
    /**
     * Ошибка конкретного поля из @Valid валидации.
     */
    @Builder
    public record FieldError(
            String field,
            String message,
            Object rejectedValue
    ) {}
}
