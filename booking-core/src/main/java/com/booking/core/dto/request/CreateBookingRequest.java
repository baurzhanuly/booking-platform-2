package com.booking.core.dto.request;

import com.booking.core.enums.ResourceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Запрос на создание бронирования.
 * Приходит от клиента через REST API booking-service.
 *
 * Аннотации валидации Jakarta срабатывают автоматически при наличии
 * @Valid на параметре контроллера.
 */
@Builder
public record CreateBookingRequest(

        @NotNull(message = "Тип ресурса обязателен")
        ResourceType resourceType,

        @NotNull(message = "ID ресурса обязателен")
        @Positive(message = "ID ресурса должен быть положительным")
        Long resourceId,

        @NotNull(message = "Время начала обязательно")
        @Future(message = "Время начала должно быть в будущем")
        LocalDateTime startTime,

        @NotNull(message = "Время окончания обязательно")
        @Future(message = "Время окончания должно быть в будущем")
        LocalDateTime endTime,

        /** Комментарий к бронированию (необязательно) */
        String comment

) {}
