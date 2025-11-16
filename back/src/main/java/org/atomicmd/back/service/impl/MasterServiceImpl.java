package org.atomicmd.back.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.atomicmd.back.mapper.DetailMapper;
import org.atomicmd.back.mapper.MasterMapper;
import org.atomicmd.back.model.dto.*;
import org.atomicmd.back.model.entity.DetailEntity;
import org.atomicmd.back.model.entity.MasterEntity;
import org.atomicmd.back.repository.MasterRepository;
import org.atomicmd.back.service.MasterService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService {
    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;
    private final DetailMapper detailMapper;

    @Transactional
    public MasterPostResponseDto getMasterById(UUID masterId) {
        Optional<MasterEntity> optionalMasterEntity = masterRepository.findById(masterId);
        return optionalMasterEntity.map(masterMapper::toMasterResponseDto).orElse(null);
    }

    @Transactional
    public List<MasterPostResponseDto> getMasterAll() {
        return masterMapper.toMasterResponseDtoList(masterRepository.findAll());
    }

    @Transactional
    public MasterPostResponseDto postMaster(MasterPostDto postMasterDto) {
        // вычисляем общую сумму мастера по деталям
        BigDecimal sum = postMasterDto.getDetails()
                .stream()
                .map(DetailPostDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        MasterEntity masterEntity = masterMapper.toMasterEntity(postMasterDto);
        masterEntity.setTotal(sum); // устанавливаем общую сумму мастера

        return masterMapper.toMasterResponseDto(masterRepository.save(masterEntity));
    }

    @Transactional
    public MasterPutResponseDto putMaster(UUID masterId, MasterPutDto masterPutDto) {
        Optional<MasterEntity> optionalMasterEntity = masterRepository.findById(masterId);
        if (optionalMasterEntity.isPresent()) {
            MasterEntity masterEntity = optionalMasterEntity.get();
            masterEntity.setDescription(masterPutDto.getDescription());
            masterEntity.setNumber(masterPutDto.getNumber());

            return masterMapper.toMasterPutResponseDto(masterEntity);
        }
        return null;
    }

    @Transactional
    public DetailResponseDto postDetail(UUID masterId, DetailPostDto detailPostDto) {
        Optional<MasterEntity> optionalMasterEntity = masterRepository.findById(masterId);
        if (optionalMasterEntity.isEmpty()) return null;
        MasterEntity masterEntity = optionalMasterEntity.get();
        DetailEntity detailEntity = detailMapper.toDetailEntity(detailPostDto);
        masterEntity.getDetails().add(detailEntity);
        //masterRepository.save(masterEntity);
        return detailMapper.toDetailResponseDto(detailEntity);
    }

    @Transactional
    public DetailResponseDto putDetail(UUID masterId, UUID detailId, DetailPostDto detailPostDto) {
        Optional<MasterEntity> optionalMasterEntity = masterRepository.findById(masterId);
        if (optionalMasterEntity.isEmpty()) return null;
        MasterEntity masterEntity = optionalMasterEntity.get();
        Optional<DetailEntity> optionalDetailEntity = masterEntity.getDetails()
                .stream()
                .filter(det -> detailId.equals(det.getId()))
                .findFirst();
        if (optionalDetailEntity.isEmpty()) return null;
        DetailEntity detailEntity = optionalDetailEntity.get();
        detailEntity.setName(detailPostDto.getName());
        detailEntity.setPrice(detailPostDto.getPrice());

        return detailMapper.toDetailResponseDto(detailEntity);
    }

    @Transactional
    public boolean deleteMaster(UUID masterId) {
        if (masterRepository.findById(masterId).isEmpty()) return false;
        masterRepository.deleteById(masterId);
        return true;
    }

    @Transactional
    public boolean deleteDetail(UUID masterId, UUID detailId) {
        Optional<MasterEntity> optionalMasterEntity = masterRepository.findById(masterId);
        if (optionalMasterEntity.isEmpty()) return false;
        MasterEntity masterEntity = optionalMasterEntity.get();
        Collection<DetailEntity> detailEntities =
                masterEntity.getDetails()
                        .stream()
                        .filter(det -> det.getId().equals(detailId))
                        .toList();

        if (detailEntities.isEmpty()) return false;
        detailEntities.forEach(detailEntity -> masterEntity.getDetails().remove(detailEntity));
        //masterRepository.save(masterEntity);
        return true;
    }


}
