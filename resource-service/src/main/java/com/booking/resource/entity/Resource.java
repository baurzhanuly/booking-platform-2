package com.booking.resource.entity;

import com.booking.core.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность бронируемого ресурса.
 *
 * Паттерн: одна таблица для всех типов ресурсов (TABLE, BOOK, TICKET).
 * Специфичные поля вынесены в отдельную таблицу resource_attributes (EAV).
 * Это позволяет добавлять новые типы без изменения схемы.
 *
 * Используем @EqualsAndHashCode(of = "id") — два объекта равны если одинаковый id.
 * НЕ включаем в equals коллекции (slots, attributes) — проблема N+1 и LazyInit.
 */
@Entity
@Table(
        name = "resources",
        indexes = {
                @Index(name = "idx_resources_type", columnList = "type"),
                @Index(name = "idx_resources_active", columnList = "active")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"slots", "attributes"})
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_seq")
    @SequenceGenerator(name = "resource_seq", sequenceName = "resource_seq", allocationSize = 50)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ResourceType type;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Вместимость зависит от типа:
     * TABLE  → количество мест
     * BOOK   → число экземпляров
     * TICKET → количество билетов в продаже
     */
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "location", length = 500)
    private String location;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private boolean active = true;

    /**
     * Временные слоты — отдельная таблица.
     * LAZY — не грузим при каждом запросе ресурса.
     * CascadeType.ALL — создаём/удаляем вместе с ресурсом.
     */
    @OneToMany(
            mappedBy = "resource",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ResourceSlot> slots = new ArrayList<>();

    /**
     * Доп. атрибуты в формате key-value.
     * TABLE: {"zone": "terrace", "smoking": "false"}
     * BOOK:  {"isbn": "978-...", "author": "Толстой"}
     * TICKET: {"row": "5", "seat": "12", "category": "VIP"}
     */
    @OneToMany(
            mappedBy = "resource",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ResourceAttribute> attributes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
