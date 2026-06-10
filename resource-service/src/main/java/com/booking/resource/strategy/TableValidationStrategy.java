package com.booking.resource.strategy;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.enums.ResourceType;
import com.booking.core.exception.BookingValidationException;
import org.springframework.stereotype.Component;

/**
 * Правила валидации для типа TABLE (столики в ресторане).
 */
@Component
public class TableValidationStrategy implements ResourceValidationStrategy {

    private static final int MAX_TABLE_CAPACITY = 20;
    private static final int MAX_BOOKING_HOURS  = 4;

    @Override
    public ResourceType getSupportedType() {
        return ResourceType.TABLE;
    }

    @Override
    public void validate(CreateResourceRequest request) {
        if (request.capacity() == null || request.capacity() < 1) {
            throw new BookingValidationException(
                    "Вместимость стола должна быть от 1 до " + MAX_TABLE_CAPACITY
            );
        }
        if (request.capacity() > MAX_TABLE_CAPACITY) {
            throw new BookingValidationException(
                    "Стол не может вмещать более %d человек".formatted(MAX_TABLE_CAPACITY)
            );
        }
        if (request.location() == null || request.location().isBlank()) {
            throw new BookingValidationException(
                    "Для стола необходимо указать расположение (зал, терраса и т.д.)"
            );
        }
    }

    @Override
    public int getMaxBookingDurationHours() {
        return MAX_BOOKING_HOURS;
    }
}
