package com.booking.core.dto.request;

import com.booking.core.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * Запрос на создание нового ресурса (стол, книга, место).
 * Используется в resource-service.
 */
@Builder
public record CreateResourceRequest(

        @NotNull(message = "Тип ресурса обязателен")
        ResourceType type,

        @NotBlank(message = "Название ресурса обязательно")
        String name,

        /** Описание: номер стола, ISBN книги, описание места */
        String description,

        /**
         * Вместимость — смысл зависит от типа:
         * TABLE  → количество мест за столом
         * BOOK   → количество экземпляров
         * TICKET → количество билетов
         */
        @Positive(message = "Вместимость должна быть положительной")
        Integer capacity,

        /** Локация: зал, этаж, адрес */
        String location

) {}
