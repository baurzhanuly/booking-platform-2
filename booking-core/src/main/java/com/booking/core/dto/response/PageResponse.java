package com.booking.core.dto.response;

import lombok.Builder;

import java.util.List;

/**
 * Универсальный ответ с пагинацией.
 *
 * Использование:
 *   PageResponse<BookingResponse> page = PageResponse.<BookingResponse>builder()
 *       .content(bookings)
 *       .page(0)
 *       .size(20)
 *       .totalElements(100L)
 *       .totalPages(5)
 *       .build();
 *
 * @param <T> тип элементов в списке
 */
@Builder
public record PageResponse<T>(

        List<T> content,

        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last

) {}
