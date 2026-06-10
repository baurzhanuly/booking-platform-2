-- =============================================================================
-- V1__init_schema.sql
-- Начальная схема resource-service.
-- Flyway выполняет миграции по порядку: V1, V2, V3...
-- Никогда не редактируем применённые миграции — только добавляем новые.
-- =============================================================================

-- ── Sequences ──────────────────────────────────────────────────────────────
-- allocationSize=50 в JPA соответствует INCREMENT BY 50 здесь.
-- Это batch-оптимизация: Hibernate резервирует 50 ID за один DB-запрос.
CREATE SEQUENCE IF NOT EXISTS resource_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS slot_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS attr_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- ── resources ─────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS resources
(
    id          BIGINT       NOT NULL DEFAULT nextval('resource_seq'),
    type        VARCHAR(20)  NOT NULL,     -- ResourceType enum: TABLE, BOOK, TICKET
    name        VARCHAR(200) NOT NULL,
    description TEXT,
    capacity    INTEGER      NOT NULL CHECK (capacity > 0),
    location    VARCHAR(500),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP,

    CONSTRAINT pk_resources PRIMARY KEY (id),
    CONSTRAINT chk_resources_type CHECK (type IN ('TABLE', 'BOOK', 'TICKET'))
    );

-- Основные индексы
CREATE INDEX IF NOT EXISTS idx_resources_type   ON resources (type);
CREATE INDEX IF NOT EXISTS idx_resources_active ON resources (active);

-- ── resource_slots ─────────────────────────────────────────────────────────
-- Хранит занятые временные слоты.
-- Критичный индекс: resource_id + временной диапазон для проверки перекрытий.
CREATE TABLE IF NOT EXISTS resource_slots
(
    id          BIGINT    NOT NULL DEFAULT nextval('slot_seq'),
    resource_id BIGINT    NOT NULL,
    booking_id  BIGINT    NOT NULL,   -- ID из booking-service (не FK — разные БД)
    start_time  TIMESTAMP NOT NULL,
    end_time    TIMESTAMP NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_resource_slots       PRIMARY KEY (id),
    CONSTRAINT fk_slot_resource        FOREIGN KEY (resource_id) REFERENCES resources (id) ON DELETE CASCADE,
    CONSTRAINT chk_slot_time_order     CHECK (end_time > start_time)
    );

-- Индекс для запроса "есть ли перекрытие с этим слотом?"
CREATE INDEX IF NOT EXISTS idx_slots_resource_time
    ON resource_slots (resource_id, start_time, end_time);

CREATE INDEX IF NOT EXISTS idx_slots_booking_id
    ON resource_slots (booking_id);

-- ── resource_attributes ────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS resource_attributes
(
    id          BIGINT       NOT NULL DEFAULT nextval('attr_seq'),
    resource_id BIGINT       NOT NULL,
    attr_key    VARCHAR(100) NOT NULL,
    attr_value  VARCHAR(500) NOT NULL,

    CONSTRAINT pk_resource_attributes  PRIMARY KEY (id),
    CONSTRAINT fk_attr_resource        FOREIGN KEY (resource_id) REFERENCES resources (id) ON DELETE CASCADE,
    CONSTRAINT uq_attr_resource_key    UNIQUE (resource_id, attr_key)  -- один ключ на ресурс
    );

CREATE INDEX IF NOT EXISTS idx_attr_resource_key
    ON resource_attributes (resource_id, attr_key);