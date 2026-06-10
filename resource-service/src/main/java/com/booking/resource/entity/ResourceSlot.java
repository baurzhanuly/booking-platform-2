package com.booking.resource.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Временной слот — занятый период ресурса.
 *
 * Когда бронирование подтверждается (Camunda CONFIRMED),
 * создаётся ResourceSlot, блокирующий период.
 * При отмене — слот удаляется, ресурс снова доступен.
 *
 * Индекс (resource_id, start_time, end_time) критичен для
 * быстрой проверки доступности: SELECT EXISTS(перекрытие слотов).
 */
@Entity
@Table(
        name = "resource_slots",
        indexes = {
                @Index(
                        name = "idx_slots_resource_time",
                        columnList = "resource_id, start_time, end_time"
                ),
                @Index(name = "idx_slots_booking_id", columnList = "booking_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = "resource")
public class ResourceSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slot_seq")
    @SequenceGenerator(name = "slot_seq", sequenceName = "slot_seq", allocationSize = 50)
    private Long id;

    /**
     * ManyToOne + LAZY — не подтягиваем Resource при каждом запросе слота.
     * ForeignKey — явное имя для удобства в explain-планах.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "resource_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_slot_resource")
    )
    private Resource resource;

    /**
     * ID бронирования из booking-service.
     * Не FK — разные базы данных (microservices).
     */
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
