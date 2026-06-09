package com.booking.core.event;

import com.booking.core.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Kafka-событие для отправки уведомления.
 *
 * Публикуется в топик: notification-events
 * Консьюмер: notification-service
 *
 * templateVariables — переменные для подстановки в Thymeleaf-шаблон письма.
 * Например: {"resourceName": "Стол №5", "startTime": "2024-02-01 19:00"}
 */
@Builder
public record NotificationEvent(

        String eventId,

        NotificationType type,

        String recipientEmail,
        String recipientName,

        /** Переменные для шаблона письма */
        Map<String, String> templateVariables,

        LocalDateTime occurredAt

) {}
