package com.booking.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации периода бронирования:
 *   - endTime > startTime
 *   - продолжительность не превышает максимально допустимую
 *
 * Вешается на класс DTO (class-level constraint), т.к. нужен доступ
 * сразу к двум полям.
 *
 * Использование:
 * <pre>
 * {@literal @}ValidBookingPeriod
 * public record CreateBookingRequest(
 *     LocalDateTime startTime,
 *     LocalDateTime endTime,
 *     ...
 * ) {}
 * </pre>
 */
@Documented
@Constraint(validatedBy = BookingPeriodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBookingPeriod {

    String message() default "Некорректный период бронирования: endTime должен быть позже startTime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** Максимальная продолжительность в часах (0 = без ограничений) */
    int maxDurationHours() default 0;
}
