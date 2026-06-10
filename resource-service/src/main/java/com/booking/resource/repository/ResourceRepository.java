package com.booking.resource.repository;

import com.booking.core.enums.ResourceType;
import com.booking.resource.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Page<Resource> findAllByActiveTrue(Pageable pageable);

    Page<Resource> findAllByTypeAndActiveTrue(ResourceType type, Pageable pageable);

    /**
     * Проверяет, свободен ли ресурс в запрошенный период.
     *
     * Логика перекрытия слотов (Allen's interval algebra):
     * два периода [A, B] и [C, D] перекрываются, если A < D И B > C.
     *
     * Запрос использует индекс idx_slots_resource_time.
     */
    @Query("""
            SELECT COUNT(s) = 0
            FROM ResourceSlot s
            WHERE s.resource.id = :resourceId
              AND s.startTime < :endTime
              AND s.endTime > :startTime
            """)
    boolean isAvailable(
            @Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * Все свободные ресурсы данного типа в указанный период.
     * Используется для отображения доступных слотов пользователю.
     */
    @Query("""
            SELECT r FROM Resource r
            WHERE r.type = :type
              AND r.active = true
              AND NOT EXISTS (
                  SELECT 1 FROM ResourceSlot s
                  WHERE s.resource = r
                    AND s.startTime < :endTime
                    AND s.endTime > :startTime
              )
            """)
    List<Resource> findAvailableByType(
            @Param("type") ResourceType type,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
