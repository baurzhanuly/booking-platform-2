package com.booking.resource.strategy;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.enums.ResourceType;

/**
 * Strategy-паттерн: валидация и бизнес-правила для каждого типа ресурса.
 *
 * Каждая реализация — отдельный @Component, регистрируется в ResourceValidationStrategyFactory.
 * Добавление нового типа = новый класс, без изменения существующего кода (Open/Closed Principle).
 */
public interface ResourceValidationStrategy {

    /** Тип ресурса, который обрабатывает эта стратегия */
    ResourceType getSupportedType();

    /**
     * Валидирует запрос на создание/обновление ресурса.
     * Бросает BookingValidationException если данные некорректны.
     */
    void validate(CreateResourceRequest request);

    /**
     * Максимальная продолжительность одного бронирования в часах.
     * Используется в BookingPeriodValidator.
     */
    int getMaxBookingDurationHours();
}
