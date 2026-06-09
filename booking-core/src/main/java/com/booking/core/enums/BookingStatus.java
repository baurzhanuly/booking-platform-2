package com.booking.core.enums;

/**
 * Статус бронирования — отражает текущий шаг в Camunda BPMN процессе.
 *
 * Жизненный цикл:
 *
 *   PENDING → CONFIRMED → COMPLETED
 *      ↓           ↓
 *   CANCELLED   CANCELLED
 *
 * PENDING    — заявка создана, процесс Camunda запущен
 * CONFIRMED  — ресурс зарезервирован, ожидаем оплату / подтверждение
 * COMPLETED  — бронирование успешно завершено
 * CANCELLED  — отменено (таймаут, отказ, ручная отмена)
 * FAILED     — техническая ошибка в процессе
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELLED,
    FAILED
}
