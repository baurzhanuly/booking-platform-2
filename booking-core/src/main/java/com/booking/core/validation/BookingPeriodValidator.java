package com.booking.core.validation;

import com.booking.core.dto.request.CreateBookingRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

/**
 * Реализация валидатора для @ValidBookingPeriod.
 *
 * ConstraintValidator<A, T>:
 *   A — аннотация
 *   T — тип объекта, на котором она висит
 *
 * Валидирует CreateBookingRequest:
 * 1. endTime > startTime
 * 2. Продолжительность не превышает maxDurationHours (если задано)
 */
public class BookingPeriodValidator
        implements ConstraintValidator<ValidBookingPeriod, CreateBookingRequest> {

    private int maxDurationHours;

    @Override
    public void initialize(ValidBookingPeriod annotation) {
        this.maxDurationHours = annotation.maxDurationHours();
    }

    @Override
    public boolean isValid(CreateBookingRequest request, ConstraintValidatorContext context) {
        if (request == null
                || request.startTime() == null
                || request.endTime() == null) {
            return true; // null проверяет @NotNull отдельно
        }

        if (!request.endTime().isAfter(request.startTime())) {
            // Отключаем дефолтное сообщение и задаём своё с указанием полей
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "endTime должен быть позже startTime"
            ).addPropertyNode("endTime").addConstraintViolation();
            return false;
        }

        if (maxDurationHours > 0) {
            long hours = Duration.between(request.startTime(), request.endTime()).toHours();
            if (hours > maxDurationHours) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Максимальная продолжительность бронирования: %d ч."
                                .formatted(maxDurationHours)
                ).addPropertyNode("endTime").addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
