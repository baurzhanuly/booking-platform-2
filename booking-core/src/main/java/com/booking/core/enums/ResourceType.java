package com.booking.core.enums;

/**
 * Тип бронируемого ресурса.
 * Strategy-паттерн: каждый тип имеет свою реализацию BookingStrategy.
 */
public enum ResourceType {

    /** Столик в ресторане */
    TABLE,

    /** Книга в библиотеке */
    BOOK,

    /** Билет на мероприятие */
    TICKET
}
