package com.booking.core.dto.request;

import jakarta.validation.constraints.Future;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Запрос на изменение времени бронирования.
 * Все поля опциональны — меняем только то, что передано (PATCH-семантика).
 */
@Builder
public record UpdateBookingRequest(

        @Future(message = "Новое время начала должно быть в будущем")
        LocalDateTime startTime,

        @Future(message = "Новое время окончания должно быть в будущем")
        LocalDateTime endTime,

        String comment

) {}
