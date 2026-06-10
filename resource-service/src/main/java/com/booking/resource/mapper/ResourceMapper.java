package com.booking.resource.mapper;

import com.booking.core.dto.request.CreateResourceRequest;
import com.booking.core.dto.response.ResourceResponse;
import com.booking.resource.entity.Resource;
import org.mapstruct.*;

/**
 * MapStruct маппер — генерирует реализацию в compile-time.
 *
 * componentModel = "spring" — делает маппер Spring @Component,
 * можно инжектить через @Autowired / конструктор.
 *
 * nullValuePropertyMappingStrategy = IGNORE — при update-маппинге
 * не затираем существующие поля, если в запросе они null.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ResourceMapper {

    /**
     * CreateResourceRequest → Resource (новая сущность).
     * id, createdAt, updatedAt игнорируем — проставляет БД/Hibernate.
     */
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "active",     constant = "true")
    @Mapping(target = "slots",      ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    Resource toEntity(CreateResourceRequest request);

    /**
     * Resource → ResourceResponse (ответ клиенту).
     */
    ResourceResponse toResponse(Resource resource);

    /**
     * Обновляем существующую сущность полями из request.
     * @MappingTarget — MapStruct изменяет объект, а не создаёт новый.
     */
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "slots",      ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    @Mapping(target = "updatedAt",  ignore = true)
    void updateEntity(CreateResourceRequest request, @MappingTarget Resource resource);
}
