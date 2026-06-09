package com.booking.core.exception;


import com.booking.core.enums.ResourceType;
import org.springframework.http.HttpStatus;

/**
 * Запрошен тип бронирования, для которого нет реализации Strategy → HTTP 400.
 *
 * Бросается в BookingStrategyFactory, если запрошен тип,
 * который ещё не реализован или некорректен.
 */
public class UnsupportedBookingTypeException extends BookingException {

    public UnsupportedBookingTypeException(ResourceType type) {
        super("Тип бронирования '%s' не поддерживается".formatted(type), HttpStatus.BAD_REQUEST);
    }
}
