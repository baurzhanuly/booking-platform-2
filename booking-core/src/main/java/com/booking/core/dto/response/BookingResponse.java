package com.booking.core.dto.response;

import com.booking.core.enums.BookingStatus;
import com.booking.core.enums.ResourceType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Ответ с данными о бронировании.
 * Возвращается клиенту из booking-service REST API.
 *
 * Используем record — immutable, нет boilerplate, equals/hashCode/toString бесплатно.
 * @Builder от Lombok работает с record через явный конструктор.
 */
@Builder
public record BookingResponse(

        Long id,

        /** ID пользователя из Keycloak (sub claim JWT) */
        String userId,

        ResourceType resourceType,
        Long resourceId,
        String resourceName,

        BookingStatus status,

        LocalDateTime startTime,
        LocalDateTime endTime,

        /** ID процесса в Camunda — полезен для отладки в Operate UI */
        String processInstanceKey,

        String comment,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
