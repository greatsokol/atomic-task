package org.atomicmd.back.mapper;

import org.atomicmd.back.model.dto.DetailPostDto;
import org.atomicmd.back.model.dto.MasterPostDto;
import org.atomicmd.back.model.dto.MasterPostResponseDto;
import org.atomicmd.back.model.dto.MasterPutResponseDto;
import org.atomicmd.back.model.entity.DetailEntity;
import org.atomicmd.back.model.entity.MasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface MasterMapper {
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    MasterEntity toMasterEntity(MasterPostDto masterPostDto);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    DetailEntity mapDetail(DetailPostDto inputElement);

    MasterPostResponseDto toMasterResponseDto(MasterEntity masterEntity);

    MasterPutResponseDto toMasterPutResponseDto(MasterEntity masterEntity);

    List<MasterPostResponseDto> toMasterResponseDtoList(List<MasterEntity> masterEntityList);

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
