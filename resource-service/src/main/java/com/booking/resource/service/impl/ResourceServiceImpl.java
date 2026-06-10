package com.booking.resource.service.impl;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.dto.response.PageResponse;
import com.booking.core.dto.response.ResourceResponse;
import com.booking.core.enums.ResourceType;
import com.booking.core.exception.ResourceNotFoundException;
import com.booking.resource.entity.Resource;
import com.booking.resource.mapper.ResourceMapper;
import com.booking.resource.repository.ResourceRepository;
import com.booking.resource.repository.ResourceSlotRepository;
import com.booking.resource.service.ResourceService;
import com.booking.resource.strategy.ResourceValidationStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceSlotRepository resourceSlotRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceValidationStrategyFactory strategyFactory;

    @Override
    @Transactional
    public ResourceResponse create(CreateResourceRequest request) {
        strategyFactory.getStrategy(request.type()).validate(request);

        Resource resource = resourceMapper.toEntity(request);
        Resource saved = resourceRepository.save(resource);

        log.info("Создан ресурс: id={}, type={}, name={}", saved.getId(), saved.getType(), saved.getName());
        return resourceMapper.toResponse(saved);
    }

    @Override
    @Cacheable(value = "resources", key = "#id")
    public ResourceResponse getById(Long id) {
        return resourceMapper.toResponse(findOrThrow(id));
    }

    @Override
    public PageResponse<ResourceResponse> getAll(int page, int size) {
        Page<Resource> result = resourceRepository.findAllByActiveTrue(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
        return toPageResponse(result);
    }

    @Override
    public PageResponse<ResourceResponse> getAllByType(ResourceType type, int page, int size) {
        return null;
    }

    @Override
    public ResourceResponse update(Long id, CreateResourceRequest request) {
        return null;
    }

    @Override
    public void deactivate(Long id) {

    }

    @Override
    public boolean isAvailable(Long resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        return false;
    }

    @Override
    public List<ResourceResponse> findAvailable(ResourceType type, LocalDateTime startTime, LocalDateTime endTime) {
        return List.of();
    }

    @Override
    public void reserveSlot(Long resourceId, Long bookingId, LocalDateTime startTime, LocalDateTime endTime) {

    }

    @Override
    public void releaseSlot(Long bookingId) {

    }

    private Resource findOrThrow(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ресурс", id));
    }

    private PageResponse<ResourceResponse> toPageResponse(Page<Resource> page) {
        return PageResponse.<ResourceResponse>builder()
                .content(page.getContent().stream().map(resourceMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
