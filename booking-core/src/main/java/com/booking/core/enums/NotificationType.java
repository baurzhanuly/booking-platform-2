package com.booking.core.enums;

/**
 * Тип уведомления — определяет шаблон письма в notification-service.
 */
public enum NotificationType {

    /** Бронирование подтверждено */
    BOOKING_CONFIRMED,

    /** Бронирование отменено */
    BOOKING_CANCELLED,

    /** Напоминание (за 1 час до начала) */
    BOOKING_REMINDER,

    /** Бронирование успешно завершено */
    BOOKING_COMPLETED
}
