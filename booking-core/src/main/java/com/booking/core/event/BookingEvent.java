package com.booking.core.event;

import com.booking.core.enums.BookingStatus;
import com.booking.core.enums.ResourceType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Kafka-событие об изменении статуса бронирования.
 *
 * Публикуется в топик: booking-events
 * Консьюмеры: notification-service, audit-service (будущий)
 *
 * Используем record — сериализуется в JSON через Jackson без лишних настроек.
 * eventId нужен для idempotency в консьюмерах (не обрабатывать дубли).
 */
@Builder
public record BookingEvent(

        /** UUID события — для идемпотентности на стороне консьюмера */
        String eventId,

        Long bookingId,
        String userId,

        ResourceType resourceType,
        Long resourceId,
        String resourceName,

        BookingStatus status,

        LocalDateTime startTime,
        LocalDateTime endTime,

        /** Email пользователя — чтобы notification-service не ходил в БД */
        String userEmail,

        LocalDateTime occurredAt

) {}
