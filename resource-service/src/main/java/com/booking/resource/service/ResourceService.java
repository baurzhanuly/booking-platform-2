package com.booking.resource.service;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.dto.response.PageResponse;
import com.booking.core.dto.response.ResourceResponse;
import com.booking.core.enums.ResourceType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контракт сервисного слоя resource-service.
 * Контроллер работает только с этим интерфейсом — не с реализацией.
 * Это упрощает тестирование (мокируем интерфейс, не класс).
 */
public interface ResourceService {

    ResourceResponse create(CreateResourceRequest request);

    ResourceResponse getById(Long id);

    PageResponse<ResourceResponse> getAll(int page, int size);

    PageResponse<ResourceResponse> getAllByType(ResourceType type, int page, int size);

    ResourceResponse update(Long id, CreateResourceRequest request);

    void deactivate(Long id);

    /**
     * Проверяет доступность ресурса (без создания слота).
     * Вызывается из camunda-workers через Feign-клиент.
     */
    boolean isAvailable(Long resourceId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Возвращает все свободные ресурсы данного типа в период.
     */
    List<ResourceResponse> findAvailable(ResourceType type, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Резервирует слот (вызывается Camunda-воркером при подтверждении).
     */
    void reserveSlot(Long resourceId, Long bookingId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Освобождает слот (вызывается Camunda-воркером при отмене).
     */
    void releaseSlot(Long bookingId);
}
