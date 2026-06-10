package com.booking.resource.strategy;

import com.booking.core.enums.ResourceType;
import com.booking.core.exception.UnsupportedBookingTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Factory-паттерн: возвращает нужную Strategy по типу ресурса.
 *
 * Spring автоматически инжектирует все реализации ResourceValidationStrategy
 * через List<ResourceValidationStrategy>. При добавлении нового типа
 * достаточно создать новый @Component — фабрика подхватит его сама.
 *
 * EnumMap быстрее HashMap для enum-ключей (массив под капотом).
 */
@Slf4j
@Component
public class ResourceValidationStrategyFactory {

    private final Map<ResourceType, ResourceValidationStrategy> strategies;

    public ResourceValidationStrategyFactory(List<ResourceValidationStrategy> strategyList) {
        strategies = new EnumMap<>(ResourceType.class);
        strategyList.forEach(s -> {
            strategies.put(s.getSupportedType(), s);
            log.info("Зарегистрирована стратегия: {} → {}", s.getSupportedType(), s.getClass().getSimpleName());
        });
    }

    /**
     * @throws UnsupportedBookingTypeException если стратегия не найдена
     */
    public ResourceValidationStrategy getStrategy(ResourceType type) {
        ResourceValidationStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new UnsupportedBookingTypeException(type);
        }
        return strategy;
    }
}
