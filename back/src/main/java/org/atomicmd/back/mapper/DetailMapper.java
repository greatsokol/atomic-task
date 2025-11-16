package org.atomicmd.back.mapper;

import org.atomicmd.back.model.dto.DetailPostDto;
import org.atomicmd.back.model.dto.DetailResponseDto;
import org.atomicmd.back.model.entity.DetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface DetailMapper {
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    DetailEntity toDetailEntity(DetailPostDto detailPostDto);

    DetailResponseDto toDetailResponseDto(DetailEntity detailEntity);


//    Logger LOGGER = LoggerFactory.getLogger(DetailMapper.class);
//    @BeforeMapping
//    default void logBefore(Object source) {
//        LOGGER.info("Before mapping SourceObject with ID: {}", source);
//    }
//
//    @AfterMapping
//    default void logAfter(@MappingTarget Object target) {
//        LOGGER.info("After mapping, TargetObject created with ID: {}", target);
//    }
//
//    @AfterThrowing
//    default void logAfterThrowing(@MappingTarget Object target, Throwable throwable) {
//        LOGGER.error("After throwing, TargetObject created with ID: {}", target, throwable);
//    }
}
