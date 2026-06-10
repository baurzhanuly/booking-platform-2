package com.booking.resource.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Дополнительные атрибуты ресурса (EAV — Entity-Attribute-Value).
 *
 * Позволяет хранить специфичные поля без изменения схемы:
 *   TABLE:  zone=terrace, window=true
 *   BOOK:   isbn=978-xxx, author=Достоевский
 *   TICKET: row=5, seat=12, category=VIP
 *
 * Альтернатива — JSONB колонка в PostgreSQL. EAV выбран для удобства
 * фильтрации по атрибутам (WHERE key='zone' AND value='terrace').
 */
@Entity
@Table(
        name = "resource_attributes",
        indexes = {
                @Index(name = "idx_attr_resource_key", columnList = "resource_id, attr_key")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = "resource")
public class ResourceAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attr_seq")
    @SequenceGenerator(name = "attr_seq", sequenceName = "attr_seq", allocationSize = 50)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "resource_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_attr_resource")
    )
    private Resource resource;

    @Column(name = "attr_key", nullable = false, length = 100)
    private String key;

    @Column(name = "attr_value", nullable = false, length = 500)
    private String value;
}
