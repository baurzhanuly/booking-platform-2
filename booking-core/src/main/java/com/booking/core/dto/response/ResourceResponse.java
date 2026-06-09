package com.booking.core.dto.response;


import com.booking.core.enums.ResourceType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Ответ с данными о ресурсе из resource-service.
 */
@Builder
public record ResourceResponse(

        Long id,
        ResourceType type,
        String name,
        String description,
        Integer capacity,
        String location,

        /** true — ресурс активен и доступен для бронирования */
        boolean active,

        LocalDateTime createdAt

) {}
