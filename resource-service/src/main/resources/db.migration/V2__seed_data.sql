-- =============================================================================
-- V2__seed_data.sql
-- Тестовые данные для разработки.
-- НЕ применяется в production (используй Flyway profiles или отдельный модуль).
-- =============================================================================

-- ── Столики в ресторане ────────────────────────────────────────────────────
INSERT INTO resources (type, name, description, capacity, location, active)
VALUES
    ('TABLE', 'Стол №1', 'Угловой стол у окна', 2, 'Основной зал', true),
    ('TABLE', 'Стол №2', 'Стол для большой компании', 6, 'Основной зал', true),
    ('TABLE', 'Стол №3', 'VIP-кабинет', 4, 'VIP-зал', true),
    ('TABLE', 'Стол №4', 'Терраса у фонтана', 2, 'Терраса', true),
    ('TABLE', 'Стол №5', 'Барная стойка', 3, 'Бар', true);

-- Атрибуты столиков
INSERT INTO resource_attributes (resource_id, attr_key, attr_value)
VALUES
    (1, 'zone',    'main_hall'),
    (1, 'window',  'true'),
    (2, 'zone',    'main_hall'),
    (2, 'smoking', 'false'),
    (3, 'zone',    'vip'),
    (3, 'private', 'true'),
    (4, 'zone',    'terrace'),
    (4, 'outdoor', 'true'),
    (5, 'zone',    'bar');

-- ── Книги в библиотеке ─────────────────────────────────────────────────────
INSERT INTO resources (type, name, description, capacity, location, active)
VALUES
    ('BOOK', 'Война и мир',       'Лев Толстой, 1869',         3, 'Зал классики, стеллаж A1', true),
    ('BOOK', 'Преступление и наказание', 'Достоевский, 1866',  2, 'Зал классики, стеллаж A2', true),
    ('BOOK', 'Мастер и Маргарита', 'Булгаков, 1967',           4, 'Зал XX века, стеллаж B3',  true),
    ('BOOK', 'Чистый код',        'Роберт Мартин, 2008',       2, 'IT-зал, стеллаж C1',       true);

INSERT INTO resource_attributes (resource_id, attr_key, attr_value)
VALUES
    (6, 'isbn',      '978-5-17-099202-5'),
    (6, 'author',    'Толстой Лев Николаевич'),
    (6, 'genre',     'Роман'),
    (7, 'isbn',      '978-5-17-099876-8'),
    (7, 'author',    'Достоевский Фёдор Михайлович'),
    (7, 'genre',     'Роман'),
    (8, 'isbn',      '978-5-17-099451-7'),
    (8, 'author',    'Булгаков Михаил Афанасьевич'),
    (8, 'genre',     'Фантастика'),
    (9, 'isbn',      '978-5-4461-2379-3'),
    (9, 'author',    'Мартин Роберт'),
    (9, 'genre',     'Профессиональная литература');

-- ── Билеты на мероприятия ──────────────────────────────────────────────────
INSERT INTO resources (type, name, description, capacity, location, active)
VALUES
    ('TICKET', 'Концерт джаза — 15 февраля',   'Билеты стандарт',  50, 'Концертный зал, ряды 1-5',   true),
    ('TICKET', 'Концерт джаза — 15 февраля',   'Билеты VIP',       10, 'Концертный зал, ряды 1-2',   true),
    ('TICKET', 'Выставка современного искусства', 'Дневной сеанс',  30, 'Галерея, этаж 2',            true);

INSERT INTO resource_attributes (resource_id, attr_key, attr_value)
VALUES
    (10, 'category', 'standard'),
    (10, 'event_date', '2025-02-15'),
    (10, 'price',    '1500'),
    (11, 'category', 'vip'),
    (11, 'event_date', '2025-02-15'),
    (11, 'price',    '5000'),
    (12, 'category', 'standard'),
    (12, 'event_date', '2025-02-20'),
    (12, 'price',    '800');