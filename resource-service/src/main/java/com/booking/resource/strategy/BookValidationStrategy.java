package com.booking.resource.strategy;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.enums.ResourceType;
import com.booking.core.exception.BookingValidationException;
import org.springframework.stereotype.Component;

/**
 * Правила валидации для типа BOOK (книги в библиотеке).
 */
@Component
public class BookValidationStrategy implements ResourceValidationStrategy {

    private static final int MAX_BORROW_DAYS = 30;

    @Override
    public ResourceType getSupportedType() {
        return ResourceType.BOOK;
    }

    @Override
    public void validate(CreateResourceRequest request) {
        if (request.capacity() == null || request.capacity() < 1) {
            throw new BookingValidationException(
                    "Количество экземпляров книги должно быть не менее 1"
            );
        }
        if (request.name() == null || request.name().isBlank()) {
            throw new BookingValidationException("Название книги обязательно");
        }
    }

    @Override
    public int getMaxBookingDurationHours() {
        // Книги выдаются на дни, не часы — 30 дней * 24 часа
        return MAX_BORROW_DAYS * 24;
    }
}
