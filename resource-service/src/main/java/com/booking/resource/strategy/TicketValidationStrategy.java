package com.booking.resource.strategy;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.enums.ResourceType;
import com.booking.core.exception.BookingValidationException;
import org.springframework.stereotype.Component;

/**
 * Правила валидации для типа TICKET (билеты на мероприятие).
 */
@Component
public class TicketValidationStrategy implements ResourceValidationStrategy {

    @Override
    public ResourceType getSupportedType() {
        return ResourceType.TICKET;
    }

    @Override
    public void validate(CreateResourceRequest request) {
        if (request.capacity() == null || request.capacity() < 1) {
            throw new BookingValidationException(
                    "Количество билетов должно быть не менее 1"
            );
        }
        if (request.description() == null || request.description().isBlank()) {
            throw new BookingValidationException(
                    "Для билета необходимо указать описание мероприятия"
            );
        }
    }

    @Override
    public int getMaxBookingDurationHours() {
        // Билет — разовое посещение, не более 12 часов
        return 12;
    }
}
